package com.broadcom.ms.MicroserviceA.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/msa")
public class AResource {
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/")
	public String getName() {
		
		String response = "Hello service A and ";
		
		//Calling another service registered with Eureka server
		ResponseEntity<String> responseEntity = 
		restTemplate.exchange("http://microservice-b/msb/", HttpMethod.GET, null, String.class);
		response = response + responseEntity.getBody();
		
		return response;
	}
}
