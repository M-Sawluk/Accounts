package com.michals.interview.controller;


import com.michals.interview.service.AccountService;
import com.michals.interview.model.AccountDTO;
import com.michals.interview.model.AccountCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0/accounts")
public class AccountsController {

    private final AccountService accountService;

    @PostMapping
    ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody AccountCreateRequest accountCreateRequest) {
        return new ResponseEntity<>(accountService.createAccount(accountCreateRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{accountNumber}")
    ResponseEntity<AccountDTO> getAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.findById(accountNumber));
    }
}
