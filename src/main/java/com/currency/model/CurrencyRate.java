package com.currency.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CurrencyRate(
        String currencyCode,
        BigDecimal rate,
        LocalDateTime updatedAt
) {}