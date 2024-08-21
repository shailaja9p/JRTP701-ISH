package com.w3softtech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

	@Bean(name="template")
	public RestTemplate createRestTemplate() {
		return new RestTemplate();
	}
	@Bean(name="client")
	public WebClient createWebClient() {
		return WebClient.create();
	}
}
