package tn.library_managment_system.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.library_managment_system.util.DatabaseConnection;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

import static tn.library_managment_system.Service.UserService.*;

public class LoginController{

    public Button signInButton;
    public Text cinErrorText;
    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordTextField;





    @FXML
    protected void initialize() {

    }

    @FXML
    protected void handleLogin(ActionEvent event) throws SQLException, NoSuchAlgorithmException {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        String userRole = sValidLogin(email, password, DatabaseConnection.getConnection());
        String fxmlLocation;
        System.out.println(userRole);

        if (userRole != null) {
            try {
                if ("admin".equals(userRole)) {
                    fxmlLocation = "/tn/library_managment_system/admin/adminHome.fxml";
                } else if ("client".equals(userRole)) {
                    fxmlLocation = "/tn/library_managment_system/client/clientHome.fxml";
                } else {
                    fxmlLocation = "/tn/library_managment_system/HomeDefault.fxml";
                }

                URL locationUrl = getClass().getResource(fxmlLocation);
                if (locationUrl != null) {
                    FXMLLoader loader = new FXMLLoader(locationUrl);
                    Parent root = loader.load();

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // Retrieve the existing stage
                    stage.setScene(new Scene(root));
                    stage.show();
                } else {
                    System.err.println("FXML file not found: " + fxmlLocation);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            cinErrorText.setText("Invalid email or password.");
            System.out.println("Login failed. Invalid email or password.");
        }
    }

    private String sValidLogin(String email, String password, Connection connection) {
        return null;
    }

    protected void redirectToLogInScreen(ActionEvent event) {
        try {
            URL fxmlLocation = getClass().getResource("/tn/library_managment_system/login.fxml");
            // System.out.println(fxmlLocation);
            FXMLLoader loader = new FXMLLoader(fxmlLocation);

            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    private void redirectToSignInScreen(ActionEvent event) {
        SigninController Sn = new SigninController();
        Sn.redirectToSignInScreen(event);


    }

}
