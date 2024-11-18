package com.michals.interview.controller;

import com.michals.interview.domain.Currency;
import com.michals.interview.model.AccountDTO;
import com.michals.interview.model.ExchangeDTO;
import com.michals.interview.model.ExchangeRequest;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.michals.interview.fixtures.AccountTestData.ACCOUNT_CREATE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class ExchangesControllerTestIT {
    private static final String HOST = "http://localhost:";
    private static final String EXCHANGES_URL = "/v1.0/accounts/{accountNumber}/exchanges";
    private static final String ACCOUNTS_URL = "/v1.0/accounts";
    public static final BigDecimal TEN = BigDecimal.TEN.setScale(2, RoundingMode.FLOOR);

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String accountNumber = null;

    @BeforeEach
    void setUp() {
        ResponseEntity<AccountDTO> accResponse = restTemplate.postForEntity(HOST + port + ACCOUNTS_URL, ACCOUNT_CREATE_REQUEST, AccountDTO.class);
        accountNumber = accResponse.getBody().accountNumber();
    }

    @Test
    void shouldExchangePlnToEuro() {
        //given
        ExchangeRequest exchangeRequest = new ExchangeRequest(TEN, Currency.PLN, Currency.EUR);

        //when
        String url = HOST + port + EXCHANGES_URL.replace("{accountNumber}", accountNumber);
        ResponseEntity<ExchangeDTO> response = restTemplate.postForEntity(url, exchangeRequest, ExchangeDTO.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        ExchangeDTO exchangeDTO = response.getBody();
        assertThat(exchangeDTO.amountFrom()).isEqualTo(TEN);
        assertThat(exchangeDTO.currencyFrom()).isEqualTo(Currency.PLN);
        assertThat(exchangeDTO.currencyTo()).isEqualTo(Currency.EUR);
        //10 : 4,3649 = 2.2910
        assertThat(exchangeDTO.amountTo()).isEqualTo(new BigDecimal("2.29"));
    }

    @Test
    void shouldExchangeEuroToPln() {
        //given
        ExchangeRequest exchangeRequestPlnToEuro = new ExchangeRequest(TEN, Currency.PLN, Currency.EUR);
        String url = HOST + port + EXCHANGES_URL.replace("{accountNumber}", accountNumber);
        restTemplate.postForEntity(url, exchangeRequestPlnToEuro, ExchangeDTO.class);
        ExchangeRequest exchangeRequestEuroToPln = new ExchangeRequest(BigDecimal.valueOf(2), Currency.EUR, Currency.PLN);

        //when
        ResponseEntity<ExchangeDTO> response = restTemplate.postForEntity(url, exchangeRequestEuroToPln, ExchangeDTO.class);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        ExchangeDTO exchangeDTO = response.getBody();
        assertThat(exchangeDTO.amountFrom()).isEqualTo(BigDecimal.valueOf(2));
        assertThat(exchangeDTO.currencyFrom()).isEqualTo(Currency.EUR);
        assertThat(exchangeDTO.currencyTo()).isEqualTo(Currency.PLN);
        //2 * 4,2785 = 8,557
        assertThat(exchangeDTO.amountTo()).isEqualTo(new BigDecimal("8.55"));
    }

    @Test
    void shouldThrowInsufficientFunds() {
        //given
        ExchangeRequest exchangeRequest = new ExchangeRequest(BigDecimal.valueOf(20), Currency.PLN, Currency.EUR);

        //when
        String url = HOST + port + EXCHANGES_URL.replace("{accountNumber}", accountNumber);
        ResponseEntity<ExchangeDTO> response = restTemplate.postForEntity(url, exchangeRequest, ExchangeDTO.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getHeaders().get("message")).isEqualTo(List.of("Insufficient funds"));

    }
}