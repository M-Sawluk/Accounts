package com.michals.interview.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class AccountHolder {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(length = 20)
    private String name;

    @NotNull
    @Column(length = 50)
    private String surname;

    @OneToMany(mappedBy = "accountHolder")
    private List<Account> accounts;
}
