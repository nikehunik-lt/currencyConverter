package com.currency.controller;

import com.currency.dto.CurrencyDto;
import com.currency.entity.Currency;
import com.currency.service.CurrencyService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/currencies")
public class CurrencyWebController {

    private final CurrencyService currencyService;

    public CurrencyWebController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public String listCurrencies(@RequestParam(required = false) String search, Model model) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("currencies", currencyService.searchByCodeOrName(search, Pageable.unpaged()).getContent());
            model.addAttribute("search", search);
        } else {
            model.addAttribute("currencies", currencyService.getAll(Pageable.unpaged()).getContent());
        }
        return "currencies/list";
    }

    @GetMapping("/{id}")
    public String viewCurrency(@PathVariable Long id, Model model) {
        model.addAttribute("currency", currencyService.getById(id));
        return "currencies/view";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("currencyDto", new CurrencyDto());
        return "currencies/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Currency currency = currencyService.getById(id);
        CurrencyDto dto = new CurrencyDto();
        dto.setId(currency.getId());
        dto.setCode(currency.getCode());
        dto.setName(currency.getName());
        model.addAttribute("currencyDto", dto);
        return "currencies/form";
    }

    @PostMapping("/save")
    public String saveCurrency(@Valid @ModelAttribute("currencyDto") CurrencyDto dto,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "currencies/form";
        }
        Currency currency = new Currency();
        currency.setCode(dto.getCode());
        currency.setName(dto.getName());
        if (dto.getId() != null) {
            currencyService.update(dto.getId(), currency);
        } else {
            currencyService.create(currency);
        }
        return "redirect:/currencies";
    }

    @PostMapping("/delete/{id}")
    public String deleteCurrency(@PathVariable Long id) {
        currencyService.delete(id);
        return "redirect:/currencies";
    }
}