package tn.library_managment_system.Model;

import java.util.List;

public class Book {

    private long ISBN;

    private String title;
    private final Author author;
    private long code;
    private final int numberOfCopies;
    private final List<String> types;
    private String description;

    public Author getAuthor() {
        return author;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public List<String> getTypes() {
        return types;
    }

    public String getDescription() {
        return description;
    }

    public Book(long ISBN, String title, Author author, long code, int numberOfCopies, List<String> types, String description) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.code = code;
        this.numberOfCopies = numberOfCopies;
        this.types = types;
        this.description = description;
    }

    public long getISBN() {
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

    public void setISBN(long ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public String toString() {
        return "model.Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public int compare(Book b1) {
        return title.compareToIgnoreCase(b1.title);
    }

    static int compare(Book b1, Book b2) {
        return b1.title.compareToIgnoreCase(b2.title);
    }
}
