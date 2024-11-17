package com.michals.interview.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;

import java.math.BigDecimal;

public record AccountCreateRequest(AccountHolderDTO accountHolder,
                                   @DecimalMin(value = "0.01", message = "Minimum starting balance is 0.01")
                                   @Digits(fraction = 2, integer = Integer.MAX_VALUE, message = "Must be positive number with 2 fraction places")
                                   BigDecimal startingBalance) {
}
