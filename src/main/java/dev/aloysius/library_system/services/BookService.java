package dev.aloysius.library_system.services;

import dev.aloysius.library_system.customExceptions.CustomException;
import dev.aloysius.library_system.models.Books;
import dev.aloysius.library_system.repositories.BooksRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BooksRepository booksRepository;

    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Books> findAll() {
        return booksRepository.findAll();
    }

    public void addBook(Books books) throws CustomException {
        if (booksRepository.findById(books.getId()).isPresent()) {
            throw new CustomException("Books with id: %s already Exists".formatted(books.getId()));
        }
        else {
            booksRepository.save(books);
        }

    }

    public Books findBookById(int id) throws CustomException {
       return booksRepository.findById(id).orElseThrow(()-> new CustomException("Book with id: %s not found".formatted(id)));
    }

    public Books updateBook(int id, Books books) throws CustomException {
        Books books1 = booksRepository.findById(id).orElseThrow(() -> new CustomException("Book with id: %s not found".formatted(id)));
        books1.setAuthor(books.getAuthor());
        books1.setIsbn(books.getIsbn());
        books1.setTitle(books.getTitle());
        books1.setCopiesAvailable(books.getCopiesAvailable());
        books1.setPublishedYear(books.getPublishedYear());
        return booksRepository.save(books1);
    }

    public String deleteBooks(int id) throws CustomException {
        Books books = booksRepository.findById(id).orElseThrow(() -> new CustomException("Book with id: %s not found".formatted(id)));
        booksRepository.delete(books);
        return "Book with id: %s successfully deleted".formatted(id);
    }
}
