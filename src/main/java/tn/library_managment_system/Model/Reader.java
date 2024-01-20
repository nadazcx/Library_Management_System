package tn.library_managment_system.Model;


import java.time.LocalDate;

public class Reader extends User {

    public double credit;
    public Subscription subscription;

    public Reader(long CIN, String nom, String prenom, String email, String password, String dateOfBirth, Subscription subscription, int subscriptionFee) {
        super(CIN, nom, prenom, email, password, dateOfBirth);
        this.subscription = subscription;
        subscription.setSubscriptionFee(subscriptionFee);
        this.credit = 0;
    }

    public Reader(long CIN, String nom, String prenom, String dateOfBirth, Subscription subscription, int subscriptionFee) {
        super(CIN, nom, prenom, "-1", "-1", dateOfBirth);
        this.subscription = subscription;
        subscription.setSubscriptionFee(subscriptionFee);
        this.credit = 0;
    }
    public Reader(long CIN, String nom, String prenom, String email, String password, LocalDate dateOfBirth, Subscription subscription, int subscriptionFee) {
        super(CIN, nom, prenom, email, password, dateOfBirth);
        this.subscription = subscription;
        subscription.setSubscriptionFee(subscriptionFee);
        this.credit = 0;
    }

    public double getSubscriptionFee() {
        return subscription.getSubscriptionFee();
    }

    public Subscription getSubscription() {
        return subscription;
    }

    // Uncomment the following methods if needed and provide the necessary implementation
//    void payCredit(long amount) throws NegativeCreditException {
//        if (this.subscription.getSubscriptionFee() - amount < 0)
//            throw new NegativeCreditException("The amount paid by " + this.nom + " is greater than the subscription fee");
//        this.credit = this.credit - amount;
//        // System.out.println(this.nom + " paid the credit and has " + this.credit + " units remaining");
//    }

//    void borrowBook(Book book) throws ForbiddenLoanException {
//        LocalDate creationDate = this.getSubscription().getCreationDate();
//        LocalDate today = LocalDate.now();
//        LocalDate oneYearLater = creationDate.plus(1, ChronoUnit.YEARS);
//
//        if (oneYearLater.isBefore(today) || oneYearLater.isEqual(today)) {
//            this.subscription.borrowedBooks.add(book);
//            // Add the book borrowing logic here, including the return date
//        } else {
//            throw new ForbiddenLoanException();
//        }
//    }
}
