package com.creditcard.model;

import com.creditcard.converter.CreditCardNumberConverter;
import com.creditcard.validation.CreditCardNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "credit_cards")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be less than 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Name can only contain letters and spaces")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Card number is required")
    @Pattern(regexp = "^\\d{1,19}$", message = "Card number must be numeric and between 1-19 digits")
    @CreditCardNumber(message = "Invalid card number")
    @Column(nullable = false, length = 255, unique = true)
    @Convert(converter = CreditCardNumberConverter.class)
    private String cardNumber;

    @NotNull(message = "Limit is required")
    @Positive(message = "Limit must be positive")
    @Max(value = 1000000, message = "Limit cannot exceed £1,000,000")
    @Column(name = "maxlimit", nullable = false, precision = 19, scale = 2)
    private BigDecimal limit;

    @Builder.Default
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Builder.Default
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
} 