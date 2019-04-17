package com.rebvar.nortask.front.service;

import java.util.ArrayList;
import org.json.JSONObject;

public interface LocationWeatherService {

	public String search(String city, String token);
	public String search(double lat, double lon, String token);
	public String getSearchByUniqueId(String uniqueId, String token);
	public JSONObject AddToFavorites(String uniqueId, String token);
	public JSONObject removeFromFavorites(String uniqueId, String token);
	public ArrayList<JSONObject> getAllSearches(String token);
	public ArrayList<JSONObject> getAllFavorites(String token);
	
}
