package tn.library_managment_system.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import tn.library_managment_system.Model.Book;
import tn.library_managment_system.Model.Loan;
import tn.library_managment_system.Model.User;
import tn.library_managment_system.Service.LoanService;
import tn.library_managment_system.util.DatabaseConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.function.BiPredicate;

public class ReturnBookController {

    public TextField isbnTextField;
    public TextField cinTextField;
    public Label cinError;
    public Label isbnError;
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

    public void returnBook(ActionEvent event) {
        try{
            String isbn= isbnTextField.getText();
            Long cin= Long.parseLong(cinTextField.getText());
            Book book = new Book(isbn);
            User user = new User(cin);
            LocalDate Date = LocalDate.now();
            Loan loan = new Loan(book,user,Date);
            LoanService.returnBook(loan, DatabaseConnection.getConnection());
            alert("Book returned successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
    }  catch (Exception e) {

        if ("For input string: \"\"".equals(e.getMessage())) {
            emptyError.setText("Please fill all the fields");
        }
        if (e.getMessage().equals("java.sql.SQLException: Book not found")) {
            isbnError.setText("Book not found");
        } else if (e.getMessage().equals("java.sql.SQLException: No user found with the specified CIN.")) {
            cinError.setText("User not found");
        } else {
            e.printStackTrace();
        }
    }
    }

    private void alert(String bookReturnedSuccessfully) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Book returned");
        alert.setContentText(bookReturnedSuccessfully);
        alert.showAndWait();

    }
}
