package tn.library_managment_system.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import tn.library_managment_system.Model.Author;
import tn.library_managment_system.Model.Book;
import tn.library_managment_system.Service.BookService;
import tn.library_managment_system.util.DatabaseConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class EditBookController {


    public TableColumn authorColumn;
    public TableColumn typeColumn;
    public TextField searchTextField;
    public TextField searchTextISBNField;
    public TextField searchTextTypeField;
    @FXML
    private TableView<Book> booksTableView;

    @FXML
    private TableColumn<Book, String> isbnColumn;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    public void initialize() throws SQLException {
        List<Book> listOfBooks = BookService.getAllBooks(DatabaseConnection.getConnection());

        booksTableView.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Book rowData = row.getItem();
                    System.out.println("Double click on: " + rowData.getISBN() + " - " + rowData.getTitle());
                }
            });
            return row;
        });

        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("types"));

        TableColumn<Book, String> authorColumn = (TableColumn<Book, String>) booksTableView.getColumns().get(2);
        authorColumn.setCellValueFactory(cellData -> {
            Author author = cellData.getValue().getAuthor();
            if (author != null) {
                return new SimpleStringProperty(author.getFirstName() + " " + author.getLastName());
            } else {
                return new SimpleStringProperty("Unknown Author");
            }
        });

        booksTableView.getItems().setAll(listOfBooks);

        TableColumn<Book, Void> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setPrefWidth(60.0);

        deleteColumn.setCellFactory(param -> new DeleteButtonCell());

        booksTableView.getColumns().add(deleteColumn);
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
    @FXML

    public void handleSearch(javafx.scene.input.KeyEvent keyEvent) throws SQLException {
        String searchTitle = searchTextField.getText().toLowerCase();
        List<Book> filteredBooks =BookService.getAllBooks(DatabaseConnection.getConnection()).stream().filter(book -> book.getTitle().toLowerCase().contains(searchTitle))
                .toList();

        booksTableView.getItems().setAll(filteredBooks);
    }

    public void handleISBN(KeyEvent keyEvent) throws SQLException {
        String searchISBN =searchTextISBNField.getText().toLowerCase();
        List<Book> filteredBooks =BookService.getAllBooks(DatabaseConnection.getConnection()).stream().filter(book -> book.getISBN().contains(searchISBN))
                .toList();

        booksTableView.getItems().setAll(filteredBooks);
    }

    public void handleType(KeyEvent keyEvent) throws SQLException {
        String searchType=searchTextTypeField.getText().toLowerCase();
        List<Book> filteredBooks =BookService.getAllBooks(DatabaseConnection.getConnection()).stream().filter(book -> book.getTypes().toLowerCase().contains(searchType))
                .toList();

        booksTableView.getItems().setAll(filteredBooks);
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
