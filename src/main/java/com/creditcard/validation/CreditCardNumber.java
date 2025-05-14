package com.creditcard.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = Luhn10Validator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CreditCardNumber {
    String message() default "Invalid credit card number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
} 