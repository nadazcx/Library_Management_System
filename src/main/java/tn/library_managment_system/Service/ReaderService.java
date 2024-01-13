package tn.library_managment_system.Service;




import tn.library_managment_system.Model.*;

import java.sql.*;

public class ReaderService {

    public static void getReaderID(Reader reader, Connection conn) {
        String getReaderIDSQL = "SELECT Reader_ID FROM reader WHERE User_ID IN (SELECT User_ID FROM user WHERE CIN = ?)";
        try (PreparedStatement getReaderIDStmt = conn.prepareStatement(getReaderIDSQL)) {
            getReaderIDStmt.setLong(1, reader.getCIN());

            int affectedRows = getReaderIDStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Getting reader failed, no rows affected.");
            }

            System.out.println("Reader got from the database.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addReaderToDatabase(Reader reader, Connection conn) throws SQLException {
        Savepoint savepoint = null;
        try {
            conn.setAutoCommit(false);
            savepoint = conn.setSavepoint();

            UserService.addUserToDatabase(reader, conn);

            long userId = UserService.getUserId(reader, conn);
            String addReaderSQL = "INSERT INTO reader (User_ID, Credit) VALUES (?, ?)";
            try (PreparedStatement addReaderStmt = conn.prepareStatement(addReaderSQL, Statement.RETURN_GENERATED_KEYS)) {
                addReaderStmt.setLong(1, userId);
                addReaderStmt.setDouble(2, reader.getSubscriptionFee());

                int affectedRows = addReaderStmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating reader failed, no rows affected.");
                }

                // Retrieve the auto-generated key (Reader_ID)
                try (ResultSet generatedKeys = addReaderStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long readerId = generatedKeys.getLong(1);
                        System.out.println("Reader added to the database with ID: " + readerId);
                    } else {
                        throw new SQLException("Creating reader failed, no ID obtained.");
                    }
                }
            }

            conn.commit();
        } catch (SQLException e) {
            System.out.println("Error adding Reader to the database");
            if (savepoint != null) {
                conn.rollback(savepoint);
            }

            UserService.deleteUser(reader, conn);

            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public static void deleteReader(Reader reader, Connection conn) throws SQLException {
        String deleteReaderSQL = "DELETE FROM reader WHERE User_ID = ?";
        try (PreparedStatement deleteReaderStmt = conn.prepareStatement(deleteReaderSQL)) {
            long id = UserService.getUserId(reader, conn);
            deleteReaderStmt.setLong(1, id);
            UserService.deleteUser(reader, conn);

            int affectedRows = deleteReaderStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting reader failed, no rows affected.");
            }

            System.out.println("Reader deleted from the database.");
        }
    }


}
