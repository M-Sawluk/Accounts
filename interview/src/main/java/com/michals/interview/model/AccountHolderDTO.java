package com.michals.interview.model;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AccountHolderDTO(@Pattern(regexp = "[A-Za-z]{3,}", message = "At least 3 characters required")
                               @Size(min = 3, max = 20)
                               String name,
                               @Pattern(regexp = "[A-Za-z ]{3,}", message = "At least 3 characters required")
                               @Size(min = 3, max = 50)
                               String surname) {
}
