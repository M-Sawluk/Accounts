package com.michals.interview.service.factory;

import com.michals.interview.domain.Account;
import com.michals.interview.domain.AccountHolder;
import com.michals.interview.model.AccountCreateRequest;
import com.michals.interview.domain.Currency;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.List;

@UtilityClass
public class AccountsFactory {

    public static Account createAccounts(AccountCreateRequest accountCreateRequest, AccountHolder accountHolder) {
        Account mainAccount = new Account();
        mainAccount.setBalance(accountCreateRequest.startingBalance());
        mainAccount.setCurrency(Currency.PLN);
        mainAccount.setAccountHolder(accountHolder);
        mainAccount.setSubAccounts(buildSubAccounts(accountHolder, mainAccount));

        return mainAccount;
    }

    private static List<Account> buildSubAccounts(AccountHolder accountHolder, Account mainAccount) {
        Account subAccount = new Account();
        subAccount.setBalance(BigDecimal.ZERO);
        subAccount.setCurrency(Currency.EUR);
        subAccount.setAccountHolder(accountHolder);
        subAccount.setParentAccount(mainAccount);

        return List.of(subAccount);
    }
}
