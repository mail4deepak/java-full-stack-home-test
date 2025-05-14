package com.creditcard.model;

import com.creditcard.validation.CreditCardNumber;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreditCardRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be less than 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Name can only contain letters and spaces")
    private String name;

    @NotBlank(message = "Card number is required")
    @Pattern(regexp = "^\\d{1,19}$", message = "Card number must be numeric and between 1-19 digits")
    @CreditCardNumber(message = "Invalid card number")
    private String cardNumber;

    @NotNull(message = "Limit is required")
    @Positive(message = "Limit must be positive")
    @Max(value = 1000000, message = "Limit cannot exceed £1,000,000")
    private BigDecimal limit;
} 