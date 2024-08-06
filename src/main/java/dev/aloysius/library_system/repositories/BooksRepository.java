package dev.aloysius.library_system.repositories;

import dev.aloysius.library_system.models.Books;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BooksRepository extends JpaRepository<Books, Integer> {
    Optional<Books> findById(int id);
}
