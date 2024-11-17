package com.michals.interview.service;

import com.michals.interview.domain.Account;
import com.michals.interview.domain.AccountHolder;
import com.michals.interview.model.AccountDTO;
import com.michals.interview.model.AccountCreateRequest;
import com.michals.interview.service.mapper.AccountMapper;
import com.michals.interview.service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountHolderService accountHolderService;

    @Transactional
    public AccountDTO createAccount(AccountCreateRequest accountCreateRequest) {
        AccountHolder accountHolder = accountHolderService.createAccountHolder(accountCreateRequest.accountHolder());
        Account mainAccount = AccountsFactory.createAccounts(accountCreateRequest, accountHolder);
        Account saved = accountRepository.save(mainAccount);

        return AccountMapper.map(saved);
    }

    public AccountDTO findById(String id) {
        return accountRepository.findById(UUID.fromString(id))
                .map(AccountMapper::map)
                .orElseThrow(IllegalStateException::new);
    }
}
