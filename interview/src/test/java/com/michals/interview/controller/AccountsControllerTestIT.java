package com.michals.interview.controller;

import com.michals.interview.domain.Currency;
import com.michals.interview.model.AccountCreateRequest;
import com.michals.interview.model.AccountDTO;
import com.michals.interview.model.AccountHolderDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static com.michals.interview.fixtures.AccountTestData.*;
import static java.math.RoundingMode.FLOOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountsControllerTestIT {
    private static final String HOST = "http://localhost:";
    private static final String ACCOUNTS_URL = "/v1.0/accounts";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateAccount() {
        //when
        ResponseEntity<AccountDTO> accountDTOResponseEntity = restTemplate.postForEntity(HOST + port + ACCOUNTS_URL, ACCOUNT_CREATE_REQUEST, AccountDTO.class);

        //then
        assertThat(accountDTOResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        AccountDTO body = accountDTOResponseEntity.getBody();
        assertThat(body).isNotNull();
        assertThat(body.accountNumber()).isNotNull();
        assertThat(body.balance()).isEqualTo(STARTING_BALANCE);
        assertThat(body.currency()).isEqualTo(Currency.PLN);
        assertThat(body.subAccounts()).extracting("currency", "balance")
                .containsExactly(tuple(Currency.EUR, BigDecimal.ZERO));
        assertThat(body.accountHolder())
                .extracting("name", "surname")
                .containsExactly(NAME, SURNAME);
    }

    @Test
    void shouldGetAccount() {
        //given
        ResponseEntity<AccountDTO> result = restTemplate.postForEntity(HOST + port + ACCOUNTS_URL, ACCOUNT_CREATE_REQUEST, AccountDTO.class);

        //when
        ResponseEntity<AccountDTO> accountDTOResponseEntity = restTemplate.getForEntity(HOST + port + ACCOUNTS_URL + "/" +result.getBody().accountNumber(), AccountDTO.class);

        //then
        assertThat(accountDTOResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        AccountDTO body = accountDTOResponseEntity.getBody();
        assertThat(body).isNotNull();
        assertThat(body.accountNumber()).isNotNull();
        assertThat(body.balance()).isEqualTo(STARTING_BALANCE);
        assertThat(body.currency()).isEqualTo(Currency.PLN);
        assertThat(body.subAccounts()).extracting("currency", "balance")
                .containsExactly(tuple(Currency.EUR, BigDecimal.ZERO.setScale(2, FLOOR)));
        assertThat(body.accountHolder())
                .extracting("name", "surname")
                .containsExactly(NAME, SURNAME);
    }

    @Test
    void shouldReturn404ForUnKnownAccountNumber() {
        //when
        ResponseEntity<AccountDTO> accountDTOResponseEntity = restTemplate.getForEntity(HOST + port + ACCOUNTS_URL + "/6e5f5edc-f9b9-48db-b633-03969912833f", AccountDTO.class);

        //then
        assertThat(accountDTOResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "Michal,Sawluk,,Starting balance cannot be null",
            "Michal,Sawluk,-10,Has to be greater than 0.00",
            "Michal,,10,Surname cannot be null",
            ",Sawluk,10,Name cannot be null",
    })
    void shouldReturnValidationErrors(String name, String surname, BigDecimal balance, String errorMessage) {
        //given
        AccountCreateRequest accountCreateRequest = new AccountCreateRequest(new AccountHolderDTO(name, surname), balance);

        //when
        ResponseEntity<String> response = restTemplate.postForEntity(HOST + port + ACCOUNTS_URL, accountCreateRequest, String.class);

        //then
        assertThat(response.getBody()).isEqualTo(errorMessage);
    }
}