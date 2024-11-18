package com.michals.interview.exception;

public class AccountNotFound extends RuntimeException {
    public AccountNotFound(String accountNumber) {
        super("Account not found for number: " + accountNumber);
    }
}
