package com.creditcard.service;

import com.creditcard.exception.DuplicateCardNumberException;
import com.creditcard.model.CreditCard;
import com.creditcard.model.CreditCardRequest;
import com.creditcard.repository.CreditCardRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditCardService {
    private static final Logger logger = LoggerFactory.getLogger(CreditCardService.class);
    private final CreditCardRepository creditCardRepository;

    public CreditCard createCard(CreditCardRequest request) {
        logger.debug("Creating new credit card for user: {}", request.getName());
        CreditCard card = CreditCard.builder()
                .name(request.getName())
                .cardNumber(request.getCardNumber())
                .limit(request.getLimit())
                .balance(BigDecimal.ZERO)
                .build();        
        if (creditCardRepository.findByCardNumber(card.getCardNumber()) != null)
            throw new DuplicateCardNumberException("Duplicate card number");
        
        CreditCard savedCard = creditCardRepository.save(card);
        logger.info("Successfully created credit card with ID: {}", savedCard.getId());
        return savedCard;
    }

    public List<CreditCard> getAllCards() {
        logger.debug("Retrieving all credit cards");
        List<CreditCard> cards = creditCardRepository.findAll();
        logger.info("Retrieved {} credit cards", cards.size());
        return cards;
    }
} 