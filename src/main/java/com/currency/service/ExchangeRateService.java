package com.currency.service;

import com.currency.entity.Currency;
import com.currency.entity.ExchangeRate;
import com.currency.exception.ResourceNotFoundException;
import com.currency.repository.ExchangeRateRepository;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExchangeRateService {

    private static final Logger log = LoggerFactory.getLogger(ExchangeRateService.class);

    private final ExchangeRateRepository repository;
    private final CurrencyService currencyService;
    private final EntityManager entityManager;

    public ExchangeRateService(ExchangeRateRepository repository, CurrencyService currencyService, EntityManager entityManager) {
        this.repository = repository;
        this.currencyService = currencyService;
        this.entityManager = entityManager;
    }

    public Page<ExchangeRate> getAll(Pageable pageable) {
        log.info("Fetching all exchange rates");
        return repository.findAll(pageable);
    }

    public Page<ExchangeRate> getByCurrencyId(Long currencyId, Pageable pageable) {
        log.info("Fetching exchange rates for currency id: {}", currencyId);
        return repository.findByCurrencyId(currencyId, pageable);
    }

    public ExchangeRate getById(Long id) {
        log.info("Fetching exchange rate with id: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Exchange rate not found with id: {}", id);
                    return new ResourceNotFoundException("Rate not found");
                });
    }

    @Transactional
    public ExchangeRate createForCurrency(Long currencyId, ExchangeRate rate) {
        log.info("Creating exchange rate for currency id: {}", currencyId);
        Currency currency = currencyService.getById(currencyId);
        rate.setCurrency(currency);
        return entityManager.merge(rate);
    }

    @Transactional
    public ExchangeRate update(Long id, ExchangeRate updated) {
        log.info("Updating exchange rate with id: {}", id);
        ExchangeRate existing = getById(id);
        existing.setDate(updated.getDate());
        existing.setBuyRate(updated.getBuyRate());
        existing.setSellRate(updated.getSellRate());
        return entityManager.merge(existing);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting exchange rate with id: {}", id);
        if (!repository.existsById(id)) {
            log.error("Failed to delete. Exchange rate not found with id: {}", id);
            throw new ResourceNotFoundException("Rate not found");
        }
        repository.deleteById(id);
    }
}