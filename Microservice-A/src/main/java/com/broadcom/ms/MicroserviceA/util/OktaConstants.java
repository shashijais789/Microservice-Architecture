package com.broadcom.ms.MicroserviceA.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import com.broadcom.ms.MicroserviceA.util.GlobalConstants;

@Configuration
@PropertySource("classpath:okta.properties")
public class OktaConstants {
	@Autowired
	private Environment environment;

	private static Map<String, String> localCache = new HashMap<>();

	public static String getPropValue(String key) {
		return localCache.get(key);
	}
	
	@PostConstruct
	private void init() {
		localCache.put(GlobalConstants.client_id, environment.getProperty(GlobalConstants.client_id));
		localCache.put(GlobalConstants.client_secret, environment.getProperty(GlobalConstants.client_secret));
		localCache.put(GlobalConstants.base_url, environment.getProperty(GlobalConstants.base_url));
		localCache.put(GlobalConstants.access_token, environment.getProperty(GlobalConstants.access_token));
		localCache.put(GlobalConstants.refresh_token, environment.getProperty(GlobalConstants.refresh_token));

	}

}

