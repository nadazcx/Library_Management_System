package tn.library_managment_system.Service;



import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.library_managment_system.Model.User;
import tn.library_managment_system.util.DatabaseConnection;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static tn.library_managment_system.util.PasswordHashing.hashPasswordSha256;

public class UserService {

    public static long getUserId(User user, Connection conn) throws SQLException {
        String selectUserSql = "SELECT User_ID FROM user WHERE CIN=?";
        try (PreparedStatement selectUserStmt = conn.prepareStatement(selectUserSql)) {
            selectUserStmt.setLong(1, user.getCIN());
            try (ResultSet resultSet = selectUserStmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("User_ID");
                } else {
                    throw new SQLException("No user found with the specified CIN.");
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<User> getAllUsers(Connection conn) {
        String queryGetAllUsers = "SELECT * FROM user";
        try {
            PreparedStatement statementGetAllUsers = conn.prepareStatement(queryGetAllUsers);
            try (ResultSet resultSet = statementGetAllUsers.executeQuery()) {
                List<User> listOfUsers = new ArrayList<>();
                while (resultSet.next()) {
                    int userId = resultSet.getInt("user_ID");
                    long cin = resultSet.getLong("CIN");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    String email = resultSet.getString("Email");
                    String password = resultSet.getString("Password");
                    Date birthdate = resultSet.getDate("birthdate");

                    User user = new User(cin, firstName, lastName, email, password, birthdate.toLocalDate());
                    listOfUsers.add(user);
                }

                return listOfUsers;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addUserToDatabase(User user, Connection conn) throws SQLException {
        try {
            String checkCINQuery = "SELECT User_ID FROM user WHERE CIN=?";
            try (PreparedStatement checkCINStmt = conn.prepareStatement(checkCINQuery)) {
                checkCINStmt.setLong(1, user.getCIN());
                try (ResultSet checkCINResult = checkCINStmt.executeQuery()) {
                    if (checkCINResult.next()) {
                        throw new SQLException("A user already exists with this CIN");
                    } else {
                        try {
                            String checkEmailQuery = "SELECT User_ID FROM user WHERE email LIKE ?";
                            try (PreparedStatement checkEmailStmt = conn.prepareStatement(checkEmailQuery)) {
                                checkEmailStmt.setString(1, user.getEmail());
                                try (ResultSet checkEmailResult = checkEmailStmt.executeQuery()) {
                                    if (checkEmailResult.next()) {
                                        throw new SQLException("A user already exists with this email");
                                    } else {
                                        String addUserQuery = "INSERT INTO user (CIN, firstname, lastname, birthdate, email, password,role) VALUES (?, ?, ?, ?, ?, ?,?)";
                                        try (PreparedStatement addUserStmt = conn.prepareStatement(addUserQuery)) {
                                            addUserStmt.setLong(1, user.getCIN());
                                            addUserStmt.setString(2, user.getName());
                                            addUserStmt.setString(3, user.getLastName());
                                            addUserStmt.setDate(4, Date.valueOf(user.getBirthDate()));
                                            addUserStmt.setString(5, user.getEmail());
                                            addUserStmt.setString(6, user.getPassword());
                                            addUserStmt.setString(7, "client");
                                            int rowsAffected = addUserStmt.executeUpdate();
                                            if (rowsAffected == 0) {
                                                throw new SQLException("Failed to add user");
                                            } else {
                                                System.out.println("User Created");
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteUser(User user, Connection conn) {
        String deleteUserSQL = "DELETE FROM user WHERE CIN = ?";

        try (PreparedStatement deleteUserStmt = conn.prepareStatement(deleteUserSQL)) {
            deleteUserStmt.setLong(1, user.getCIN());

            int rowsAffected = deleteUserStmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("No user found with the specified ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String sValidLogin(String email, String password, Connection connection) throws NoSuchAlgorithmException {
        String passwordHashed=hashPasswordSha256(password);

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE email = ? AND password= ?")) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, passwordHashed);
            System.out.println("email");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()){
                    return resultSet.getString("role");
                }
                else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws SQLException {
        List<User> users = getAllUsers(DatabaseConnection.getConnection());
        System.out.println(users);
    }



}
