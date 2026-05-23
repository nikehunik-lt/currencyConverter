package com.currency.controller;

import com.currency.dto.CurrencyDto;
import com.currency.dto.CurrencyResponseDto;
import com.currency.entity.Currency;
import com.currency.service.CurrencyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    private final CurrencyService service;

    public CurrencyController(CurrencyService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<CurrencyResponseDto>> getAllCurrencies(
            @RequestParam(required = false) String search,
            Pageable pageable) {
        Page<Currency> page;
        if (search != null && !search.isBlank()) {
            page = service.searchByCodeOrName(search, pageable);
        } else {
            page = service.getAll(pageable);
        }
        return ResponseEntity.ok(page.map(this::toResponseDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyResponseDto> getCurrencyById(@PathVariable Long id) {
        Currency currency = service.getById(id);
        return ResponseEntity.ok(toResponseDto(currency));
    }

    @PostMapping
    public ResponseEntity<CurrencyResponseDto> createCurrency(@RequestBody CurrencyDto dto) {
        Currency currency = new Currency();
        currency.setCode(dto.getCode());
        currency.setName(dto.getName());
        Currency created = service.create(currency);
        return ResponseEntity.ok(toResponseDto(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyResponseDto> updateCurrency(@PathVariable Long id,
                                                              @RequestBody CurrencyDto dto) {
        Currency currency = new Currency();
        currency.setCode(dto.getCode());
        currency.setName(dto.getName());
        Currency updated = service.update(id, currency);
        return ResponseEntity.ok(toResponseDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private CurrencyResponseDto toResponseDto(Currency currency) {
        CurrencyResponseDto dto = new CurrencyResponseDto();
        dto.setId(currency.getId());
        dto.setCode(currency.getCode());
        dto.setName(currency.getName());
        return dto;
    }
}