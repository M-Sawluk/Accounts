package com.michals.interview.model;

import com.michals.interview.domain.Currency;

import java.math.BigDecimal;
import java.util.List;

public record AccountDTO(String accountNumber,
                         Currency currency,
                         BigDecimal balance,
                         AccountHolderDTO accountHolder,
                         List<SubAccountDTO> subAccounts) {
}
