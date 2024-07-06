package com.marcostonina.nonconstructiblechange;

import com.marcostonina.nonconstructiblechange.domain.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NonconstructiblechangeApplicationTests {

	@Autowired
	private WebTestClient webClient;

	@BeforeEach
	public void beforeAll() {
		webClient = webClient.mutate()
				.responseTimeout(Duration.ofMillis(30000))
				.build();
	}

	@Test
	void whenPostRequestThenCurrencyCreated() {
		var currency = new Currency("Dollar", "USD", Arrays.asList(1,2,3));

		webClient
				.post()
				.uri("currencies")
				.bodyValue(currency)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Currency.class).value(actualCurrency -> {
					assertThat(actualCurrency).isNotNull();
					assertThat(actualCurrency.name()).isEqualTo(currency.name());
					assertThat(actualCurrency.code()).isEqualTo(currency.code());
					assertThat(actualCurrency.coins()).hasSameElementsAs(currency.coins());
				});

	}

	@Test
	void whenGetRequestWithCurrencyCreatedThenCurrencyReturned() {
		var currency = new Currency("Dollar", "USD", Arrays.asList(1,2,3));

		webClient
				.post()
				.uri("currencies")
				.bodyValue(currency)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Currency.class).value(actualCurrency -> {
					assertThat(actualCurrency).isNotNull();
					assertThat(actualCurrency.name()).isEqualTo(currency.name());
					assertThat(actualCurrency.code()).isEqualTo(currency.code());
					assertThat(actualCurrency.coins()).hasSameElementsAs(currency.coins());
				});
		webClient
				.get()
				.uri("currencies")
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(Currency.class).value(currencyList -> {
					assertThat(currencyList).isNotNull();
					assertThat(currencyList).hasSize(1);
					currencyList.forEach(actualCurrency -> {
						assertThat(actualCurrency.name()).isEqualTo(currency.name());
						assertThat(actualCurrency.code()).isEqualTo(currency.code());
						assertThat(actualCurrency.coins()).hasSameElementsAs(currency.coins());
					});
				});

	}

}
