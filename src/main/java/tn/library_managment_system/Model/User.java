package tn.library_managment_system.Model;


import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class User {
    private long CIN;
    private String name, lastName;
    private String email;
    private String password;
    private LocalDate birthDate;
    private int user_Id;

    public User(long CIN, String name, String lastName, String email, String password, String birthDate){
        this.CIN = CIN;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            this.birthDate = LocalDate.parse(birthDate, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use the format 'dd/MM/yyyy'.");
        }
    }

    public User(long cin, String name, String lastName, String email, String password, LocalDate BirthDate) {

        this.CIN = cin;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthDate = BirthDate;
    }
    public  User (long cin){
        this.CIN=cin;
    }


    public int getAge() {
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(birthDate, currentDate);
        return age.getYears();
    }

    public long getCIN() {
        return CIN;
    }

    public void setCIN(long id) {
        this.CIN = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    public LongProperty cinProperty() {
        return new SimpleLongProperty(CIN);
    }

    public StringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public StringProperty lastNameProperty() {
        return new SimpleStringProperty(lastName);
    }

    public StringProperty emailProperty() {
        return new SimpleStringProperty(email);
    }
}