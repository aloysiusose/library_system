package dev.aloysius.library_system.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aloysius.library_system.models.Books;
import dev.aloysius.library_system.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ExtendWith(MockitoExtension.class)
@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private ObjectMapper objectMapper;

    private Books book1;
    private Books book2;
    private List<Books> booksList;

    @BeforeEach
    void setUp() {
        //MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();

        book1 = new Books();
        book2 = new Books();
        booksList = Arrays.asList(book1, book2);
    }

    @Test
    void getAllBooks() throws Exception {
        when(bookService.findAll()).thenReturn(booksList);

        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(book1.getId()))
                .andExpect(jsonPath("$[0].title").value(book1.getTitle()))
                .andExpect(jsonPath("$[0].author").value(book1.getAuthor()))
                .andExpect(jsonPath("$[1].id").value(book2.getId()))
                .andExpect(jsonPath("$[1].title").value(book2.getTitle()))
                .andExpect(jsonPath("$[1].author").value(book2.getAuthor()));

        verify(bookService, times(1)).findAll();
    }

    @Test
    void addBook() throws Exception {
        doNothing().when(bookService).addBook(any(Books.class));

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book1)))
                .andExpect(status().isOk());

        verify(bookService, times(1)).addBook(any(Books.class));
    }

    @Test
    void getBookById() throws Exception {
        when(bookService.findBookById(1)).thenReturn(book1);

        mockMvc.perform(get("/api/v1/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book1.getId()))
                .andExpect(jsonPath("$.title").value(book1.getTitle()))
                .andExpect(jsonPath("$.author").value(book1.getAuthor()));

        verify(bookService, times(1)).findBookById(1);
    }
    @Test
    void updateBookById() throws Exception {
        when(bookService.updateBook(eq(1), any(Books.class))).thenReturn(book1);

        mockMvc.perform(put("/api/v1/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book1.getId()))
                .andExpect(jsonPath("$.title").value(book1.getTitle()))
                .andExpect(jsonPath("$.author").value(book1.getAuthor()));

        verify(bookService, times(1)).updateBook(eq(1), any(Books.class));
    }

    @Test
    void deleteBookById() throws Exception {
        when(bookService.deleteBooks(1)).thenReturn("Book deleted successfully");

        mockMvc.perform(delete("/api/v1/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book deleted successfully"));

        verify(bookService, times(1)).deleteBooks(1);
    }
}