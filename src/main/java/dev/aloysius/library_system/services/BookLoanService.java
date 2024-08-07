package dev.aloysius.library_system.services;

import dev.aloysius.library_system.customExceptions.CustomException;
import dev.aloysius.library_system.models.BookLoans;
import dev.aloysius.library_system.models.Books;
import dev.aloysius.library_system.models.Users;
import dev.aloysius.library_system.repositories.BookLoanRepository;
import dev.aloysius.library_system.repositories.BooksRepository;
import dev.aloysius.library_system.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookLoanService {
    private final BookLoanRepository bookLoanRepository;
    private final UserRepository userRepository;

   private final BooksRepository booksRepository;

    public BookLoanService(BookLoanRepository bookLoanRepository, UserRepository userRepository, BooksRepository booksRepository) {
        this.bookLoanRepository = bookLoanRepository;
        this.userRepository = userRepository;
        this.booksRepository = booksRepository;
    }

    public List<BookLoans> findAll() {
        return bookLoanRepository.findAll();
    }
@Transactional
    public void newLoan(int bookId, int userId) throws CustomException {
        Users users = userRepository.findById(userId).orElseThrow(() -> new CustomException("user with id : %s does not exists".formatted(userId)));
        Books books = booksRepository.findById(bookId).orElseThrow(() -> new CustomException("Book with Id: %s does not exists".formatted(bookId)));
    BookLoans bookLoans = new BookLoans();
    bookLoans.setBooks(books);
    bookLoans.setUsers(users);
    bookLoans.setLoanDate(LocalDate.now());
    books.setCopiesAvailable(books.getCopiesAvailable()-1);
    bookLoanRepository.save(bookLoans);
    booksRepository.save(books);
    }

    public BookLoans getLoansById(int loanId) throws CustomException {
       return bookLoanRepository.findById(loanId).orElseThrow(() -> new CustomException("There is no record or entry for loan corresponding to id: %s".formatted(loanId)));
    }

    public void returnLoanedBook(int loanId) throws CustomException {
        BookLoans bookLoans = bookLoanRepository.findById(loanId).orElseThrow(() -> new CustomException("There is no record or entry for loan corresponding to id: %s".formatted(loanId)));
        bookLoans.setReturnDate(LocalDate.now());
        Books books = booksRepository.findById(bookLoans.getBooks().getId()).orElseThrow();
        books.setCopiesAvailable(books.getCopiesAvailable()+1);
        bookLoanRepository.save(bookLoans);
        booksRepository.save(books);
    }
}
