package tn.library_managment_system.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import java.sql.Connection;
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
        try {
            String ISBN = ISBNTextField.getText().trim();
            String title = titleTextField.getText().trim();
            String authorFirstName = authorFirstNameTextField.getText().trim();
            String authorLastName = authorLastNameTextField.getText().trim();
            String type = typeTextField.getText().trim();
            String description = DescriptionTextField.getText().trim();
            int copiesNumber = copiesSpinner.getValue();

            if (!ISBN.isEmpty() && !title.isEmpty() && !type.isEmpty()
                    && !authorFirstName.isEmpty() && !authorLastName.isEmpty()
                    && copiesNumber > 0) {

                Author author = new Author(-1, authorLastName, authorFirstName);
                Book book = new Book(ISBN, title, author, -1, copiesNumber, type, description);

                try (Connection connection = DatabaseConnection.getConnection()) {
                    BookService.ajouterLivreDansBaseDeDonnees(book, connection);
                    alert("book added successfully");
                    resetForm();


                }
                catch (SQLException e) {
                    if (e.getMessage().trim().contains("java.sql.SQLException:Un book avec le même ISBN existe déjà dans la base de données.".trim())) {
                        bookAddErrorText.setText("A book with this ISBN already exists");
                    } else {
                        bookAddErrorText.setText(e.getMessage());
                    }
                }


            } else {
                showValidationErrorAlert("Please fill in all the fields.");
            }
        } catch (Exception e) {
            if (e.getMessage().trim().contains("Un book avec le même ISBN existe déjà dans la base de données.".trim())) {
                bookAddErrorText.setText("A book with this ISBN already exists");
            } else {
                bookAddErrorText.setText(e.getMessage());
            }

        }
    }

    private void alert(String bookReturnedSuccessfully) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Book Added Successfully");
        alert.setContentText(bookReturnedSuccessfully);
        alert.showAndWait();

    }

    private void resetForm() {
        ISBNTextField.clear();
        titleTextField.clear();
        authorFirstNameTextField.clear();
        authorLastNameTextField.clear();
        typeTextField.clear();
        DescriptionTextField.clear();
        copiesSpinner.getValueFactory().setValue(1);

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
    private void showValidationErrorAlert(String message) {
        // You can customize this method to display an alert for validation errors
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

