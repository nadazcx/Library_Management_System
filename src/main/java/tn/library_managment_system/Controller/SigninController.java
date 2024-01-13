package tn.library_managment_system.Controller;



import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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


public class SigninController {
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
    private BooleanProperty isValidPassword=new SimpleBooleanProperty(true);



    public SigninController() {
    }

    @FXML
    protected void initialize() {
        addEmailValidationListener();
        addCINValidationListener();
        addLastNameValidationListener();
        addLastNameValidationListener();
        addFirstNameValidationListener();
        addPasswordValidationListener();
    }

    @FXML
    protected void signIn(ActionEvent event) throws NoSuchAlgorithmException {
        String nom = firstNameTextField.getText();
        String prenom = lastNameTextField.getText();
        long cin = Long.parseLong(cinTextField.getText());
        String email = emailTextField.getText();
        String password = PasswordHashing.hashPasswordSha256(passwordField.getText());
        LocalDate dateNaissance = datePicker.getValue();
        if(!isValidFirstName.getValue()){
            firstNameTextField.setText("Invalid name format. Please enter a valid name.");
            return;
        }
        if(!isValidLastName.getValue()){
            lastNameErrorText.setText("Invalid last name format. Please enter a valid last name.");
            return;
        }

        if (!isValidEmail(email)) {
            emailErrorText.setText("Invalid email format. Please enter a valid email.");
            cinErrorText.setText("");
            return;
        }
        if (!isValidCIN.getValue()) {
            cinErrorText.setText("Invalid CIN format. Please enter a valid number.");
            emailErrorText.setText("");
            return;
        }
        if(!isValidPassword.getValue()){
            passwordErrorText.setText("Invalid password format. Please enter a valid password.");
            return;
        }


        Subscription a1 = new Subscription();
        Reader lecteur = new Reader(cin, nom, prenom, email, password, dateNaissance, a1, 0);
        DatabaseConnection conn = new DatabaseConnection();


        try {
            ReaderService.addReaderToDatabase(lecteur, DatabaseConnection.getConnection());
            showInfoAlert();
            resetForm();
        } catch (Exception e) {
            if (e.getMessage().contains("A user already exists with this CIN")) {
                cinErrorText.setText("CIN already exists. Please choose a different CIN.");
                cinErrorText.setText("");
            } else if (e.getMessage().contains("A user already exists with this email")) {
                emailErrorText.setText("A user already exists with this email.");
                cinErrorText.setText("");
            } else {
               e.printStackTrace();
            }
        }
    }

    private void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Email already exists. Please choose a different email.");
        alert.showAndWait();
    }

    private void showInfoAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Inscription successful");
        alert.setHeaderText(null);
        alert.setContentText("You signed up successfully!");
        alert.showAndWait();
    }

    private void resetForm() {
        // Clear input fields
        firstNameTextField.clear();
        lastNameTextField.clear();
        cinTextField.clear();
        emailTextField.clear();
        passwordField.clear();
        datePicker.setValue(null);
        cinErrorText.setText("");
        emailErrorText.setText("");
    }
    private void addCINValidationListener() {
        // Add listener to cinTextField for real-time validation
        cinTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isValidCIN.set(isValidCIN(newValue));
            if (!isValidCIN.getValue()) {
                cinErrorText.setText("Invalid CIN format. Please enter a valid number.");
            } else {
                cinErrorText.setText("");
            }
        });
    }
    private void addEmailValidationListener() {
        emailTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isValidEmail.set(isValidEmail(newValue));
            if (!isValidEmail.getValue()) {
                emailErrorText.setText("Email doesn't match the format");
            } else {
                emailErrorText.setText("");
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
    private void addPasswordValidationListener(){
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            isValidPassword.set(isValidPassword(newValue));
            if(!isValidPassword.getValue()){
                passwordErrorText.setText("Password should have a minimu of 8 characters, at least one uppercase letter, one lowercase letter, one number and one special character");
            }
            else{
                passwordErrorText.setText("");
            }
        });
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


    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
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
    private  boolean isValidPassword(String password){
        String passwordRegex="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return password.matches(passwordRegex);

    }
    @FXML
    private void redirectToLogInScreen(ActionEvent event) {
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


}