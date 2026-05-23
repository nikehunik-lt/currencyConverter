package com.currency.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private BigDecimal buyRate;
    private BigDecimal sellRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", nullable = false)
    @JsonIgnore
    private Currency currency;

    public ExchangeRate() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public BigDecimal getBuyRate() { return buyRate; }
    public void setBuyRate(BigDecimal buyRate) { this.buyRate = buyRate; }
    public BigDecimal getSellRate() { return sellRate; }
    public void setSellRate(BigDecimal sellRate) { this.sellRate = sellRate; }
    public Currency getCurrency() { return currency; }
    public void setCurrency(Currency currency) { this.currency = currency; }
}