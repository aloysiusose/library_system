package dev.aloysius.library_system.models;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;

public record BookLoanDTO(int id,
        LocalDate loanDate,
        LocalDate returnDate,
        String user,

        String book) {
}
