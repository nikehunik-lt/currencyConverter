package com.currency.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CurrencyDto {
    private Long id;

    @NotBlank(message = "Currency code cannot be empty")
    @Size(min = 3, max = 3, message = "Currency code must be exactly 3 characters (e.g., USD)")
    private String code;

    @NotBlank(message = "Currency name is required")
    private String name;

    public CurrencyDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}