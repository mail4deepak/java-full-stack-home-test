package com.creditcard.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Luhn10Validator implements ConstraintValidator<CreditCardNumber, String> {
    private static final Logger logger = LoggerFactory.getLogger(Luhn10Validator.class);
    /*
     * The Luhn algorithm, also known as Luhn-10 or Mod 10, is a checksum formula used to validate various identification numbers, 
     * including credit card numbers. It's a simple mathematical check that helps identify potential errors in data entry by verifying 
     * the numerical validity of a sequence of digits. While effective at catching many errors, it's not foolproof and shouldn't be 
     * solely relied upon for security. 
     * 
     * Here's a simplified explanation of the Luhn algorithm:
     *  Double every second digit: Starting from the rightmost digit (the check digit), double every second digit in the number.
     *  Adjust doubled values: If a doubled digit is greater than 9, subtract 9 from it.
     *  Sum all digits: Add up all the original digits and the adjusted doubled digits.
     *  Check the remainder: If the sum is divisible by 10, the number is considered potentially valid. 
     */
    @Override
    public boolean isValid(String cardNumber, ConstraintValidatorContext context) {
        logger.debug("Validating card number: {}", maskCardNumber(cardNumber));
        
        if (cardNumber == null || cardNumber.isEmpty()) {
			logger.warn("Card number is null or empty");
            return false;
        }
        if (cardNumber.length() < 1 || cardNumber.length() > 19) {
            return false; // Length check for typical credit card numbers
        }
        try {
            long val = Long.parseLong(cardNumber); // Check if the card number is numeric
            if (val == 0) {
                return false; // credit card number cannot be all zeros
            }
        } catch (NumberFormatException e) {
            return false; // Not a valid number
        }
        

        int sum = 0;
        boolean alternate = false;
        
        for (int i = cardNumber.length() - 1; i >= 0; i--) {            
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        
        boolean isValid = (sum % 10 == 0);
        logger.info("Card number {} validation result: {}", maskCardNumber(cardNumber), isValid);
        return isValid;
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        return "****" + cardNumber.substring(cardNumber.length() - 4);
    }
} 