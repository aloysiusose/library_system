package dev.aloysius.library_system.services;

import dev.aloysius.library_system.customExceptions.CustomException;
import dev.aloysius.library_system.models.Books;
import dev.aloysius.library_system.repositories.BooksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BooksRepository booksRepository;

    @InjectMocks
    private BookService bookService;

    private Books book1;
    private Books book2;

    @BeforeEach
    void setUp() {
        book1 = new Books();
        book1.setAuthor("Author 1");
        book1.setId(1);
        book2 = new Books();
        book2.setId(2);
        book2.setAuthor("Author 2");
    }

    @Test
    void testFindAll() {
        List<Books> booksList = Arrays.asList(book1, book2);
        when(booksRepository.findAll()).thenReturn(booksList);

        List<Books> result = bookService.findAll();

        assertEquals(2, result.size());
        verify(booksRepository, times(1)).findAll();
    }

    @Test
    void testAddBook() throws CustomException {
        when(booksRepository.findById(book1.getId())).thenReturn(Optional.empty());

        bookService.addBook(book1);

        verify(booksRepository, times(1)).save(book1);
    }

    @Test
    void testAddBookThrowsException() {
        when(booksRepository.findById(book1.getId())).thenReturn(Optional.of(book1));

        CustomException exception = assertThrows(CustomException.class, () -> {
            bookService.addBook(book1);
        });

        assertEquals("Books with id: 1 already Exists", exception.getMessage());
        verify(booksRepository, never()).save(book1);
    }
    @Test
    void testFindBookById() throws CustomException {
        when(booksRepository.findById(1)).thenReturn(Optional.of(book1));

        Books result = bookService.findBookById(1);

        assertNotNull(result);
        assertEquals(book1.getTitle(), result.getTitle());
        verify(booksRepository, times(1)).findById(1);
    }

    @Test
    void testFindBookByIdThrowsException() {
        when(booksRepository.findById(1)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> {
            bookService.findBookById(1);
        });

        assertEquals("Book with id: 1 not found", exception.getMessage());
        verify(booksRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateBook() throws CustomException {
        when(booksRepository.findById(1)).thenReturn(Optional.of(book1));
        when(booksRepository.save(any(Books.class))).thenReturn(book1);

        Books updatedBook = new Books();
        updatedBook.setAuthor("Updated Author");
        updatedBook.setTitle("Updated Title");
        updatedBook.setIsbn("Updated ISBN");
        updatedBook.setCopiesAvailable(10);
        updatedBook.setPublishedYear("2022");

        Books result = bookService.updateBook(1, updatedBook);

        assertNotNull(result);
        assertEquals(updatedBook.getTitle(), result.getTitle());
        verify(booksRepository, times(1)).findById(1);
        verify(booksRepository, times(1)).save(book1);
    }

    @Test
    void testUpdateBookThrowsException() {
        when(booksRepository.findById(1)).thenReturn(Optional.empty());

        Books updatedBook = new Books();
        updatedBook.setAuthor("Updated Author");
        updatedBook.setTitle("Updated Title");
        updatedBook.setId(1);

        CustomException exception = assertThrows(CustomException.class, () -> {
            bookService.updateBook(1, updatedBook);
        });

        assertEquals("Book with id: 1 not found", exception.getMessage());
        verify(booksRepository, times(1)).findById(1);
        verify(booksRepository, never()).save(any(Books.class));
    }

    @Test
    void testDeleteBooks() throws CustomException {
        when(booksRepository.findById(1)).thenReturn(Optional.of(book1));

        String result = bookService.deleteBooks(1);

        assertEquals("Book with id: 1 successfully deleted", result);
        verify(booksRepository, times(1)).findById(1);
        verify(booksRepository, times(1)).delete(book1);
    }

    @Test
    void testDeleteBooksThrowsException() {
        when(booksRepository.findById(1)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> {
            bookService.deleteBooks(1);
        });

        assertEquals("Book with id: 1 not found", exception.getMessage());
        verify(booksRepository, times(1)).findById(1);
        verify(booksRepository, never()).delete(any(Books.class));
    }

}