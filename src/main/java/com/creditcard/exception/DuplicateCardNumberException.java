package com.creditcard.exception;

public class DuplicateCardNumberException extends RuntimeException {
    public DuplicateCardNumberException(String message) {
        super("Duplicate card number");
    }

}
