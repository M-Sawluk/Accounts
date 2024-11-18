package com.michals.interview.service.exchange;

import com.michals.interview.domain.Account;
import com.michals.interview.domain.Currency;
import com.michals.interview.domain.Exchange;
import com.michals.interview.exception.AccountNotFound;
import com.michals.interview.external.ExchangeResponse;
import com.michals.interview.model.ExchangeRequest;
import com.michals.interview.service.factory.ExchangeFactory;
import com.michals.interview.service.repository.AccountRepository;
import com.michals.interview.service.repository.ExchangeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ToForeignCurrencyExchangeStrategy implements ExchangeStrategy {

    private final AccountRepository accountRepository;
    private final ExchangeRepository exchangeRepository;

    @Override
    public boolean appliesFor(ExchangeRequest exchangeRequest) {
        return exchangeRequest.currencyFrom().equals(Currency.PLN);
    }

    @Override
    @Transactional
    public Exchange createExchange(ExchangeRequest exchangeRequest, String accountNumber, ExchangeResponse currentExchangeRates) {
        Account account = accountRepository.loadForUpdateByAccountNumber(UUID.fromString(accountNumber))
                .orElseThrow(() -> new AccountNotFound(accountNumber));
        BigDecimal buyRate = currentExchangeRates.getBuyRate();
        BigDecimal usdAmountBought = exchangeRequest.amount().divide(buyRate, 2, RoundingMode.FLOOR);
        account.subtractBalance(exchangeRequest.amount());
        account.findCurrencyAccount(Currency.USD).addBalance(usdAmountBought);
        Exchange exchange = ExchangeFactory.createExchange(exchangeRequest, buyRate, accountNumber, usdAmountBought);

        return exchangeRepository.save(exchange);
    }
}
