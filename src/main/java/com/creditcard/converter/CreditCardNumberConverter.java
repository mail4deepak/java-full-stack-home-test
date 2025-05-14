package com.creditcard.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@Converter
public class CreditCardNumberConverter implements AttributeConverter<String, String> {

    private static TextEncryptor textEncryptor;

    @Autowired
    public void setTextEncryptor(TextEncryptor textEncryptor) {
        CreditCardNumberConverter.textEncryptor = textEncryptor;
    }

    @Override
    public String convertToDatabaseColumn(String cardNumber) {
        if (cardNumber == null) {
            return null;
        }
        return textEncryptor.encrypt(cardNumber);
    }

    @Override
    public String convertToEntityAttribute(String encryptedCardNumber) {
        if (encryptedCardNumber == null) {
            return null;
        }
        return textEncryptor.decrypt(encryptedCardNumber);
    }
} 