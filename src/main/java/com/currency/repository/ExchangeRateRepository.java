package com.currency.repository;

import com.currency.entity.ExchangeRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    Page<ExchangeRate> findByCurrencyId(Long currencyId, Pageable pageable);
}