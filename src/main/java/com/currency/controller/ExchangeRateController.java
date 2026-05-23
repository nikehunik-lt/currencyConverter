package com.currency.controller;

import com.currency.dto.ExchangeRateDto;
import com.currency.dto.ExchangeRateResponseDto;
import com.currency.entity.ExchangeRate;
import com.currency.service.ExchangeRateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rates")
public class ExchangeRateController {

    private final ExchangeRateService service;

    public ExchangeRateController(ExchangeRateService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<ExchangeRateResponseDto>> getAllRates(
            @RequestParam(required = false) Long currencyId,
            Pageable pageable) {
        Page<ExchangeRate> page;
        if (currencyId != null) {
            page = service.getByCurrencyId(currencyId, pageable);
        } else {
            page = service.getAll(pageable);
        }
        return ResponseEntity.ok(page.map(this::toResponseDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExchangeRateResponseDto> getRateById(@PathVariable Long id) {
        ExchangeRate rate = service.getById(id);
        return ResponseEntity.ok(toResponseDto(rate));
    }

    @PostMapping("/currency/{currencyId}")
    public ResponseEntity<ExchangeRateResponseDto> createRateForCurrency(
            @PathVariable Long currencyId,
            @RequestBody ExchangeRateDto dto) {
        ExchangeRate rate = new ExchangeRate();
        rate.setDate(dto.getDate());
        rate.setBuyRate(dto.getBuyRate());
        rate.setSellRate(dto.getSellRate());
        ExchangeRate created = service.createForCurrency(currencyId, rate);
        return ResponseEntity.ok(toResponseDto(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExchangeRateResponseDto> updateRate(@PathVariable Long id,
                                                              @RequestBody ExchangeRateDto dto) {
        ExchangeRate rate = new ExchangeRate();
        rate.setDate(dto.getDate());
        rate.setBuyRate(dto.getBuyRate());
        rate.setSellRate(dto.getSellRate());
        ExchangeRate updated = service.update(id, rate);
        return ResponseEntity.ok(toResponseDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRate(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private ExchangeRateResponseDto toResponseDto(ExchangeRate rate) {
        ExchangeRateResponseDto dto = new ExchangeRateResponseDto();
        dto.setId(rate.getId());
        dto.setDate(rate.getDate());
        dto.setBuyRate(rate.getBuyRate());
        dto.setSellRate(rate.getSellRate());
        dto.setCurrencyId(rate.getCurrency().getId());
        dto.setCurrencyCode(rate.getCurrency().getCode());
        return dto;
    }
}