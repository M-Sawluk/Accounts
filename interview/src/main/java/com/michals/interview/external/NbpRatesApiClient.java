package com.michals.interview.external;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class NbpRatesApiClient {

    @Value("${nbp.exchange-rates.url}")
    private String exchangeRateUrl;

    private final RestTemplate restTemplate;

    public ExchangeResponse getCurrentExchangeRates(String currency) {
        return restTemplate.getForEntity(exchangeRateUrl + currency, ExchangeResponse.class).getBody();
    }
}
