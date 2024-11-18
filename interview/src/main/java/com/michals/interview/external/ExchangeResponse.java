package com.michals.interview.external;

import java.math.BigDecimal;
import java.util.List;

public record ExchangeResponse(List<Rates> rates) {

    public static final int FIRST = 0;

    public record Rates(BigDecimal bid, BigDecimal ask) {}

    public BigDecimal getSaleRate() {
        return rates.get(FIRST).bid();
    }

    public BigDecimal getBuyRate() {
        return rates.get(FIRST).ask();
    }
}
