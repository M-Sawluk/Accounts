package com.michals.interview.service.exchange;

import com.michals.interview.domain.Exchange;
import com.michals.interview.external.ExchangeResponse;
import com.michals.interview.model.ExchangeRequest;

public interface ExchangeStrategy {

    boolean appliesFor(ExchangeRequest exchangeRequest);

    Exchange createExchange(ExchangeRequest exchangeRequest, String accountNumber, ExchangeResponse currentExchangeRates);
}
