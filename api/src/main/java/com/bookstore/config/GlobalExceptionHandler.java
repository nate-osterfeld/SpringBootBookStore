package com.bookstore.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.View;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Centralized exception handler for the API layer.
 * Catches exceptions thrown by controllers and returns structured,
 * meaningful HTTP error responses instead of raw 500 Internal Server Errors.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final View error;

    public GlobalExceptionHandler(View error) {
        this.error = error;
    }

    /**
     * Handles IOExceptions (e.g. file upload failures) and returns
     * a 500 response with a descriptive error message.
     *
     * @param ex the IOException that was thrown
     * @return a ResponseEntity with error details and HTTP 500 status
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<Map<String, String>> handleIOException(IOException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "File processing error");
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles IllegalArgumentExceptions (e.g. invalid author ID) and returns
     * a 400 Bad Request response with a descriptive error message.
     *
     * @param ex the IllegalArgumentException that was thrown
     * @return a ResponseEntity with error details and HTTP 400 status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Invalid request");
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Catches the IllegalStateException from the service layer (e.g. data integrity issues)
     * and returns a 409 Conflict.
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleConflict(IllegalStateException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Conflict");
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
