package com.bookstore.web.clients.Config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles FeignExceptions by mapping specific backend HTTP status codes
     * to user-friendly error messages on the frontend.
     *
     * @param ex the FeignException thrown during the API call
     * @return a ResponseEntity with a customized error map
     */
    @ExceptionHandler(feign.FeignException.class)
    public ResponseEntity<Map<String, Object>> handleFeignException(feign.FeignException ex) {
        int status = ex.status() > 0 ? ex.status() : 500;

        // Grab the raw body from the backend (the JSON string)
        String backendMessage = ex.contentUTF8();

        String customMessage = switch (status) {
            case 401 -> "You must be logged in to perform this action.";
            case 403 -> "You must be signed in as an admin to perform this action.";
            case 404 -> "The requested resource was not found on the server.";
            case 409 -> (backendMessage != null && !backendMessage.isEmpty())
                    ? backendMessage
                    : "This action could not be completed due to a conflict.";
            case 500 -> "Oops. Something blew up on the server.";
            default -> "An unexpected error occurred: " + status;
        };

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status);
        errorResponse.put("error", ex.getMessage());
        errorResponse.put("message", customMessage);
        errorResponse.put("timestamp", System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(status));
    }
}
