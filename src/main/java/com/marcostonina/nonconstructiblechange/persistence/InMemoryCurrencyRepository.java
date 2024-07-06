package com.marcostonina.nonconstructiblechange.persistence;

import com.marcostonina.nonconstructiblechange.domain.Currency;
import com.marcostonina.nonconstructiblechange.domain.CurrencyRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryCurrencyRepository implements CurrencyRepository {

    private static final Map<String, Currency> currencies = new ConcurrentHashMap<>();

    @Override
    public Currency save(Currency currency) {
        currencies.put(currency.code(), currency);
        return currency;
    }

    @Override
    public void deleteByCode(String code) {
        currencies.remove(code);
    }

    @Override
    public Optional<Currency> findByCode(String code) {
        if(currencies.containsKey(code)) {
            return Optional.of(currencies.get(code));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Currency> findAll() {
        return currencies.values().stream().toList();
    }
}
