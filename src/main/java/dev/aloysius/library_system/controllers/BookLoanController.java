package dev.aloysius.library_system.controllers;

import dev.aloysius.library_system.customExceptions.CustomException;
import dev.aloysius.library_system.models.BookLoanDTO;
import dev.aloysius.library_system.models.BookLoans;
import dev.aloysius.library_system.services.BookLoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/loans")
public class BookLoanController {
    private final BookLoanService bookLoanService;

    public BookLoanController(BookLoanService bookLoanService) {
        this.bookLoanService = bookLoanService;
    }
    @PostMapping()
    public void newBookLoan(@RequestParam("bookId") int bookId, @RequestParam("userId") int userId) throws CustomException {
        bookLoanService.newLoan(bookId, userId);
    }
    @GetMapping
    public List<BookLoanDTO> getAllLoadRecords(){
        return bookLoanService.findAll()
                .stream().map(this::toBookLoanDTO).collect(Collectors.toList());
    }
    @GetMapping("/{loanId}")
    public BookLoanDTO getLoans(@PathVariable("loanId") int loanId) throws CustomException {
        return toBookLoanDTO(bookLoanService.getLoansById(loanId));
    }
    @PutMapping("/{loanId}")
    public void returnLoanedBook(@PathVariable int loanId) throws CustomException {
        bookLoanService.returnLoanedBook(loanId);
    }

    private BookLoanDTO toBookLoanDTO(BookLoans bookLoans){
        return new BookLoanDTO(bookLoans.getId(), bookLoans.getLoanDate(), bookLoans.getReturnDate(), bookLoans.getUsers().getName(), bookLoans.getBooks().getTitle());
    }
}
