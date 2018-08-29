package com.broadcom.api.gateway.filters;

import java.net.URI;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.broadcom.api.gateway.util.AccessTokenVO;
import com.broadcom.api.gateway.util.GlobalConstants;
import com.broadcom.api.gateway.util.OktaConstants;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import okhttp3.Headers;

@Component
public class AccessCheckFilter extends ZuulFilter{
	
	public static final Logger logger = LoggerFactory.getLogger(AccessCheckFilter.class);

	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
		logger.info("AccessCheckFilter:  " + String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
		
		Headers.Builder headers = new Headers.Builder();
		Enumeration<String> headerNames = request.getHeaderNames();
		String accessToken = null;
		while (headerNames.hasMoreElements()) {
			String name = headerNames.nextElement();
			if(name.equals(GlobalConstants.access_token)) {
				Enumeration<String> values = request.getHeaders(name);
				while (values.hasMoreElements()) {
					String value = values.nextElement();
					accessToken = value;
					logger.info(String.format("HEADER--> %s: %s", name, value));
					headers.add(name, value);
				}
			}
		}
		
		if(accessToken==null || !isAccessTokenValid(accessToken)) {
			ctx.setSendZuulResponse(false);
			ctx.removeRouteHost();
			ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
			ctx.setResponseBody("Access Token Expired!");
		}
		
		return null;
	}
	
	private Boolean isAccessTokenValid(String accessToken) {
		Boolean isTokenValid = false;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(OktaConstants.getPropValue(GlobalConstants.base_url) + 
				OktaConstants.getPropValue(GlobalConstants.sub_introspect_url))
		        .queryParam(GlobalConstants.client_id, OktaConstants.getPropValue(GlobalConstants.client_id))
		        .queryParam(GlobalConstants.client_secret, OktaConstants.getPropValue(GlobalConstants.client_secret))
		        .queryParam(GlobalConstants.token, accessToken)
		        .queryParam(GlobalConstants.token_type_hint, GlobalConstants.access_token);
		URI uri = builder.build(true).toUri();
		logger.info(String.format("URL: %s", uri.toString()));
		ResponseEntity<AccessTokenVO> responseEntity = restTemplate.exchange(uri, 
				HttpMethod.POST, requestEntity, AccessTokenVO.class);
		AccessTokenVO accessTokenVO = responseEntity.getBody();

		if(accessToken != null) {
			isTokenValid = accessTokenVO.getActive();
		}
		return isTokenValid; 
	}

	@Override
	public String filterType() {
		return GlobalConstants.zuul_filter_type_pre;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
