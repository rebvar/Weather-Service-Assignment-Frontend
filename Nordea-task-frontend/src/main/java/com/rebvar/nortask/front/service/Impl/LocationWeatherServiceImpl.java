package com.rebvar.nortask.front.service.Impl;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.rebvar.nortask.front.AppConstants;
import com.rebvar.nortask.front.service.LocationWeatherService;
import com.rebvar.nortask.front.service.ServiceHelper;

@Service
public class LocationWeatherServiceImpl implements LocationWeatherService {
	
	@Override
	public JSONObject AddToFavorites(String uniqueId, String token) {
		String uri = AppConstants.REST_BASE+AppConstants.FAVORITES_ENDPOINT;
	    JSONObject request = new JSONObject();
	    request.put("uniqueId", uniqueId);    
	    HttpEntity<String> response = ServiceHelper.prepareRestRequest(uri, HttpMethod.POST, request, token);
	    return new JSONObject(response.getBody());		
	}

	
	@Override
	public ArrayList<JSONObject> getAllSearches(String token)
	{
		String uri = AppConstants.REST_BASE+AppConstants.SEARCHES_ENDPOINT;
		ArrayList<JSONObject> ret = new ArrayList<>();
		JSONObject request = new JSONObject();
		HttpEntity<String> response = ServiceHelper.prepareRestRequest(uri, HttpMethod.GET, request, token);
		
		
		String body = response.getBody();
		JSONArray ar = new JSONArray(body);
		
		for (int i = 0;i<ar.length();i++)
		{
			ret.add(ar.getJSONObject(i));
		}
		return ret;
	}
	
	
	@Override
	public ArrayList<JSONObject> getAllFavorites(String token) {
		
		String uri = AppConstants.REST_BASE+AppConstants.FAVORITES_ENDPOINT;
		ArrayList<JSONObject> ret = new ArrayList<>();
		JSONObject request = new JSONObject();
		HttpEntity<String> response = ServiceHelper.prepareRestRequest(uri, HttpMethod.GET, request, token);
				
		String body = response.getBody();
		JSONArray ar = new JSONArray(body);
		
		for (int i = 0;i<ar.length();i++)
		{
			ret.add(ar.getJSONObject(i));
		}
		return ret;
	}
	

	@Override
	public JSONObject removeFromFavorites(String uniqueId, String token) {
		String uri = AppConstants.REST_BASE+AppConstants.FAVORITES_ENDPOINT;
	    JSONObject request = new JSONObject();	    
	    request.put("uniqueId", uniqueId);	    
	    HttpEntity<String> response = ServiceHelper.prepareRestRequest(uri, HttpMethod.DELETE, request, token);
    	return new JSONObject(response.getBody());	
	}
	
	
	@Override
	public String search(String city, String token) {
		String uri = AppConstants.REST_BASE+AppConstants.WEATHER_BASE_URL+"/city/"+city;
		JSONObject request = new JSONObject();
		return ServiceHelper.prepareRestRequest(uri, HttpMethod.GET, request, token).getBody();
	}

	@Override
	public String search(double lat, double lon, String token) {
		String uri = AppConstants.REST_BASE+AppConstants.WEATHER_BASE_URL+"/lat/"+String.valueOf(lat)+"/lon/"+String.valueOf(lon);
		return ServiceHelper.prepareRestRequest(uri, HttpMethod.GET, new JSONObject(), token).getBody();
	}

	@Override
	public String getSearchByUniqueId(String uniqueId, String token) {
		String uri = AppConstants.REST_BASE+AppConstants.WEATHER_BASE_URL+"/"+uniqueId;
		return ServiceHelper.prepareRestRequest(uri, HttpMethod.GET, new JSONObject(), token).getBody();
	}
}
