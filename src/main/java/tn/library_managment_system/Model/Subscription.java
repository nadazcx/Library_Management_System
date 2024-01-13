package tn.library_managment_system.Model;


import java.time.LocalDate;
import java.util.ArrayList;

public class Subscription {
    LocalDate creationDate;
    private double subscriptionFee;
    ArrayList<Loan> loanList = new ArrayList<>();

    public Subscription(ArrayList<Loan> loanList, LocalDate creationDate) {
        this.creationDate = creationDate;
        this.loanList = loanList;
    }

    public Subscription() {

    }

    public double getSubscriptionFee() {
        return subscriptionFee;
    }

    public void setSubscriptionFee(double subscriptionFee) {
        this.subscriptionFee = subscriptionFee;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
}
