package com.michals.interview.model;

import java.math.BigDecimal;
import java.util.List;

public record ExchangeResponse(List<Rates> rates) {

    public record Rates(BigDecimal bid, BigDecimal ask) {}
}
