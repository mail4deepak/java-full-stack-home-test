package com.creditcard.exception;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        logger.debug("Handling validation exception");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            logger.warn("Validation error in field '{}': {}", fieldName, errorMessage);
        });
        logger.info("Returning validation error response with {} errors", errors.size());
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("Validation Error", errors));
    }

    @ExceptionHandler(DuplicateCardNumberException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationExceptions(
        DuplicateCardNumberException ex) {
        logger.debug("Handling Constraint violation exception");
        Map<String, String> errors = new HashMap<>();        
        errors.put("cardNumber", ex.getMessage());
            
        logger.info("Returning Constraint violation error response with {} errors", errors.size());
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("Validation Error", errors));
    }

    @Data
    public static class ErrorResponse {
        private final String message;
        private final Map<String, String> errors;
    }
} 