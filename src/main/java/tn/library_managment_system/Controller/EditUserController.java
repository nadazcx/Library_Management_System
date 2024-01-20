package tn.library_managment_system.Controller;

import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import tn.library_managment_system.Model.Book;
import tn.library_managment_system.Model.User;
import tn.library_managment_system.Service.BookService;
import tn.library_managment_system.Service.UserService;
import tn.library_managment_system.util.DatabaseConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class EditUserController {


    public TableView<User> usersTableView;
    public TableColumn cinColumn;
    public TableColumn firstNameColumn;
    public TableColumn lastNameColumn;
    public TableColumn birthDateColumn;
    public TableColumn EmailColumn;
    public TextField searchTextNomField;
    public TextField searchTextCINField;

    public void initialize()throws SQLException {
        List<User> listOfUsers = UserService.getAllUsers(DatabaseConnection.getConnection());
        usersTableView.setRowFactory(tv->{
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    User rowData = row.getItem();
                    System.out.println("Double click on: " + rowData.getCIN() + " - " + rowData.getName());
                }
            });
            return row;
        });

        cinColumn.setCellValueFactory(new PropertyValueFactory<>("CIN"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        EmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        usersTableView.getItems().setAll(listOfUsers);
        TableColumn<User,Void> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setPrefWidth(60.0);
        deleteColumn.setCellFactory(param -> new DeleteButtonUserCell());
        usersTableView.getColumns().add(deleteColumn);

    }

    public void redirectToAddBook(ActionEvent event) {
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

    public void handleSearch(KeyEvent keyEvent) {
    }

    public void handleCIN(KeyEvent keyEvent) throws SQLException {
        String searchCIN = searchTextCINField.getText().toLowerCase();

        List<User> filteredUsers = UserService.getAllUsers(DatabaseConnection.getConnection())
                .stream()
                .filter(user -> String.valueOf(user.getCIN()).contains(searchCIN))
                .toList();

        usersTableView.getItems().setAll(filteredUsers);
    }

    public void handleName(KeyEvent keyEvent) throws SQLException {
        String searchName = searchTextNomField.getText().toLowerCase();
        List<User> filteredUsers = UserService.getAllUsers(DatabaseConnection.getConnection())
                .stream()
                .filter(user -> user.getName().toLowerCase().contains(searchName))
                .toList();
        usersTableView.getItems().setAll(filteredUsers);
    }

    public void handleFamilyName(KeyEvent keyEvent) throws SQLException {
        String searchFamilyName = searchTextNomField.getText().toLowerCase();
        List<User> filteredUsers = UserService.getAllUsers(DatabaseConnection.getConnection())
                .stream()
                .filter(user -> user.getLastName().toLowerCase().contains(searchFamilyName))
                .toList();
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
