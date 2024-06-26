package com.marcostonina.nonconstructiblechange.domain;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository {

    Currency save(Currency currency);
    void deleteByCode(String code);
    Optional<Currency> findByCode(String code);
    List<Currency> findAll();

}
