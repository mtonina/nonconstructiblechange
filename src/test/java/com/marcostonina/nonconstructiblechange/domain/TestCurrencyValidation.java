package com.marcostonina.nonconstructiblechange.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TestCurrencyValidation {
    private static Validator validator;

    @BeforeAll
    static void setUpBeforeClass() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void testCurrencyValidation_completed_valid() {
        Currency currency = new Currency("Dollar", "USD", Arrays.asList(1, 2, 5, 25));
        Set<ConstraintViolation<Currency>> result = validator.validate(currency);
        assertThat(result).isEmpty();
    }

    @Test
    void testCurrencyValidation_null_name() {
        Currency currency = new Currency(null, "USD", Arrays.asList(1, 2, 5, 25));
        Set<ConstraintViolation<Currency>> result = validator.validate(currency);
        assertThat(result).hasSize(1);
        assertThat(result.iterator().next().getMessage()).isEqualTo("Name must be defined");
    }

    @Test
    void testCurrencyValidation_null_code() {
        Currency currency = new Currency("Dollar", null, Arrays.asList(1, 2, 5, 25));
        Set<ConstraintViolation<Currency>> result = validator.validate(currency);
        assertThat(result).hasSize(1);
        assertThat(result.iterator().next().getMessage()).isEqualTo("Code must be defined");
    }

    @Test
    void testCurrencyValidation_invalid_code() {
        Currency currency = new Currency("Dollar", "USDT", Arrays.asList(1, 2, 5, 25));
        Set<ConstraintViolation<Currency>> result = validator.validate(currency);
        assertThat(result).hasSize(1);
        assertThat(result.iterator().next().getMessage())
                .isEqualTo("Currency code should be between 2 and 3 characters");
    }

    @Test
    void testCurrencyValidation_invalid_code_min() {
        Currency currency = new Currency("Dollar", "U", Arrays.asList(1, 2, 5, 25));
        Set<ConstraintViolation<Currency>> result = validator.validate(currency);
        assertThat(result).hasSize(1);
        assertThat(result.iterator().next().getMessage())
                .isEqualTo("Currency code should be between 2 and 3 characters");
    }

    @Test
    void testCurrencyValidation_null_coins() {
        Currency currency = new Currency("Dollar", "USD", null);
        Set<ConstraintViolation<Currency>> result = validator.validate(currency);
        assertThat(result).hasSize(1);
        assertThat(result.iterator().next().getMessage())
                .isEqualTo("Coin list cannot be null");
    }

    @Test
    void testCurrencyValidation_empty_coins() {
        Currency currency = new Currency("Dollar", "USD", Collections.emptyList());
        Set<ConstraintViolation<Currency>> result = validator.validate(currency);
        assertThat(result).hasSize(1);
        assertThat(result.iterator().next().getMessage())
                .isEqualTo("Coin list should have at least one value");
    }

}
