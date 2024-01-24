package tn.library_managment_system.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import tn.library_managment_system.Model.Book;
import tn.library_managment_system.Model.Loan;
import tn.library_managment_system.Model.User;
import tn.library_managment_system.Service.LoanService;
import tn.library_managment_system.util.DatabaseConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;

public class NewLoanController {
    public TextField cinTextField;
    public TextField isbnTextField;
    public DatePicker datePicker;
    public Button addLoan;
    public Label isbnError;
    public Label cinError;
    public Label emptyError;
    public Label dateError;

    public void redirectToEditBook(ActionEvent event) {
        try {
            MenuItem menuItem = (MenuItem) event.getSource();
            Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();

            URL fxmlLocation = getClass().getResource("/tn/library_managment_system/admin/edit-book.fxml");
            scene.setRoot(FXMLLoader.load(fxmlLocation));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void redirectToAddBook(ActionEvent event) {
        try {
            MenuItem menuItem = (MenuItem) event.getSource();
            Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();

            URL fxmlLocation = getClass().getResource("/tn/library_managment_system/admin/add-book.fxml");
            scene.setRoot(FXMLLoader.load(fxmlLocation));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void redirectToAddUser(ActionEvent event) {
        try {
            MenuItem menuItem = (MenuItem) event.getSource();
            Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();

            URL fxmlLocation = getClass().getResource("/tn/library_managment_system/admin/add-user.fxml");
            scene.setRoot(FXMLLoader.load(fxmlLocation));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void redirectToEditUser(ActionEvent event)   {
        try {
            MenuItem menuItem = (MenuItem) event.getSource();
            Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();

            URL fxmlLocation = getClass().getResource("/tn/library_managment_system/admin/edit-user.fxml");
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

    public void newLoan(ActionEvent event) {
        try {
            String cinText = cinTextField.getText().trim();
            String isbn = isbnTextField.getText().trim();
            LocalDate date = datePicker.getValue();

            if (cinText.isEmpty() || isbn.isEmpty() || date == null) {
                showValidationError("Please fill in all the fields");
                return;
            }

            long cin = Long.parseLong(cinText);
            Book book = new Book(isbn);
            User user = new User(cin);

            Loan loan = new Loan(book, user, date);
            LoanService.addNewLoan(loan, DatabaseConnection.getConnection());
            alert("Loan added successfully");
            clear();
        } catch (Exception e) {
            if (e.getMessage().equals("java.sql.SQLException: Book not found")) {
                isbnError.setText("Book not found");
            } else if (e.getMessage().equals("java.sql.SQLException: No user found with the specified CIN.")) {
                cinError.setText("User not found");
            } else {
                e.printStackTrace();
            }
        }
    }

    private void showValidationError(String message) {
        // You can customize this method to display an alert for validation errors
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void alert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void clear() {
        isbnError.setText("");
        cinError.setText("");
        emptyError.setText("");
        dateError.setText("");
        isbnTextField.clear();
        cinTextField.clear();
        datePicker.setValue(null);
    }

}
