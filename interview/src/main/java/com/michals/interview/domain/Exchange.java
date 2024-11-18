package com.michals.interview.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Exchange {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private UUID mainAccountNumber;
    @NotNull
    private BigDecimal exchangeRate;
    @NotNull
    private LocalDate exchangeDate;
    @NotNull
    private BigDecimal amountFrom;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Currency currencyFrom;
    @NotNull
    private BigDecimal amountTo;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Currency currencyTo;
}
