package com.michals.interview.service.repository;

import com.michals.interview.domain.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT acc FROM Account acc " +
            "JOIN FETCH acc.subAccounts " +
            "WHERE acc.id=:accountNumber")
    Optional<Account> loadForUpdateByAccountNumber(@Param("accountNumber") UUID accountNumber);
}
