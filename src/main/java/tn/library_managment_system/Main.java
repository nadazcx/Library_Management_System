package tn.library_managment_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin/add-book.fxml")));

        // Set the background color to blue

        // Create a scene and set the background
        Scene scene = new Scene(root, 900, 700);
        primaryStage.getIcons().add(new Image(getClass().getResource("/Image/Icon.png").toExternalForm()));


        primaryStage.setTitle("Library Application");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
