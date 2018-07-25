package com.broadcom.ms.admin.msadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.config.EnableAdminServer;

@EnableAdminServer
@SpringBootApplication
public class MsAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAdminApplication.class, args);
	}
}
