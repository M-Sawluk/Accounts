package com.michals.interview.service.mapper;

import com.michals.interview.domain.Exchange;
import com.michals.interview.model.ExchangeDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExchangeMapper {

    public static ExchangeDTO map(Exchange exchange) {
        return new ExchangeDTO(exchange.getAmountFrom(), exchange.getCurrencyFrom(),
                exchange.getAmountTo(), exchange.getCurrencyTo()
        );
    }
}
