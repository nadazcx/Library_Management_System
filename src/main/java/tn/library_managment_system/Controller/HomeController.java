package tn.library_managment_system.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import tn.library_managment_system.Model.Book;
import tn.library_managment_system.Service.BookService;
import tn.library_managment_system.util.DatabaseConnection;
import javafx.scene.image.Image;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class HomeController {
    public ListView bookTypesListView;
    public ListView booksListView;
    public Label descriptionLabel;
    public ImageView bookImageView;

    public void initialize() {
        try {
            Map<String, List<Book>> map = BookService.groupBooksByType(DatabaseConnection.getConnection());

            bookTypesListView.getItems().addAll(map.keySet());

            bookTypesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                List<Book> books = map.get(newValue);
                booksListView.getItems().clear();
                for (Book book : books) {
                    booksListView.getItems().add(book.getTitle());
                }
            });

            booksListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {


                String selectedType = bookTypesListView.getSelectionModel().getSelectedItem().toString();

                String selectedBookTitle = booksListView.getSelectionModel().getSelectedItem().toString();
                //urlabel
                //description label
                List<Book> books = map.get(selectedType);
                Book book= books.stream().filter(b -> b.getTitle().equals(selectedBookTitle)).findFirst().get();
//                bookImageView.setText(book.getImageURL());
//                Image image = new Image(book.getImageURL());
//                  Image image=new Image(getClass().getResource("/Image/3643090.jpeg").toExternalForm());
                    String picURL;
                    if(book.getImageURL()!=null){
                          picURL="/Image/"+book.getImageURL().trim();}
                    else{
                          picURL="/Image/0.jpeg";
                    }

                  Image image=new Image(getClass().getResource(picURL).toExternalForm());

//                    Image image=new Image(getClass().getResource(book.getImageURL()).toExternalForm());
                    bookImageView.setImage(image);
                    bookImageView.setFitWidth(250); // Set the desired width
                    bookImageView.setFitHeight(250); // Set the desired height
                    bookImageView.setPreserveRatio(true);


                    bookImageView.setImage(image);


                descriptionLabel.setText(book.getDescription());
                    System.out.println(book.getDescription());


                System.out.println("Selected Type: " + selectedType);
                System.out.println("Selected Book Title: " + selectedBookTitle);}

           });

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    @FXML
    private void redirectToSignInScreen(ActionEvent event) {
        SigninController Sn = new SigninController();
        Sn.redirectToSignInScreen(event);


    }
    @FXML
    private void redirectToLogInScreen(ActionEvent event) {
        LoginController LC=new LoginController();
        LC.redirectToLogInScreen(event);
    }





}
