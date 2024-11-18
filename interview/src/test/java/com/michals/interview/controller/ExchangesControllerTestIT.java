package com.michals.interview.controller;

import com.michals.interview.domain.Account;
import com.michals.interview.domain.Currency;
import com.michals.interview.model.AccountDTO;
import com.michals.interview.model.ExchangeDTO;
import com.michals.interview.model.ExchangeRequest;
import com.michals.interview.service.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
 import java.util.UUID;

import static com.michals.interview.fixtures.AccountTestData.ACCOUNT_CREATE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@Transactional
class ExchangesControllerTestIT {
    private static final String HOST = "http://localhost:";
    private static final String EXCHANGES_URL = "/v1.0/accounts/{accountNumber}/exchanges";
    private static final String ACCOUNTS_URL = "/v1.0/accounts";
    public static final BigDecimal TEN = BigDecimal.TEN.setScale(2, RoundingMode.FLOOR);

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository accountRepository;

    private String accountNumber = null;

    @BeforeEach
    void setUp() {
        ResponseEntity<AccountDTO> accResponse = restTemplate.postForEntity(HOST + port + ACCOUNTS_URL, ACCOUNT_CREATE_REQUEST, AccountDTO.class);
        accountNumber = accResponse.getBody().accountNumber();
    }

    @Test
    void shouldExchangePlnToUsd() {
        //given
        ExchangeRequest exchangeRequest = new ExchangeRequest(TEN, Currency.PLN, Currency.USD);

        //when
        String url = HOST + port + EXCHANGES_URL.replace("{accountNumber}", accountNumber);
        ResponseEntity<ExchangeDTO> response = restTemplate.postForEntity(url, exchangeRequest, ExchangeDTO.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        ExchangeDTO exchangeDTO = response.getBody();
        assertThat(exchangeDTO.amountFrom()).isEqualTo(TEN);
        assertThat(exchangeDTO.currencyFrom()).isEqualTo(Currency.PLN);
        assertThat(exchangeDTO.currencyTo()).isEqualTo(Currency.USD);
        //10 : 4,3649 = 2.2910
        assertThat(exchangeDTO.amountTo()).isEqualTo(new BigDecimal("2.29"));
    }

    @Test
    void shouldExchangeUsdToPln() {
        //given
        ExchangeRequest exchangeRequestPlnToUsd = new ExchangeRequest(TEN, Currency.PLN, Currency.USD);
        String url = HOST + port + EXCHANGES_URL.replace("{accountNumber}", accountNumber);
        restTemplate.postForEntity(url, exchangeRequestPlnToUsd, ExchangeDTO.class);
        //PLN = 0, EURO 2.29
        ExchangeRequest exchangeRequestUsdToPln = new ExchangeRequest(BigDecimal.valueOf(2), Currency.USD, Currency.PLN);

        //when
        ResponseEntity<ExchangeDTO> response = restTemplate.postForEntity(url, exchangeRequestUsdToPln, ExchangeDTO.class);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        ExchangeDTO exchangeDTO = response.getBody();
        assertThat(exchangeDTO.amountFrom()).isEqualTo(BigDecimal.valueOf(2));
        assertThat(exchangeDTO.currencyFrom()).isEqualTo(Currency.USD);
        assertThat(exchangeDTO.currencyTo()).isEqualTo(Currency.PLN);
        //2 * 4,2785 = 8,557
        assertThat(exchangeDTO.amountTo()).isEqualTo(new BigDecimal("8.55"));
        Account account = accountRepository.loadForUpdateByAccountNumber(UUID.fromString(accountNumber)).get();
        assertThat(account.getBalance()).isEqualTo(new BigDecimal("8.55"));
        //leftover 2.29 - 2
        assertThat(account.getSubAccounts().get(0).getBalance()).isEqualTo(new BigDecimal("0.29"));
    }

    @Test
    void shouldThrowInsufficientFunds() {
        //given
        ExchangeRequest exchangeRequest = new ExchangeRequest(BigDecimal.valueOf(20), Currency.PLN, Currency.USD);

        //when
        String url = HOST + port + EXCHANGES_URL.replace("{accountNumber}", accountNumber);
        ResponseEntity<ExchangeDTO> response = restTemplate.postForEntity(url, exchangeRequest, ExchangeDTO.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getHeaders().get("message")).isEqualTo(List.of("Insufficient funds"));

    }
}