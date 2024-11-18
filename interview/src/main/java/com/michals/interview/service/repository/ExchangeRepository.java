package com.michals.interview.service.repository;

import com.michals.interview.domain.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
}
