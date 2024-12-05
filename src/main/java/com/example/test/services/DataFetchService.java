package com.example.test.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class DataFetchService {
	@Value("${api_key}")
	private String apiKey;
	
	@Value("${proxy_host}")
	private String proxyHost;
	
	public <T> T fetch(Class<T> outputClass, String uriTemplate, Object... params) {
		var client = RestClient.builder()
				.baseUrl(proxyHost)
				.defaultHeader("x-api-key", apiKey)
				.build();
		
		return client.get().uri(uriTemplate, params)
				.retrieve()
				.body(outputClass);
	}
}
