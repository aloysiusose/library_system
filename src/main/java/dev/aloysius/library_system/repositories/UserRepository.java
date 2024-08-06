package dev.aloysius.library_system.repositories;

import dev.aloysius.library_system.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findById(int id);
    Optional<Users> findByEmail(String email);
}
