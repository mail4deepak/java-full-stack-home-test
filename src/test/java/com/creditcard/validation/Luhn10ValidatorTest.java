package com.creditcard.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Luhn10Validator Tests")
class Luhn10ValidatorTest {
    private Luhn10Validator validator;

    @BeforeEach
    void setUp() {
        validator = new Luhn10Validator();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "4532015112830366", // Visa
        "6011000000000012", // Discover
        "5424000000000015", // Mastercard
        "378282246310005"   // American Express
    })
    @DisplayName("Should validate correct card numbers")
    void shouldValidateCorrectCardNumber(String cardNumber) {
        assertTrue(validator.isValid(cardNumber, null));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "4532015112830367", // Invalid check digit
        "1234567890123456", // Invalid number
        "0000000000000000"  // Invalid number
    })
    @DisplayName("Should reject invalid card numbers")
    void shouldRejectInvalidCardNumber(String cardNumber) {
        assertFalse(validator.isValid(cardNumber, null));
    }

    @Test
    @DisplayName("Should handle null or empty card number")
    void shouldHandleNullOrEmptyCardNumber() {
        assertFalse(validator.isValid(null, null));
        assertFalse(validator.isValid("", null));
    }

    @Test
    @DisplayName("Should handle non-numeric card number")
    void shouldHandleNonNumericCardNumber() {
        assertFalse(validator.isValid("4532-0151-1283-0366", null));
        assertFalse(validator.isValid("4532 0151 1283 0366", null));
        assertFalse(validator.isValid("4532ABC12830366", null));
    }
} 