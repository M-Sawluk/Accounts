package com.michals.interview.service;

import com.michals.interview.domain.Currency;
import com.michals.interview.domain.Exchange;
import com.michals.interview.external.NbpRatesApiClient;
import com.michals.interview.model.ExchangeDTO;
import com.michals.interview.model.ExchangeRequest;
import com.michals.interview.external.ExchangeResponse;
import com.michals.interview.service.exchange.ExchangeStrategy;
import com.michals.interview.service.mapper.ExchangeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final NbpRatesApiClient nbpRatesApiClient;
    private final List<ExchangeStrategy> exchangeStrategies;

    public ExchangeDTO createExchange(String accountNumber, ExchangeRequest exchangeRequest) {
        ExchangeResponse currentExchangeRates = nbpRatesApiClient.getCurrentExchangeRates(Currency.USD.name());
        ExchangeStrategy strategy = findExchangeStrategy(exchangeRequest);
        Exchange exchange = strategy.createExchange(exchangeRequest, accountNumber, currentExchangeRates);
        return ExchangeMapper.map(exchange);
    }

    private ExchangeStrategy findExchangeStrategy(ExchangeRequest exchangeRequest) {
        return exchangeStrategies.stream()
                .filter(exchangeStrategy -> exchangeStrategy.appliesFor(exchangeRequest))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot find exchange strategy for:" + exchangeRequest));
    }
}
