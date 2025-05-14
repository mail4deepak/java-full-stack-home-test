package com.creditcard.repository;

import com.creditcard.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, String> {

    CreditCard findByCardNumber(String cardNumber);
    // JpaRepository provides all basic CRUD operations
    // No need to implement methods as they are provided by Spring Data JPA
} 