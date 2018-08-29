package com.broadcom.ms.MicroserviceB.resources;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/")
public class BResource {

	@HystrixCommand(fallbackMethod="fallbackHello", commandKey="hello", groupKey="msb")
	@GetMapping("hello")
	public String sayHello() {
		//If something wrong happened 
		if(RandomUtils.nextBoolean()) {
			throw new RuntimeException("Failed");
		}
		return "Hello I am MicroService B!";
	}
	
	public String fallbackHello() {
		return "MicroService-B: Fallback Hello initiated!";
	}
}
