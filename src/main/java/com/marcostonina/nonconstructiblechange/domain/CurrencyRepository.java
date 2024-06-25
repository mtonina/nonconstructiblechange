package com.marcostonina.nonconstructiblechange.domain;

import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository {

    public Currency findByCode(String code);

}
