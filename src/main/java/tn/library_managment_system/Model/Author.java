package tn.library_managment_system.Model;

public class Author {
    private long id;
    private final String lastName;
    private final String firstName;

    public Author(long id ,String lastName, String firstName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }
    public long getId()  {return id; }
}
