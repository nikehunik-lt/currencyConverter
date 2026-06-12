package com.currency.exception;

import com.currency.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public Object handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request, Model model) {
        log.warn("Resource not found at {}: {}", request.getRequestURI(), ex.getMessage());
        if (request.getRequestURI().startsWith("/api/")) {
            ErrorResponse error = new ErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    ex.getMessage(),
                    request.getRequestURI()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public Object handleGlobalException(Exception ex, HttpServletRequest request, Model model) {
        log.error("Unexpected error occurred at " + request.getRequestURI(), ex);
        if (request.getRequestURI().startsWith("/api/")) {
            ErrorResponse error = new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    "An unexpected error occurred",
                    request.getRequestURI()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
        model.addAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
        return "error";
    }
}