package tn.library_managment_system.Controller;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import tn.library_managment_system.Model.User;
import tn.library_managment_system.Service.UserService;
import tn.library_managment_system.util.DatabaseConnection;

import java.sql.SQLException;
public class DeleteButtonUserCell extends TableCell<User, Void> {
    private final Button deleteButton = new Button("Delete");

    public DeleteButtonUserCell() {
        deleteButton.setOnAction(event -> {
            User user = getTableView().getItems().get(getIndex());
            try {
                UserService.deleteUser(user, DatabaseConnection.getConnection());
                getTableView().getItems().remove(user);
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
