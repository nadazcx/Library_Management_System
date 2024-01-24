package tn.library_managment_system.Controller;



import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.library_managment_system.Model.Reader;
import tn.library_managment_system.Model.Subscription;
import tn.library_managment_system.Service.ReaderService;
import tn.library_managment_system.util.DatabaseConnection;
import tn.library_managment_system.util.PasswordHashing;


import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;


public class AddUserController{
    @FXML

    public Text emailErrorText;
    @FXML
    public Text cinErrorText;
    @FXML
    public Text lastNameErrorText;

    @FXML
    public Text passwordErrorText;
    @FXML
    public Text firstNameErrorText;
    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField cinTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button inscrireButton;
    private BooleanProperty isValidEmail = new SimpleBooleanProperty(true);
    private BooleanProperty isValidCIN = new SimpleBooleanProperty(true);
    private BooleanProperty isValidFirstName=new SimpleBooleanProperty(true);
    private BooleanProperty isValidLastName=new SimpleBooleanProperty(true);




    @FXML
    protected void initialize() {
        addEmailValidationListener();
        addCINValidationListener();
        addLastNameValidationListener();
        addLastNameValidationListener();
        addFirstNameValidationListener();

    }

    @FXML
    protected void signIn(ActionEvent event) {
        String nom = firstNameTextField.getText();
        String prenom = lastNameTextField.getText();
        String cinText = cinTextField.getText();
        String email = emailTextField.getText();
        LocalDate dateNaissance = datePicker.getValue();

        if (nom.isEmpty() || prenom.isEmpty() || cinText.isEmpty() || email.isEmpty() || dateNaissance == null) {
            // Display an error message or handle empty fields as needed
            showValidationErrorAlert("Please fill in all the fields.");
            return;
        }

        long cin;
        try {
            cin = Long.parseLong(cinText);
        } catch (NumberFormatException e) {
            // Handle the case where CIN is not a valid number
            cinErrorText.setText("Invalid CIN format. Please enter a valid number.");
            emailErrorText.setText("");
            return;
        }

        // Your existing code for validating names, CIN, and other conditions...

        Subscription a1 = new Subscription();
        Reader lecteur = new Reader(cin, nom, prenom, email, null, dateNaissance, a1, 0);
        DatabaseConnection conn = new DatabaseConnection();

        try {
            ReaderService.addReaderToDatabase(lecteur, DatabaseConnection.getConnection());
            showInfoAlert();
            resetForm();
        } catch (Exception e) {
            if (e.getMessage().contains("A user already exists with this CIN")) {
                cinErrorText.setText("CIN already exists. Please choose a different CIN.");
                emailErrorText.setText("");
            } else if (e.getMessage().contains("A user already exists with this email")) {
                emailErrorText.setText("A user already exists with this email.");
                cinErrorText.setText("");
            } else {
                e.printStackTrace();
            }
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



    private void showInfoAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Inscription successful");
        alert.setHeaderText(null);
        alert.setContentText("User Added Successfully");
        alert.showAndWait();
    }

    private void resetForm() {
        firstNameTextField.clear();
        lastNameTextField.clear();
        cinTextField.clear();
        datePicker.setValue(null);
        cinErrorText.setText("");
        emailTextField.clear();
        emailErrorText.setText("");
    }
    private void addCINValidationListener() {
        cinTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isValidCIN.set(isValidCIN(newValue));
            if (!isValidCIN.getValue()) {
                cinErrorText.setText("Invalid CIN format. Please enter a valid number.");
            } else {
                cinErrorText.setText("");
            }
        });
    }

    private  void addFirstNameValidationListener(){
        firstNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isValidFirstName.set(isValidName(newValue));
            if(!isValidFirstName.getValue()){
                firstNameErrorText.setText("Invalid name format. Please enter a valid name.");
            }
            else{
                firstNameErrorText.setText("");
            }
        });
    }
    private void addEmailValidationListener() {
        emailTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isValidEmail.set(isValidEmail(newValue));
            if (!isValidEmail.getValue()) {
                emailErrorText.setText("Invalid email format. Please enter a valid email address.");
            } else {
                emailErrorText.setText("");
            }
        });
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    private void   addLastNameValidationListener(){
        lastNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isValidLastName.set(isValidName(newValue));
            if(!isValidLastName.getValue()){
                lastNameErrorText.setText("Invalid last name format. Please enter a valid last name.");
            }
            else{
                lastNameErrorText.setText("");
            }
        });
    }




    private boolean isValidCIN(String cin) {
        String cinRegex = "^[0-9]{8}$";
        try {
            Long.parseLong(cin);
            return cin.matches(cinRegex);

        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidName(String name){
        String nameRegex="^[a-zA-Z\\s]*$";
        return name.matches(nameRegex);
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