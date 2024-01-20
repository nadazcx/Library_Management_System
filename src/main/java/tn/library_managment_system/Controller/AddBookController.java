package tn.library_managment_system.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.library_managment_system.Model.Author;
import tn.library_managment_system.Model.Book;
import tn.library_managment_system.Service.BookService;
import tn.library_managment_system.util.DatabaseConnection;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class AddBookController {
    public TextField ISBNTextField;
    public TextField titleTextField;
    public TextField typeTextField;
    public TextField authorFirstNameTextField;
    public TextField authorLastNameTextField;
    public Spinner<Integer> copiesSpinner;
    public TextField DescriptionTextField;
    public Text bookAdd;
    public Text bookAddErrorText;
    public ImageView bookImageView;
    private File selectedFile;

    @FXML
    public void addBook(ActionEvent actionEvent) throws SQLException {
        String ISBN = ISBNTextField.getText();
        String title = titleTextField.getText();
        String authorFirstName = authorFirstNameTextField.getText();
        String authorLastName = authorLastNameTextField.getText();
        String type = typeTextField.getText();
        String description = DescriptionTextField.getText();
        int copiesNumber = copiesSpinner.getValue();

        if (ISBNTextField != null && titleTextField != null && typeTextField != null
                && authorFirstNameTextField != null && authorLastNameTextField != null
                && copiesSpinner != null) {
            Author author = new Author(-1, authorLastName, authorFirstName);
            Book book = new Book(ISBN, title, author, -1, copiesNumber, type, description);
            BookService.ajouterLivreDansBaseDeDonnees(book, DatabaseConnection.getConnection());
            System.out.println(book);
            if (selectedFile != null) {
                if (BookService.uploadImage(selectedFile, book, DatabaseConnection.getConnection())) {
                    bookAdd.setText("Book and Image added successfully");
                } else {
                    bookAdd.setText("Book added successfully, but failed to upload the image");
                }
            } else {
                bookAdd.setText("Book added successfully");
            }
        }
    }

    private void resetForm() {
        ISBNTextField.clear();
        titleTextField.clear();
        authorFirstNameTextField.clear();
        authorLastNameTextField.clear();
        typeTextField.clear();
        DescriptionTextField.clear();
        copiesSpinner.getValueFactory().setValue(1);
        bookAdd.setText("");
    }

    @FXML
    private void uploadPicture(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                bookImageView.setImage(image);
                this.selectedFile = selectedFile;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void redirectToEditBook(ActionEvent event) {
        try {
            MenuItem menuItem = (MenuItem) event.getSource();
            Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();

            URL fxmlLocation = getClass().getResource("/tn/library_managment_system/admin/edit-book.fxml");
            scene.setRoot(FXMLLoader.load(fxmlLocation));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void redirecToAddUser(ActionEvent event) {
        try {
            MenuItem menuItem = (MenuItem) event.getSource();
            Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();

            URL fxmlLocation = getClass().getResource("/tn/library_managment_system/admin/add-user.fxml");
            scene.setRoot(FXMLLoader.load(fxmlLocation));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void redirectToEditUser(ActionEvent event) {
        try {
            MenuItem menuItem = (MenuItem) event.getSource();
            Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();

            URL fxmlLocation = getClass().getResource("/tn/library_managment_system/admin/edit-user.fxml");
            scene.setRoot(FXMLLoader.load(fxmlLocation));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void redirectToNewLoan(ActionEvent event) {
        try {
            MenuItem menuItem = (MenuItem) event.getSource();
            Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();

            URL fxmlLocation = getClass().getResource("/tn/library_managment_system/admin/new-loan.fxml");
            scene.setRoot(FXMLLoader.load(fxmlLocation));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void redirectToReturnBook(ActionEvent event) {
        try {
            MenuItem menuItem = (MenuItem) event.getSource();
            Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();

            URL fxmlLocation = getClass().getResource("/tn/library_managment_system/admin/return-book.fxml");
            scene.setRoot(FXMLLoader.load(fxmlLocation));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

