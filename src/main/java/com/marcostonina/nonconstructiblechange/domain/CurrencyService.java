package com.marcostonina.nonconstructiblechange.domain;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    /**
     * Given a list of coins, returns the minimum amount of change that it's impossible to create
     * @param coins: an array of coins
     * @return minimum amount of change that it's impossible to create. If coin list is 0, it returns 1
     */
    public int getNonConstructibleChange(List<Integer> coins) {
        if (coins.isEmpty()) {
            return 1;
        }
        Collections.sort(coins);
        int minChange = 0;

        for (int coin : coins) {
            if (coin > minChange + 1) {
                break;
            }
            minChange += coin;
        }
        return minChange + 1;
    }

    public Currency createCurrency(Currency currency) {
        return currencyRepository.save(currency);
    }

    public Currency getCurrency(String code) {
        return currencyRepository.findByCode(code).orElseThrow(()
                -> new RuntimeException("Could not find currency with code " + code));
    }

    public void removeCurrency(String code) {
        currencyRepository.deleteByCode(code);
    }

    public Currency updateCurrency(Currency currency){
        return currencyRepository.findByCode(currency.code())
                .map(existingCurrency -> {
                    var currencyToUpdate = new Currency(
                            existingCurrency.code(),
                            currency.name(),
                            currency.coins()
                    );
                    return currencyRepository.save(currencyToUpdate);
                }).orElseGet(() -> createCurrency(currency));
    }

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }
}
