package com.michals.interview.domain;

import com.michals.interview.exception.CurrencyAccountNotFound;
import com.michals.interview.exception.InsufficientFundsException;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Currency currency;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "accountHolderId")
    private AccountHolder accountHolder;

    @Valid
    @OneToMany(mappedBy = "parentAccount", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Account> subAccounts;

    @ManyToOne
    private Account parentAccount;

    public void subtractBalance(BigDecimal amount) {
        if (amount.compareTo(balance) > 0) {
            throw new InsufficientFundsException();
        }
        balance = balance.subtract(amount)
                .setScale(2, RoundingMode.FLOOR);
    }

    public void addBalance(BigDecimal amount) {
        balance = balance.add(amount)
                .setScale(2, RoundingMode.FLOOR);
    }

    public Account findCurrencyAccount(Currency currency) {
        return subAccounts.stream()
                .filter(acc -> acc.getCurrency().equals(currency))
                .findFirst()
                .orElseThrow(() -> new CurrencyAccountNotFound(currency.name()));
    }

}
