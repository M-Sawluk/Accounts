package com.michals.interview.model;

import com.michals.interview.domain.Currency;

import java.math.BigDecimal;

public record ExchangeDTO(BigDecimal amountFrom,
                          Currency currencyFrom,
                          BigDecimal amountTo,
                          Currency currencyTo) {
}
