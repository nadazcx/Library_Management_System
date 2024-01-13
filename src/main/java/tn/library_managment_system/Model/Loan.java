package tn.library_managment_system.Model;


import java.time.LocalDate;

public class Loan {
    private final Book book;
    private final LocalDate loanDate;
    private LocalDate returnDate;

    public Loan(Book book, LocalDate loanDate) {
        this.book = book;
        this.loanDate = loanDate;
        returnDate = loanDate.plusDays(7);
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
}
