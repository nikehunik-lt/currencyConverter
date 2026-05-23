package com.currency.service;

import com.currency.entity.Currency;
import com.currency.repository.CurrencyRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CurrencyService {

    private final CurrencyRepository repository;
    private final EntityManager entityManager;

    public CurrencyService(CurrencyRepository repository, EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    public Page<Currency> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Currency> searchByCodeOrName(String search, Pageable pageable) {
        return repository.findByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(search, search, pageable);
    }

    public Currency getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Currency not found"));
    }

    @Transactional
    public Currency create(Currency currency) {
        return entityManager.merge(currency);
    }

    @Transactional
    public Currency update(Long id, Currency updated) {
        Currency existing = getById(id);
        existing.setCode(updated.getCode());
        existing.setName(updated.getName());
        return entityManager.merge(existing);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}