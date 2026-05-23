package com.currency.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "currencies")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    private String name;

    @OneToMany(mappedBy = "currency", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ExchangeRate> exchangeRates = new ArrayList<>();

    public Currency() {}

    public Currency(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<ExchangeRate> getExchangeRates() { return exchangeRates; }
    public void setExchangeRates(List<ExchangeRate> exchangeRates) { this.exchangeRates = exchangeRates; }
}