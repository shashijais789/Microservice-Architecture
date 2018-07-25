package com.broadcom.ms.MicroserviceB.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/msb")
public class BResource {
	
	@GetMapping("/")
	public String getName() {
		return "Hello service B";
	}
}
