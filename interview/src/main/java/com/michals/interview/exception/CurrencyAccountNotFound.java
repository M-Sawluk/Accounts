package com.michals.interview.exception;

public class CurrencyAccountNotFound extends RuntimeException {
    public CurrencyAccountNotFound(String currency) {
        super("Account not found for currency:" + currency);
    }
}
