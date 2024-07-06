package com.marcostonina.nonconstructiblechange.web;

import com.marcostonina.nonconstructiblechange.domain.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class TestCurrencyJson {

    @Autowired
    private JacksonTester<Currency> json;

    @Test
    void testCurrencySerialization() throws IOException {

        var currency = new Currency("Dollar", "USD", Arrays.asList(1,2,3));
        JsonContent<Currency> jsonContent = json.write(currency);
        assertThat(jsonContent).extractingJsonPathStringValue("@.name").isEqualTo(currency.name());
        assertThat(jsonContent).extractingJsonPathStringValue("@.code").isEqualTo(currency.code());
        assertThat(jsonContent).extractingJsonPathArrayValue("@.coins").isEqualTo(currency.coins());
    }

    @Test
    void testCurrencyDeserialization() throws IOException {
        String content = "{\"name\":\"Dollar\",\"code\":\"USD\", \"coins\":[1,2,3]}";
        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(new Currency("Dollar", "USD", Arrays.asList(1,2,3)));
    }
}
