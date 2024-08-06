package dev.aloysius.library_system.controllers;

import dev.aloysius.library_system.customExceptions.CustomException;
import dev.aloysius.library_system.models.Books;
import dev.aloysius.library_system.services.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Books> getAllBooks(){
        return bookService.findAll();
    }
    @PostMapping
    public void addBook(@RequestBody Books books) throws CustomException {
        bookService.addBook(books);

    }
    @GetMapping("/{id}")
    public Books getBookById( @PathVariable int id) throws CustomException {
        return bookService.findBookById(id);
    }
    @PutMapping("/{id}")
    public Books updateBookById( @PathVariable int id, Books books) throws CustomException {
        return bookService.updateBook(id, books);
    }
    @DeleteMapping("/{id}")
    public String deleteBookById( @PathVariable int id) throws CustomException {
        return bookService.deleteBooks(id);
    }

}
