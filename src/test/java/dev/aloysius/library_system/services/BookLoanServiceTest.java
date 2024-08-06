package dev.aloysius.library_system.services;

import dev.aloysius.library_system.customExceptions.CustomException;
import dev.aloysius.library_system.models.BookLoans;
import dev.aloysius.library_system.models.Books;
import dev.aloysius.library_system.models.Users;
import dev.aloysius.library_system.repositories.BookLoanRepository;
import dev.aloysius.library_system.repositories.BooksRepository;
import dev.aloysius.library_system.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookLoanServiceTest {
    @Mock
    private BookLoanRepository bookLoanRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BooksRepository booksRepository;

    @InjectMocks
    private BookLoanService bookLoanService;

    private Users user1;
    private Books book1;
    private BookLoans loan1;
    private BookLoans loan2;

    @BeforeEach
    void setUp() {

        user1 = new Users();
        user1.setId(1);
        user1.setName("User One");
        user1.setEmail("user1@example.com");

        book1 = new Books();
        book1.setAuthor("Author 1");
        book1.setId(1);

        loan1 = new BookLoans();
        loan1.setBooks(book1);
        loan1.setUsers(user1);
        loan1.setLoanDate(LocalDate.now());
        loan2 = new BookLoans();
        loan2.setBooks(book1);
        loan2.setUsers(user1);
        loan1.setLoanDate(LocalDate.now().minus(1, ChronoUnit.DAYS));
    }

    @Test
    void testFindAll() {
        List<BookLoans> loans = Arrays.asList(loan1, loan2);
        when(bookLoanRepository.findAll()).thenReturn(loans);

        List<BookLoans> result = bookLoanService.findAll();

        assertEquals(2, result.size());
        verify(bookLoanRepository, times(1)).findAll();
    }

    @Test
    void testNewLoan() throws CustomException {
        when(userRepository.findById(1)).thenReturn(Optional.of(user1));
        when(booksRepository.findById(1)).thenReturn(Optional.of(book1));
        when(bookLoanRepository.save(any(BookLoans.class))).thenReturn(loan1);

        bookLoanService.newLoan(1, 1);

        verify(userRepository, times(1)).findById(1);
        verify(booksRepository, times(1)).findById(1);
        verify(bookLoanRepository, times(1)).save(any(BookLoans.class));
    }

    @Test
    void testNewLoanUserNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> {
            bookLoanService.newLoan(1, 1);
        });

        assertEquals("user with id : 1 does not exists", exception.getMessage());
        verify(userRepository, times(1)).findById(1);
        verify(booksRepository, never()).findById(anyInt());
        verify(bookLoanRepository, never()).save(any(BookLoans.class));
    }

    @Test
    void testNewLoanBookNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user1));
        when(booksRepository.findById(1)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> {
            bookLoanService.newLoan(1, 1);
        });

        assertEquals("Book with Id: 1 does not exists", exception.getMessage());
        verify(userRepository, times(1)).findById(1);
        verify(booksRepository, times(1)).findById(1);
        verify(bookLoanRepository, never()).save(any(BookLoans.class));
    }

    @Test
    void testGetLoansById() throws CustomException {
        when(bookLoanRepository.findById(1)).thenReturn(Optional.of(loan1));

        BookLoans result = bookLoanService.getLoansById(1);

        assertNotNull(result);
        assertEquals(loan1.getId(), result.getId());
        verify(bookLoanRepository, times(1)).findById(1);
    }

    @Test
    void testGetLoansByIdNotFound() {
        when(bookLoanRepository.findById(1)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> {
            bookLoanService.getLoansById(1);
        });

        assertEquals("There is no record or entry for loan corresponding to id: 1", exception.getMessage());
        verify(bookLoanRepository, times(1)).findById(1);
    }

    @Test
    void testReturnLoanedBook() throws CustomException {
        when(bookLoanRepository.findById(1)).thenReturn(Optional.of(loan1));
        when(bookLoanRepository.save(any(BookLoans.class))).thenReturn(loan1);

        bookLoanService.returnLoanedBook(1);

        verify(bookLoanRepository, times(1)).findById(1);
        verify(bookLoanRepository, times(1)).save(any(BookLoans.class));
    }

    @Test
    void testReturnLoanedBookNotFound() {
        when(bookLoanRepository.findById(1)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> {
            bookLoanService.returnLoanedBook(1);
        });

        assertEquals("There is no record or entry for loan corresponding to id: 1", exception.getMessage());
        verify(bookLoanRepository, times(1)).findById(1);
        verify(bookLoanRepository, never()).save(any(BookLoans.class));
    }

}