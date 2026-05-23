package com.currency.service;

import com.currency.entity.Currency;
import com.currency.entity.ExchangeRate;
import com.currency.repository.ExchangeRateRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExchangeRateService {

    private final ExchangeRateRepository repository;
    private final CurrencyService currencyService;
    private final EntityManager entityManager;

    public ExchangeRateService(ExchangeRateRepository repository, CurrencyService currencyService, EntityManager entityManager) {
        this.repository = repository;
        this.currencyService = currencyService;
        this.entityManager = entityManager;
    }

    public Page<ExchangeRate> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<ExchangeRate> getByCurrencyId(Long currencyId, Pageable pageable) {
        return repository.findByCurrencyId(currencyId, pageable);
    }

    public ExchangeRate getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rate not found"));
    }

    @Transactional
    public ExchangeRate createForCurrency(Long currencyId, ExchangeRate rate) {
        Currency currency = currencyService.getById(currencyId);
        rate.setCurrency(currency);
        return entityManager.merge(rate);
    }

    @Transactional
    public ExchangeRate update(Long id, ExchangeRate updated) {
        ExchangeRate existing = getById(id);
        existing.setDate(updated.getDate());
        existing.setBuyRate(updated.getBuyRate());
        existing.setSellRate(updated.getSellRate());
        return entityManager.merge(existing);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}