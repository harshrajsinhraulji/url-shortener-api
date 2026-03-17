

package com.harsh.urlshortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
// This catches exceptions across the entire app
public class GlobalExceptionHandler {

    // ===============================
    // HANDLE VALIDATION ERRORS
    // ===============================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    // ===============================
    // HANDLE NOT FOUND
    // ===============================
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {

        Map<String, Object> error = new HashMap<>();
        error.put("error", ex.getMessage());
        error.put("status", 404);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // ===============================
    // HANDLE GENERIC ERRORS
    // ===============================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {

        Map<String, Object> error = new HashMap<>();
        error.put("error", ex.getMessage());
        error.put("status", 500);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}