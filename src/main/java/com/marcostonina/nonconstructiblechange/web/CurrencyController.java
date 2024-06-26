package com.marcostonina.nonconstructiblechange.web;

import com.marcostonina.nonconstructiblechange.domain.Currency;
import com.marcostonina.nonconstructiblechange.domain.CurrencyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("minNonConstructibleChange")
    @ResponseStatus(HttpStatus.OK)
    public Integer minNonConstructibleChange(@RequestParam ArrayList<Integer> coinList) {
        return currencyService.getNonConstructibleChange(coinList);
    }

    @GetMapping({"{code}"})
    @ResponseStatus(HttpStatus.OK)
    public Currency getCurrencyByCode(@PathVariable String code) {
        return currencyService.getCurrency(code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Currency createCurrency(@Valid @RequestBody Currency currency) {
        return currencyService.createCurrency(currency);
    }

    @DeleteMapping({"{code}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCurrency(@PathVariable String code) {
        currencyService.removeCurrency(code);
    }

    @PutMapping
    public Currency updateCurrency(@Valid @RequestBody Currency currency) {
        //it will return 200 on success. If it were required to return 201/204 based on creation/update,
        //then it would be necessary to do an exists here before calling to update
        return currencyService.updateCurrency(currency);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Currency> getAllCurrencies() {
        return currencyService.getAllCurrencies();
    }


}
