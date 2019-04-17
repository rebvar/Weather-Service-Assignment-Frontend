package com.rebvar.nortask.front.service;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.rebvar.nortask.front.AppConstants;

public class ServiceHelper {

	public static HttpEntity<String> prepareRestRequest(String uri, HttpMethod method, JSONObject request, String token)
	{
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Accept", AppConstants.APPLICATION_JSON);
	    headers.add("Content-Type", AppConstants.APPLICATION_JSON);
	    if (!token.isEmpty())
	    {
	    	headers.add(AppConstants.HEADER_STRING, token);
	    }
	    HttpEntity<String> requestEntity = new HttpEntity<String>(request.toString(), headers);
	    RestTemplate restTemplate = new RestTemplate();
    	return restTemplate.exchange(uri,method, requestEntity, String.class);
	}
	
}
