package com.michals.interview.model;

import com.michals.interview.domain.Currency;

import java.math.BigDecimal;

public record SubAccountDTO(Currency currency, BigDecimal amount) {
}
