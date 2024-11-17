package com.michals.interview.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Currency currency;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "accountHolderId")
    private AccountHolder accountHolder;

    @Valid
    @OneToMany(mappedBy = "parentAccount", cascade = CascadeType.PERSIST)
    private List<Account> subAccounts;

    @ManyToOne
    private Account parentAccount;

}
