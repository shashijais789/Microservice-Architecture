package com.broadcom.resource;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/rest")
public class MainResource {

	@HystrixCommand(fallbackMethod="fallbackHello", commandKey="hello", groupKey="hello")
	@GetMapping("/hello")
	public String sayHello() {
		//If something wrong happened 
		if(RandomUtils.nextBoolean()) {
			throw new RuntimeException("Failed");
		}
		return "Hello World !";
	}
	
	public String fallbackHello() {
		return "Fallback Hello initiated !";
	}
}
