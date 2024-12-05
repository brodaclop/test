package com.example.test.services;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;


@ActiveProfiles("test")
@SpringBootTest
@EnableWireMock(@ConfigureWireMock( port = 9090))
public class DataFetchServiceTest {

	@Autowired
	private DataFetchService uut;
	
	@Test
	void shouldSignRequestWithApiKey() {
		stubFor(get("/sample-query").withHeader("x-api-key", equalTo("mock_api_key")).willReturn(aResponse().withHeader("Content-Type", "application/json").withBody("42")));
		
		var result = uut.fetch(int.class, "/sample-query");
		
		assertThat(result).isEqualTo(42);		
		
	}
	
}
