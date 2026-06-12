package com.currency.service;

import com.currency.entity.Currency;
import com.currency.exception.ResourceNotFoundException;
import com.currency.repository.CurrencyRepository;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CurrencyService {

    private static final Logger log = LoggerFactory.getLogger(CurrencyService.class);

    private final CurrencyRepository repository;
    private final EntityManager entityManager;

    public CurrencyService(CurrencyRepository repository, EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    public Page<Currency> getAll(Pageable pageable) {
        log.info("Fetching all currencies");
        return repository.findAll(pageable);
    }

    public Page<Currency> searchByCodeOrName(String search, Pageable pageable) {
        log.info("Searching currencies by query: {}", search);
        return repository.findByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(search, search, pageable);
    }

    public Currency getById(Long id) {
        log.info("Fetching currency with id: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Currency not found with id: {}", id);
                    return new ResourceNotFoundException("Currency not found");
                });
    }

    @Transactional
    public Currency create(Currency currency) {
        log.info("Creating new currency with code: {}", currency.getCode());
        return entityManager.merge(currency);
    }

    @Transactional
    public Currency update(Long id, Currency updated) {
        log.info("Updating currency with id: {}", id);
        Currency existing = getById(id);
        existing.setCode(updated.getCode());
        existing.setName(updated.getName());
        return entityManager.merge(existing);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting currency with id: {}", id);
        if (!repository.existsById(id)) {
            log.error("Failed to delete. Currency not found with id: {}", id);
            throw new ResourceNotFoundException("Currency not found");
        }
        repository.deleteById(id);
    }
}