package com.michals.interview.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ExchangeRatesConfig {

    @Bean
    RestTemplate exchangeRestTemplate() {

    }
}
