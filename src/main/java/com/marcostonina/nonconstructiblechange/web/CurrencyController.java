package com.marcostonina.nonconstructiblechange.web;

import com.marcostonina.nonconstructiblechange.domain.Currency;
import com.marcostonina.nonconstructiblechange.domain.CurrencyService;
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


}
