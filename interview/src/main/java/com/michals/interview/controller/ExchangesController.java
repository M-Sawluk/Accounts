package com.michals.interview.controller;

import com.michals.interview.model.ExchangeDTO;
import com.michals.interview.model.ExchangeRequest;
import com.michals.interview.service.ExchangeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0/accounts")
public class ExchangesController {

    private final ExchangeService exchangeService;

    @PostMapping("/{accountNumber}/exchanges")
    ExchangeDTO createExchange(@PathVariable String accountNumber, @Valid @RequestBody ExchangeRequest exchangeRequest) {
        return exchangeService.createExchange(accountNumber, exchangeRequest);
    }
}
