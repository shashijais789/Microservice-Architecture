package com.broadcom.ms.MicroserviceA.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:msa.properties")
public class MSAConstants {
	
	@Autowired
	private Environment environment;

	private static Map<String, String> localCache = new HashMap<>();

	public static String getPropValue(String key) {
		return localCache.get(key);
	}

	@PostConstruct
	private void init() {
		localCache.put(GlobalConstants.msb_url, environment.getProperty(GlobalConstants.msb_url));
	}

}
