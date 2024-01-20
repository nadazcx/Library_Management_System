package tn.library_managment_system.Service;

import tn.library_managment_system.Model.Book;
import tn.library_managment_system.Model.Loan;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

public class LoanService {
   public static void  addNewLoan(Loan loan , Connection conn){
       String sql = "INSERT INTO loan (book_id, user_id, loandate, returndate) VALUES (?,?,?,?)";
       try {
            LocalDate loanDate= LocalDate.now();
              PreparedStatement statement = conn.prepareStatement(sql);
              Long bookCode= BookService.getBookIDByISBN(loan.getBook(),conn);
                Long userId= UserService.getUserId(loan.getUser(),conn);
                statement.setLong(1,bookCode);
              statement.setLong(2,userId);
              statement.setDate(3, Date.valueOf(loanDate));
              statement.setDate(4, java.sql.Date.valueOf(loan.getReturnDate()));
              statement.executeUpdate();



       } catch (SQLException e) {
          e.printStackTrace();
       }


   }
   public static  void returnBook(Loan loan, Connection conn){
       String sql = "UPDATE loan SET `returned` = ? WHERE book_id = ? AND user_id = ? ";
       try {
           PreparedStatement statement = conn.prepareStatement(sql);
           Long bookCode= BookService.getBookIDByISBN(loan.getBook(),conn);
           Long userId= UserService.getUserId(loan.getUser(),conn);
           LocalDate returnDate= LocalDate.now();
           statement.setInt(1,1);

//           statement.setDate(2, Date.valueOf(returnDate));
           statement.setLong(2,bookCode);
           statement.setLong(3,userId);


           statement.executeUpdate();
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }



}

