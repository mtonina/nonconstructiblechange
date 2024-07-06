package com.marcostonina.nonconstructiblechange.web;

import com.marcostonina.nonconstructiblechange.domain.CurrencyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CurrencyController.class)
public class TestCurrencyController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyService currencyService;

    @Test
    public void whenGetNonExistingCurrencyThenReturn404() throws Exception {

        given(currencyService
                .getCurrency("ABC")).willThrow(IllegalArgumentException.class);

        mockMvc
                .perform(get("/currencies/ABC"))
                .andExpect(status().isNotFound());

    }

}
