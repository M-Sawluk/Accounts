package com.michals.interview.service.factory;

import com.michals.interview.domain.Exchange;
import com.michals.interview.model.ExchangeRequest;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@UtilityClass
public class ExchangeFactory {

    public static Exchange createExchange(ExchangeRequest exchangeRequest, BigDecimal exchangeRate, String accountNumber,
                                          BigDecimal exchangedAmount) {
        return Exchange.builder()
                .exchangeDate(LocalDate.now())
                .amountFrom(exchangeRequest.amount())
                .currencyFrom(exchangeRequest.currencyFrom())
                .exchangeRate(exchangeRate)
                .mainAccountNumber(UUID.fromString(accountNumber))
                .currencyTo(exchangeRequest.currencyTo())
                .amountTo(exchangedAmount)
                .build();
    }
}
