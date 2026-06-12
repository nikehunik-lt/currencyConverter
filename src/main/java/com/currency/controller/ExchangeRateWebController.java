package com.currency.controller;

import com.currency.dto.ExchangeRateDto;
import com.currency.entity.ExchangeRate;
import com.currency.service.CurrencyService;
import com.currency.service.ExchangeRateService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rates")
public class ExchangeRateWebController {

    private final ExchangeRateService rateService;
    private final CurrencyService currencyService;

    public ExchangeRateWebController(ExchangeRateService rateService, CurrencyService currencyService) {
        this.rateService = rateService;
        this.currencyService = currencyService;
    }

    @GetMapping
    public String listRates(
            @RequestParam(required = false) Long currencyId,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            Model model) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(0, 10000, sort);

        if (currencyId != null) {
            model.addAttribute("rates", rateService.getByCurrencyId(currencyId, pageable).getContent());
        } else {
            model.addAttribute("rates", rateService.getAll(pageable).getContent());
        }

        model.addAttribute("currencies", currencyService.getAll(Pageable.unpaged()).getContent());
        model.addAttribute("selectedCurrencyId", currencyId);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "rates/list";
    }

    @GetMapping("/{id}")
    public String viewRate(@PathVariable Long id, Model model) {
        model.addAttribute("rate", rateService.getById(id));
        return "rates/view";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("exchangeRateDto", new ExchangeRateDto());
        model.addAttribute("currencies", currencyService.getAll(Pageable.unpaged()).getContent());
        return "rates/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        ExchangeRate rate = rateService.getById(id);
        ExchangeRateDto dto = new ExchangeRateDto();
        dto.setId(rate.getId());
        dto.setDate(rate.getDate());
        dto.setBuyRate(rate.getBuyRate());
        dto.setSellRate(rate.getSellRate());
        dto.setCurrencyId(rate.getCurrency().getId());
        model.addAttribute("exchangeRateDto", dto);
        model.addAttribute("currencies", currencyService.getAll(Pageable.unpaged()).getContent());
        return "rates/form";
    }

    @PostMapping("/save")
    public String saveRate(@Valid @ModelAttribute("exchangeRateDto") ExchangeRateDto dto,
                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("currencies", currencyService.getAll(Pageable.unpaged()).getContent());
            return "rates/form";
        }
        ExchangeRate rate = new ExchangeRate();
        rate.setDate(dto.getDate());
        rate.setBuyRate(dto.getBuyRate());
        rate.setSellRate(dto.getSellRate());
        if (dto.getId() != null) {
            rateService.update(dto.getId(), rate);
        } else {
            rateService.createForCurrency(dto.getCurrencyId(), rate);
        }
        return "redirect:/rates";
    }

    @PostMapping("/delete/{id}")
    public String deleteRate(@PathVariable Long id) {
        rateService.delete(id);
        return "redirect:/rates";
    }
}