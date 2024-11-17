package com.michals.interview.service.mapper;

import com.michals.interview.domain.Account;
import com.michals.interview.domain.AccountHolder;
import com.michals.interview.model.AccountDTO;
import com.michals.interview.model.AccountHolderDTO;
import com.michals.interview.model.SubAccountDTO;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class AccountMapper {

    public static AccountDTO map(Account account) {
        List<SubAccountDTO> subAccounts = createSubAccounts(account.getSubAccounts());
        AccountHolder accountHolder = account.getAccountHolder();
        AccountHolderDTO accountHolderDTO = new AccountHolderDTO(accountHolder.getName(), accountHolder.getSurname());
        return new AccountDTO(account.getId().toString(), account.getCurrency(), account.getAmount(), accountHolderDTO, subAccounts);
    }

    private static List<SubAccountDTO> createSubAccounts(List<Account> accounts) {
        return accounts.stream()
                .map(subAccount -> new SubAccountDTO(subAccount.getCurrency(), subAccount.getAmount()))
                .toList();
    }
}
