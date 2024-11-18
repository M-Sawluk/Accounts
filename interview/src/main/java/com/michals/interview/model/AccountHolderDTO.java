package com.michals.interview.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AccountHolderDTO(@Pattern(regexp = "[A-Za-z]{3,}", message = "At least 3 characters required")
                               @NotBlank(message = "Name cannot be null")
                               @Schema(type = "string", example = "Michal")
                               String name,
                               @Pattern(regexp = "[A-Za-z ]{3,}", message = "At least 3 characters required")
                               @NotBlank(message = "Surname cannot be null")
                               @Schema(type = "string", example = "Sawluk")
                               String surname) {
}
