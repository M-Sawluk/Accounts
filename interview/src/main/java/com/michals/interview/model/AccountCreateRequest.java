package com.michals.interview.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AccountCreateRequest(@Valid AccountHolderDTO accountHolder,
                                   @DecimalMin(value = "0.00", message = "Has to be greater than 0.00")
                                   @Digits(fraction = 2, integer = Integer.MAX_VALUE)
                                   @NotNull (message = "Starting balance cannot be null")
                                   @Schema (type = "number", example = "20")
                                   BigDecimal startingBalance) {
}
