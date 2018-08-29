package com.broadcom.ms.MicroserviceA.resource;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.broadcom.ms.MicroserviceA.util.GlobalConstants;
import com.broadcom.ms.MicroserviceA.util.MSAConstants;
import com.broadcom.ms.MicroserviceA.util.OktaConstants;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/")
public class AResource {

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("hellomsb")
	public String getHelloFromMSB() {
		
		String response;
		HttpHeaders headers = new HttpHeaders();
		headers.set("access_token", OktaConstants.getPropValue(GlobalConstants.access_token));
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(MSAConstants.getPropValue(GlobalConstants.msb_url), HttpMethod.GET, requestEntity, String.class);
		if(HttpStatus.OK.equals(responseEntity.getStatusCode())) {
			response = "Success";
		}else {
			response = "Error";
		}
		return response + requestEntity.getBody();
	}
	
	@HystrixCommand(fallbackMethod="fallbackHello", commandKey="hello", groupKey="msa")
	@GetMapping("hello")
	public String sayHello() {
		//If something wrong happened 
		for(int i=0; i<100; i++) {
			System.out.println("MicroService-A");
		}
		if(RandomUtils.nextBoolean()) {
			throw new RuntimeException("Failed");
		}
		return "Hello, I am MicroService-A!";
	}
	
	public String fallbackHello() {
		return "MicroService-A: Fallback Hello initiated!";
	}
}
