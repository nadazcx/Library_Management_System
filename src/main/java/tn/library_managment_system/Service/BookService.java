package tn.library_managment_system.Service;




import tn.library_managment_system.Model.*;
import tn.library_managment_system.util.DatabaseConnection;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookService {
    static long getBookIDByISBN(Book book, Connection conn) {
        String requeteGetBookIDByISBN = "SELECT code FROM book WHERE ISBN = ?";
        try {
            PreparedStatement declarationGetBookIDByISBN = conn.prepareStatement(requeteGetBookIDByISBN);
            declarationGetBookIDByISBN.setString(1, book.getISBN());
            try (ResultSet resultSet = declarationGetBookIDByISBN.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("code");
                } else {
                    throw new SQLException("Book not found");
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static long ajouterLivreDansBaseDeDonnees(Book book, Connection conn) {
        try {
            long idAuteur = AuthorService.getAuteurID(book.getAuthor(), conn);
            String requeteVerificationISBN = "SELECT code FROM book WHERE ISBN = ?";

            try (PreparedStatement declarationVerificationISBN = conn.prepareStatement(requeteVerificationISBN)) {
                declarationVerificationISBN.setString(1, book.getISBN());

                try (ResultSet resultatVerificationISBN = declarationVerificationISBN.executeQuery()) {
                    if (resultatVerificationISBN.next()) {
//                        throw new SQLException("Un book avec le même ISBN existe déjà dans la base de données.");
                        throw new Exception("Un book avec le même ISBN existe déjà dans la base de données.");
                    } else {
                        String requeteInsertionLivre = "INSERT INTO book(ISBN, title, author_id, numberofcopies, description, type) VALUES (?, ?, ?, ?, ?, ?)";


                        try (PreparedStatement declarationInsertionLivre = conn.prepareStatement(requeteInsertionLivre, Statement.RETURN_GENERATED_KEYS)) {
                            declarationInsertionLivre.setString(1, book.getISBN());
                            declarationInsertionLivre.setString(2, book.getTitle());
                            declarationInsertionLivre.setLong(3, idAuteur);
                            declarationInsertionLivre.setInt(4, book.getNumberOfCopies());
                            declarationInsertionLivre.setString(5, book.getDescription());
                            declarationInsertionLivre.setString(6, book.getTypes());

                            int lignesModifiees = declarationInsertionLivre.executeUpdate();

                            if (lignesModifiees == 0) {
                                throw new SQLException("Échec de la création du book, aucune ligne affectée.");
                            }

                            try (ResultSet clesGenerees = declarationInsertionLivre.getGeneratedKeys()) {
                                if (clesGenerees.next()) {
                                    long idLivre = clesGenerees.getLong(1);
                                    return idLivre;
                                } else {
                                    throw new SQLException("Échec de la création du book, aucun ID obtenu.");
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;

    }


    public static void modifierLivreDansBaseDeDonnees(Book book, Connection conn) {
        try {
            String requeteModificationLivre = "UPDATE Book SET titre = ?, nombreexemplaire = ?, description = ? WHERE ISBN = ?";
            try {
                PreparedStatement declarationModificationLivre = conn.prepareStatement(requeteModificationLivre);
                declarationModificationLivre.setString(1, book.getTitle());
                declarationModificationLivre.setInt(2, book.getNumberOfCopies());
                declarationModificationLivre.setString(3, book.getDescription());
                declarationModificationLivre.setString(4, book.getISBN());
                int lignesModifies = declarationModificationLivre.executeUpdate();
                if (lignesModifies == 0) {
                    throw new SQLException("Echec de la modification du book, aucune ligne n'a été affectée");
                } else {
                    System.out.println("model.Book modifié avec succès");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public static void supprimerLivreDeLaBaseDonnee(Book book, Connection conn) {
        try {
            String requeteSuppressionLivre = "DELETE FROM Book WHERE ISBN = ?";
            try {
                PreparedStatement declarationSuppressionLivre = conn.prepareStatement(requeteSuppressionLivre);
                declarationSuppressionLivre.setString(1, book.getISBN());
                int lignesModifies = declarationSuppressionLivre.executeUpdate();
                if (lignesModifies == 0) {
                    throw new SQLException("Echec de la suppression du book, aucune ligne n'a été affectée");
                } else {
                    System.out.println("model.Book supprimé avec succès");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }


    public static List<Book> getAllBooks(Connection conn) {
        String requeteGetAllBooks = "SELECT * FROM Book";
        try {
            PreparedStatement declarationGetAllBooks = conn.prepareStatement(requeteGetAllBooks);
            try (ResultSet resultSet = declarationGetAllBooks.executeQuery()) {
                List<Book> listOfBooks = new ArrayList<>();
                while (resultSet.next()) {
                    String ISBN = resultSet.getString("ISBN");
                    String title = resultSet.getString("title");
                    long code = resultSet.getLong("code");
                    int numberOfCopies = resultSet.getInt("numberOfCopies");
                    String description = resultSet.getString("description");
                    String type = resultSet.getString("type");
                    String imageURL = resultSet.getString("cover_photo_url");
                    Author author = AuthorService.getAuthorByid(resultSet.getLong("author_id"), conn);

                    Book book = new Book(ISBN, title, author, code, numberOfCopies, type, description, imageURL);
                    listOfBooks.add(book);
                }

                return listOfBooks;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, List<Book>> groupBooksByType(Connection conn) {
        List<Book> bookList = getAllBooks(conn);
        Map<String, List<Book>> map = new HashMap<>();
        for (Book book : bookList) {
            if (map.containsKey(book.getTypes())) {
                List<Book> list = map.get(book.getTypes());
                list.add(book);
                map.put(book.getTypes(), list);
            } else {
                List<Book> list = new ArrayList<>();
                list.add(book);
                map.put(book.getTypes(), list);
            }
        }
        return map;
    }

    public static void setURL(Book book, Connection conn) throws SQLException {
        String requeteSetURL = "update book set cover_photo_url= ? where ISBN=?";
        try {
            PreparedStatement declarationSetURL = conn.prepareStatement(requeteSetURL);
            declarationSetURL.setString(1, book.getImageURL());
            declarationSetURL.setString(2, book.getISBN());
            System.out.println(declarationSetURL);
            int lignesModifies = declarationSetURL.executeUpdate();
            if (lignesModifies == 0) {
                throw new SQLException("Echec de la modification du book, aucune ligne n'a été affectée");
            } else {
                System.out.println("model.Book modifié avec succès");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean uploadImage(File sourceFile, Book book, Connection conn) throws SQLException {
        try {
            String fileName = sourceFile.getName();
            int dotIndex = fileName.lastIndexOf('.');
            String extension = (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);

            // Get the application's working directory
            String workingDirectory = System.getProperty("user.dir");

            String destinationDirectory = "/src/main/resources/Image/";

            // Create the full destination path
            String destinationPath = workingDirectory + destinationDirectory + book.getISBN() + "." + extension;
            Path destination = Path.of(destinationPath);
            System.out.println(destinationPath);
            book.setImageURL(book.getISBN() + "." + extension);
            BookService.setURL(book, conn);
            Files.copy(sourceFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
            return  true;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

