package com.michals.interview.service;

import com.michals.interview.model.AccountDTO;
import com.michals.interview.model.ExchangeRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ExchangeService {

    public AccountDTO createExchange(String accountNumber, ExchangeRequest exchangeRequest) {

        return null;
    }
}
