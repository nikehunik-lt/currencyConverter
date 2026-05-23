package com.currency.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ExchangeRateDto {
    private Long id;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Buy rate is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Buy rate must be greater than zero")
    private BigDecimal buyRate;

    @NotNull(message = "Sell rate is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Sell rate must be greater than zero")
    private BigDecimal sellRate;

    @NotNull(message = "Currency selection is required")
    private Long currencyId;

    public ExchangeRateDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public BigDecimal getBuyRate() { return buyRate; }
    public void setBuyRate(BigDecimal buyRate) { this.buyRate = buyRate; }
    public BigDecimal getSellRate() { return sellRate; }
    public void setSellRate(BigDecimal sellRate) { this.sellRate = sellRate; }
    public Long getCurrencyId() { return currencyId; }
    public void setCurrencyId(Long currencyId) { this.currencyId = currencyId; }
}