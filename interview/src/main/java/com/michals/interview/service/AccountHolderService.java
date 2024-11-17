package com.michals.interview.service;

import com.michals.interview.domain.AccountHolder;
import com.michals.interview.model.AccountHolderDTO;
import com.michals.interview.service.repository.AccountHolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountHolderService {

    private final AccountHolderRepository accountHolderRepository;

    public AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO) {
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setName(accountHolderDTO.name());
        accountHolder.setSurname(accountHolderDTO.surname());

        return accountHolderRepository.save(accountHolder);
    }

}
