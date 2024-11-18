package com.michals.interview.model;

import com.michals.interview.domain.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ExchangeRequest(@DecimalMin(value = "0.01", message = "Minimum starting balance is 0.01")
                           @Digits(fraction = 2, integer = Integer.MAX_VALUE, message = "Must be positive number with 2 fraction places")
                           @Schema(type = "number", example = "5")
                           BigDecimal amount,
                           @NotNull
                           @Schema(type = "string", example = "PLN")
                           Currency currencyFrom,
                           @NotNull
                           @Schema(type = "string", example = "EUR")
                           Currency currencyTo) {
}
