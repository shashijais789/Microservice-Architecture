package com.broadcom.ms.MicroserviceA.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/msa")
public class AResource {
	
	@GetMapping("/")
	public String getName() {
		return "Hello service A";
	}
}
