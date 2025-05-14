package com.creditcard.controller;

import com.creditcard.model.CreditCard;
import com.creditcard.model.CreditCardRequest;
import com.creditcard.service.CreditCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CreditCardController {
    private static final Logger logger = LoggerFactory.getLogger(CreditCardController.class);
    private final CreditCardService creditCardService;

    @PostMapping
    public ResponseEntity<CreditCard> addCard(@Valid @RequestBody CreditCardRequest request) {
        logger.debug("Received request to add new credit card for user: {}", request.getName());
        CreditCard card = creditCardService.createCard(request);
        logger.info("Successfully added credit card with ID: {}", card.getId());
        return ResponseEntity.ok(card);
    }

    @GetMapping
    public ResponseEntity<List<CreditCard>> getAllCards() {
        logger.debug("Received request to get all credit cards");
        List<CreditCard> cards = creditCardService.getAllCards();
        logger.info("Successfully retrieved {} credit cards", cards.size());
        return ResponseEntity.ok(cards);
    }
} 