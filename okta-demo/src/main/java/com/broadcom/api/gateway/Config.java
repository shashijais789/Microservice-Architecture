package com.broadcom.api.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import com.broadcom.api.gateway.filters.AccessCheckFilter;

@Configuration
public class Config {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public AccessCheckFilter preFilter() {
		return new AccessCheckFilter();
	}

}
