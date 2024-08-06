package dev.aloysius.library_system.repositories;

import dev.aloysius.library_system.models.BookLoans;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookLoanRepository extends JpaRepository<BookLoans, Integer> {
    Optional<BookLoans> findById(int id);
}
