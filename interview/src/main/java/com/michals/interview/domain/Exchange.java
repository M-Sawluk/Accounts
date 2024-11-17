package com.michals.interview.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Exchange {

    @Id
    @GeneratedValue
    private Long id;

    private UUID mainAccountNumber;

    private BigDecimal exchangeRate;

    private BigDecimal amountFrom;

    private Currency currencyFrom;

    private BigDecimal amountTo;

    private Currency currencyTo;
}
