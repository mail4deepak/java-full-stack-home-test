package com.creditcard.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CreditCard Model Tests")
class CreditCardTest {

    @Test
    @DisplayName("Should create credit card with valid data")
    void shouldCreateCreditCardWithValidData() {
        // Arrange
        String name = "John Doe";
        String cardNumber = "4532015112830366";
        BigDecimal limit = new BigDecimal("1000");

        // Act
        CreditCard card = CreditCard.builder()
                .name(name)
                .cardNumber(cardNumber)
                .limit(limit)
                .build();

        // Assert
        assertNotNull(card);
        assertEquals(name, card.getName());
        assertEquals(cardNumber, card.getCardNumber());
        assertEquals(limit, card.getLimit());
        assertEquals(BigDecimal.ZERO, card.getBalance());
        assertNotNull(card.getCreatedAt());        
    }

    @Test
    @DisplayName("Should set default balance to zero")
    void shouldSetDefaultBalanceToZero() {
        // Arrange & Act
        CreditCard card = CreditCard.builder()
                .name("John Doe")
                .cardNumber("4532015112830366")
                .limit(new BigDecimal("1000"))
                .build();

        // Assert
        assertEquals(BigDecimal.ZERO, card.getBalance());
    }

    @Test
    @DisplayName("Should create credit card with no args constructor")
    void shouldCreateCreditCardWithNoArgsConstructor() {
        // Act
        CreditCard card = new CreditCard();

        // Assert
        assertNotNull(card);
        assertNull(card.getName());
        assertNull(card.getCardNumber());
        assertNull(card.getLimit());
        assertEquals(BigDecimal.ZERO, card.getBalance());
    }

    // Test for invalid card number format
    // Test for invalid name format
    // Test for invalid limit value
    // Test for invalid balance value
} 