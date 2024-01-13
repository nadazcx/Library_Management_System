package tn.library_managment_system.Service;




import tn.library_managment_system.Model.*;

import java.sql.*;

public class BookService {

    private static long getAuteurID(Author auteur, Connection conn) throws SQLException {
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
                        insererAuteurStmt.setString(2, auteur.getLastName( ));
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

    public static void ajouterLivreDansBaseDeDonnees(Book book, Connection conn) {
        try {
            long idAuteur = getAuteurID(book.getAuthor(), conn);
            String requeteVerificationISBN = "SELECT code FROM book WHERE ISBN = ?";
            try (PreparedStatement declarationVerificationISBN = conn.prepareStatement(requeteVerificationISBN)) {
                declarationVerificationISBN.setLong(1, book.getISBN());
                try (ResultSet resultatVerificationISBN = declarationVerificationISBN.executeQuery()) {
                    if (resultatVerificationISBN.next()) {
                        throw new SQLException("Un book avec le même ISBN existe déjà dans la base de données.");
                    } else {
                        String requeteInsertionLivre = "INSERT INTO book(ISBN, title, author_id,numberofcopies,description) VALUES ( ?, ?, ?, ?,?)";
                        try (PreparedStatement declarationInsertionLivre = conn.prepareStatement(requeteInsertionLivre, Statement.RETURN_GENERATED_KEYS)) {
                            declarationInsertionLivre.setLong(1, book.getISBN());
                            declarationInsertionLivre.setString(2, book.getTitle());
                            declarationInsertionLivre.setLong(3, idAuteur);  // Utiliser l'ID de l'auteur obtenu/inséré
                            declarationInsertionLivre.setInt(4, book.getNumberOfCopies());
                            declarationInsertionLivre.setString(5, book.getDescription());
                            int lignesModifiees = declarationInsertionLivre.executeUpdate();

                            if (lignesModifiees == 0) {
                                throw new SQLException("Échec de la création du book, aucune ligne affectée.");
                            }
                            try (ResultSet clesGenerees = declarationInsertionLivre.getGeneratedKeys()) {
                                if (clesGenerees.next()) {
                                    long idLivre = clesGenerees.getLong(1);


                                    String requeteInsertionTypesLivre = "INSERT INTO book_type (code, type) VALUES (?, ?)";
                                    try (PreparedStatement declarationInsertionTypesLivre = conn.prepareStatement(requeteInsertionTypesLivre)) {
                                        for (String type : book.getTypes()) {
                                            declarationInsertionTypesLivre.setLong(1, idLivre);
                                            declarationInsertionTypesLivre.setString(2, type);
                                            declarationInsertionTypesLivre.addBatch();
                                        }

                                        int[] typesInseres = declarationInsertionTypesLivre.executeBatch();

                                        System.out.println("model.Book ajouté à la base de données avec " + typesInseres.length + " types.");
                                    }
                                } else {
                                    throw new SQLException("Échec de la création du book, aucun ID obtenu.");
                                }
                            }


                        }
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void supprimerLivreDansBaseDeDonnees(Book book, Connection conn) {
        try {
            String requeteSuppressionLivre = "DELETE FROM Book WHERE code = ?";
            try {
                PreparedStatement declarationSuppressionLivre = conn.prepareStatement(requeteSuppressionLivre);
                declarationSuppressionLivre.setLong(1, book.getCode());
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

    public static void modifierLivreDansBaseDeDonnees(Book book, Connection conn) {
        try {
            String requeteModificationLivre = "UPDATE Book SET titre = ?, nombreexemplaire = ?, description = ? WHERE code = ?";
            try {
                PreparedStatement declarationModificationLivre = conn.prepareStatement(requeteModificationLivre);
                declarationModificationLivre.setString(1, book.getTitle());
                declarationModificationLivre.setInt(2, book.getNumberOfCopies());
                declarationModificationLivre.setString(3, book.getDescription());
                declarationModificationLivre.setLong(4, book.getCode());
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
    public  static  void supprimerLivreDeLaBaseDonnee(Book book, Connection conn){
        try {
            String requeteSuppressionLivre = "DELETE FROM Book WHERE code = ?";
            try {
                PreparedStatement declarationSuppressionLivre = conn.prepareStatement(requeteSuppressionLivre);
                declarationSuppressionLivre.setLong(1, book.getCode());
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

}