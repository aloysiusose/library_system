package dev.aloysius.library_system.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class BookLoans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate loanDate;
    private LocalDate returnDate;
    @OneToOne
    @JoinColumn(name = "user_id")
    private Users users;
    @OneToOne
    @JoinColumn(name = "book_id")
    private Books books;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }
}
