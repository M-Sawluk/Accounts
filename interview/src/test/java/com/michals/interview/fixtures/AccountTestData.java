package com.michals.interview.fixtures;

import com.michals.interview.model.AccountCreateRequest;
import com.michals.interview.model.AccountHolderDTO;

import java.math.BigDecimal;

import static java.math.RoundingMode.FLOOR;

public interface AccountTestData {

    String NAME = "Michal";
    String SURNAME = "Sawluk";
    BigDecimal STARTING_BALANCE = BigDecimal.TEN.setScale(2, FLOOR);
    AccountCreateRequest ACCOUNT_CREATE_REQUEST = new AccountCreateRequest(new AccountHolderDTO(NAME, SURNAME), STARTING_BALANCE);

}
