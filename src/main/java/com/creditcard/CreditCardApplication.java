package com.creditcard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CreditCardApplication {
    private static final Logger logger = LoggerFactory.getLogger(CreditCardApplication.class);

    public static void main(String[] args) {
        logger.info("Starting Credit Card Provider Application");
        SpringApplication.run(CreditCardApplication.class, args);
        logger.info("Credit Card Provider Application started successfully");
    }
} 