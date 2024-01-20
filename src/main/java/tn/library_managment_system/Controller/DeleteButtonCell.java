package tn.library_managment_system.Controller;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import tn.library_managment_system.Model.Book;
import tn.library_managment_system.Service.BookService;
import tn.library_managment_system.util.DatabaseConnection;

import java.sql.SQLException;

public class DeleteButtonCell extends TableCell<Book, Void> {
    private final Button deleteButton = new Button("Delete");

    public DeleteButtonCell() {
        deleteButton.setOnAction(event -> {
            Book book = getTableView().getItems().get(getIndex());
            try {
                BookService.supprimerLivreDeLaBaseDonnee(book, DatabaseConnection.getConnection());
                getTableView().getItems().remove(book);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setGraphic(deleteButton);
        } else {
            setGraphic(null);
        }
    }
}
