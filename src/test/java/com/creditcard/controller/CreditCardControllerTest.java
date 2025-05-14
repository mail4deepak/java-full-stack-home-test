package com.creditcard.controller;

import com.creditcard.model.CreditCard;
import com.creditcard.model.CreditCardRequest;
import com.creditcard.service.CreditCardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CreditCardController.class)
@DisplayName("CreditCardController Tests")
class CreditCardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreditCardService creditCardService;

    @Test
    @DisplayName("Should create credit card with valid request")
    void shouldCreateCreditCardWithValidRequest() throws Exception {
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

        when(creditCardService.createCard(any(CreditCardRequest.class))).thenReturn(expectedCard);

        // Act & Assert
        mockMvc.perform(post("/api/v1/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(request.getName()))
                .andExpect(jsonPath("$.cardNumber").value(request.getCardNumber()))
                .andExpect(jsonPath("$.limit").value(request.getLimit().doubleValue()))
                .andExpect(jsonPath("$.balance").value(0));
    }

    @Test
    @DisplayName("Should return validation error for invalid request")
    void shouldReturnValidationErrorForInvalidRequest() throws Exception {
        // Arrange
        CreditCardRequest request = new CreditCardRequest();
        request.setName("John Doe");
        request.setCardNumber("1234567890123456"); // Invalid card number
        request.setLimit(new BigDecimal("-1000")); // Invalid limit

        // Act & Assert
        mockMvc.perform(post("/api/v1/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation Error"))
                .andExpect(jsonPath("$.errors").exists());
    }

    @Test
    @DisplayName("Should get all credit cards")
    void shouldGetAllCreditCards() throws Exception {
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

        List<CreditCard> cards = Arrays.asList(card1, card2);
        when(creditCardService.getAllCards()).thenReturn(cards);

        // Act & Assert
        mockMvc.perform(get("/api/v1/cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(card1.getName()))
                .andExpect(jsonPath("$[0].cardNumber").value(card1.getCardNumber()))
                .andExpect(jsonPath("$[1].name").value(card2.getName()))
                .andExpect(jsonPath("$[1].cardNumber").value(card2.getCardNumber()));
    }
} 