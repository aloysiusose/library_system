package dev.aloysius.library_system.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aloysius.library_system.models.BookLoans;
import dev.aloysius.library_system.models.Books;
import dev.aloysius.library_system.models.Users;
import dev.aloysius.library_system.services.BookLoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
@WebMvcTest(BookLoanController.class)
class BookLoanControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookLoanService bookLoanService;

    private ObjectMapper objectMapper;

    private BookLoans loan1;
    private BookLoans loan2;
    private Users user1;
    private Books book1;
    private List<BookLoans> loansList;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        user1 = new Users();
        user1.setId(1);
        user1.setName("User One");
        user1.setEmail("user1@example.com");

        book1 = new Books();
        book1.setAuthor("Author 1");
        book1.setId(1);

        loan1 = new BookLoans();
        loan1.setId(1);
        loan1.setBooks(book1);
        loan1.setUsers(user1);
        loan1.setLoanDate(LocalDate.now());
        loan2 = new BookLoans();
        loan2.setId(2);
        loan2.setBooks(book1);
        loan2.setUsers(user1);
        loan1.setLoanDate(LocalDate.now().minus(1, ChronoUnit.DAYS));
        loansList = Arrays.asList(loan1, loan2);
    }

    @Test
    void testNewBookLoan() throws Exception {
        doNothing().when(bookLoanService).newLoan(anyInt(), anyInt());

        mockMvc.perform(post("/api/v1/loans").param("userId", "1").param("bookId", "1"))
                .andExpect(status().isOk());

        verify(bookLoanService, times(1)).newLoan(anyInt(), anyInt());
    }
    @Test
    void testGetAllLoadRecords() throws Exception {
        when(bookLoanService.findAll()).thenReturn(loansList);

        mockMvc.perform(get("/api/v1/loans"))
                .andExpect(status().isOk());

        verify(bookLoanService, times(1)).findAll();
    }

    @Test
    void testGetLoans() throws Exception {
        when(bookLoanService.getLoansById(1)).thenReturn(loan1);

        mockMvc.perform(get("/api/v1/loans/1"))
                .andExpect(status().isOk());


        verify(bookLoanService, times(1)).getLoansById(1);
    }

    @Test
    void testReturnLoanedBook() throws Exception {
        doNothing().when(bookLoanService).returnLoanedBook(1);

        mockMvc.perform(put("/api/v1/loans/1"))
                .andExpect(status().isOk());

        verify(bookLoanService, times(1)).returnLoanedBook(1);
    }

}