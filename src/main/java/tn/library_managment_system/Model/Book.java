package tn.library_managment_system.Model;

import javafx.beans.value.ObservableValue;

public class Book {

    private String ISBN;

    private String title;
    private  Author author;
    private long code;
    private  int numberOfCopies;
    private String types;
    private String description;
    public String imageURL;


    public Author getAuthor() {
        return author;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public String getTypes() {
        return types;
    }

    public String getDescription() {
        return description;
    }

    public Book(String ISBN, String title, Author author, long code, int numberOfCopies, String types, String description) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.code = code;
        this.numberOfCopies = numberOfCopies;
        this.types = types;
        this.description = description;


    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Book(String ISBN, String title, Author author, long code, int numberOfCopies, String types, String description, String imageURL) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.code = code;
        this.numberOfCopies = numberOfCopies;
        this.types = types;
        this.description = description;
        this.imageURL=imageURL;

    }
    public Book(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getISBN() {
        return ISBN;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public String toString() {
        return "model.Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' + ",type= " + types +
                '}';
    }

    public int compare(Book b1) {
        return title.compareToIgnoreCase(b1.title);
    }

    static int compare(Book b1, Book b2) {
        return b1.title.compareToIgnoreCase(b2.title);
    }

    public String getImageURL() {
        return imageURL;
    }


}
