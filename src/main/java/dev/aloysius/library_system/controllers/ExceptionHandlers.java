package dev.aloysius.library_system.controllers;

import dev.aloysius.library_system.customExceptions.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler
    public ResponseEntity<String> handleException(CustomException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
