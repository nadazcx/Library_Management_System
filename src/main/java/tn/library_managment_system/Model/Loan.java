package tn.library_managment_system.Model;


import java.time.LocalDate;

public class Loan {
    private final Book book;
    private User user;
    private final LocalDate loanDate;
    private LocalDate returnDate;
    private boolean returned;

    public Loan(Book book, User user, LocalDate loanDate) {
        this.book = book;
        this.user=user;
        this.loanDate = loanDate;
        returnDate = loanDate.plusDays(15);
    }


    public Book getBook() {
        return book;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public User getUser() {
        return user;
    }

    public boolean isReturned() {
        return returned;
    }
}
