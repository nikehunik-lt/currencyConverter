package com.currency.dto;

public class ExchangeRateResponseDto {
    private Long id;
    private java.time.LocalDate date;
    private java.math.BigDecimal buyRate;
    private java.math.BigDecimal sellRate;
    private Long currencyId;
    private String currencyCode;

    public ExchangeRateResponseDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public java.time.LocalDate getDate() { return date; }
    public void setDate(java.time.LocalDate date) { this.date = date; }
    public java.math.BigDecimal getBuyRate() { return buyRate; }
    public void setBuyRate(java.math.BigDecimal buyRate) { this.buyRate = buyRate; }
    public java.math.BigDecimal getSellRate() { return sellRate; }
    public void setSellRate(java.math.BigDecimal sellRate) { this.sellRate = sellRate; }
    public Long getCurrencyId() { return currencyId; }
    public void setCurrencyId(Long currencyId) { this.currencyId = currencyId; }
    public String getCurrencyCode() { return currencyCode; }
    public void setCurrencyCode(String currencyCode) { this.currencyCode = currencyCode; }
}