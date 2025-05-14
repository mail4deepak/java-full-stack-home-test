package com.creditcard.service;

import com.creditcard.model.CreditCard;
import com.creditcard.model.CreditCardRequest;
import com.creditcard.repository.CreditCardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreditCardService Tests")
class CreditCardServiceTest {
    @Mock
    private CreditCardRepository creditCardRepository;

    @InjectMocks
    private CreditCardService creditCardService;

    @Test
    @DisplayName("Should create new credit card")
    void shouldCreateNewCreditCard() {
        // Arrange
        CreditCardRequest request = new CreditCardRequest();
        request.setName("John Doe");
        request.setCardNumber("4532015112830366");
        request.setLimit(new BigDecimal("1000"));

        CreditCard expectedCard = CreditCard.builder()
                .name(request.getName())
                .cardNumber(request.getCardNumber())
                .limit(request.getLimit())
                .balance(BigDecimal.ZERO)
                .build();

        when(creditCardRepository.save(any(CreditCard.class))).thenReturn(expectedCard);

        // Act
        CreditCard result = creditCardService.createCard(request);

        // Assert
        assertNotNull(result);
        assertEquals(request.getName(), result.getName());
        assertEquals(request.getCardNumber(), result.getCardNumber());
        assertEquals(request.getLimit(), result.getLimit());
        assertEquals(BigDecimal.ZERO, result.getBalance());
        verify(creditCardRepository, times(1)).save(any(CreditCard.class));
    }

    @Test
    @DisplayName("Should get all credit cards")
    void shouldGetAllCreditCards() {
        // Arrange
        CreditCard card1 = CreditCard.builder()
                .name("John Doe")
                .cardNumber("4532015112830366")
                .limit(new BigDecimal("1000"))
                .build();

        CreditCard card2 = CreditCard.builder()
                .name("Jane Doe")
                .cardNumber("6011000000000012")
                .limit(new BigDecimal("2000"))
                .build();

        List<CreditCard> expectedCards = Arrays.asList(card1, card2);
        when(creditCardRepository.findAll()).thenReturn(expectedCards);

        // Act
        List<CreditCard> result = creditCardService.getAllCards();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedCards, result);
        verify(creditCardRepository, times(1)).findAll();
    }
} 