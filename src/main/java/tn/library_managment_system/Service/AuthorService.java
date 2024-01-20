package tn.library_managment_system.Service;

import tn.library_managment_system.Model.Author;

import java.sql.*;

public class AuthorService {

    public static Author getAuthorByid(long id, Connection conn) {
        String selectAuteurSql = "SELECT * FROM Author WHERE id=?";
        try (PreparedStatement selectAuteurStmt = conn.prepareStatement(selectAuteurSql)) {
            selectAuteurStmt.setLong(1, id);
            try (ResultSet resultSet = selectAuteurStmt.executeQuery()) {
                if (resultSet.next()) {
                    return new Author(id ,resultSet.getString("firstName"), resultSet.getString("lastName"));
                } else {
                    throw new SQLException("Author not found");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    static long getAuteurID(Author auteur, Connection conn) throws SQLException {
        String selectAuteurSql = "SELECT id FROM Author WHERE firstName=? AND lastName=?";
        try (PreparedStatement selectAuteurStmt = conn.prepareStatement(selectAuteurSql)) {
            selectAuteurStmt.setString(1, auteur.getFirstName());
            selectAuteurStmt.setString(2, auteur.getLastName());
            try (ResultSet resultSet = selectAuteurStmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("id");
                } else {
                    String insererAuteurSql = "INSERT INTO Author (firstName,LastName) VALUES (?,?)";
                    try (PreparedStatement insererAuteurStmt = conn.prepareStatement(insererAuteurSql, Statement.RETURN_GENERATED_KEYS)) {
                        insererAuteurStmt.setString(1, auteur.getFirstName());
                        insererAuteurStmt.setString(2, auteur.getLastName());
                        insererAuteurStmt.executeUpdate();
                        try (ResultSet generatedKeys = insererAuteurStmt.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                return generatedKeys.getLong(1);
                            } else {
                                throw new SQLException("Creating Author failed, no ID obtained.");
                            }

                        }
                    }
                }
            }
        }
    }
    public static Author getIdByFirstNameAndLastName(String firstName,String lastName,Connection conn) throws SQLException {
        String selectAuthorSql="Select id from Author where  firstName=? and lastName=?";
        try(PreparedStatement selectAuthorStmt=conn.prepareStatement(selectAuthorSql)){
            selectAuthorStmt.setString(1, firstName);
            selectAuthorStmt.setString(1, lastName);
            try (ResultSet resultSet = selectAuthorStmt.executeQuery()) {
                if (resultSet.next()) {
                    return new Author(resultSet.getLong("id"),resultSet.getString("firstName"), resultSet.getString("lastName"));
                } else {
                    return null;
                }
            }


            }

        }


    }

