module tn.library_managment_system {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires java.desktop;
    requires javafx.graphics;
    opens tn.library_managment_system.Model to javafx.base;

    opens tn.library_managment_system to javafx.fxml;
    exports tn.library_managment_system;
    exports tn.library_managment_system.Controller;
    opens tn.library_managment_system.Controller to javafx.fxml;
}