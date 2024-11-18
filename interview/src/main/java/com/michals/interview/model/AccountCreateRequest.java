package com.michals.interview.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AccountCreateRequest(@Valid AccountHolderDTO accountHolder,
                                   @DecimalMin(value = "0.00")
                                   @Digits(fraction = 2, integer = Integer.MAX_VALUE)
                                   @NotNull (message = "Starting balance cannot be null")
                                   BigDecimal startingBalance) {
}
