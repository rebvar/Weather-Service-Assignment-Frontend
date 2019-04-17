package com.rebvar.nortask.front.ui.controller;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rebvar.nortask.front.AppConstants;
import com.rebvar.nortask.front.service.LocationWeatherService;
import com.rebvar.nortask.front.ui.model.request.LocationRequestModel;

@Controller
public class LocationWeatherController {
	
	@Autowired
	LocationWeatherService locationWeatherService;
	
	@RequestMapping(path = "/show-item/{id}",method = RequestMethod.GET)
	public String showSavedSearch(
			@RequestParam(value=AppConstants.METRIC_PARAM_NAME, defaultValue = AppConstants.METRIC_CEL) String metric,
			@PathVariable String id, @CookieValue(value=AppConstants.HEADER_STRING, defaultValue = "") String authCookie,
			Model model)
	{
		try
		{
			String respBody = locationWeatherService.getSearchByUniqueId(id, authCookie);
			JSONObject weatherJSON = new JSONObject(respBody);
			
			
			
			model.addAttribute("city", weatherJSON.get("city"));
		    model.addAttribute("sortedKeys", AppConstants.DETAILS_COLS);
			model.addAttribute("weatherInfo", weatherJSON);
			
			ArrayList<JSONObject> forecasts = new ArrayList<JSONObject>();
			JSONArray jsonForecasts = weatherJSON.getJSONArray("forecasts");
			
			
			
			
			jsonForecasts.forEach(obj -> forecasts.add((JSONObject)obj));
			forecasts.sort((JSONObject j1, JSONObject j2)->Double.compare(j1.getDouble("timestamp"),j2.getDouble("timestamp")));
			forecasts.forEach(w -> w.put("date", new Date(w.getLong("timestamp")*1000).toString()));
			
			if (metric.compareTo(AppConstants.METRIC_CEL) == 0)
			{
				weatherJSON.put(AppConstants.TEMPRATURE, weatherJSON.getDouble(AppConstants.TEMPRATURE)-273.15);
			
				forecasts.forEach(w -> w.put(AppConstants.TEMPRATURE, w.getDouble(AppConstants.TEMPRATURE)-273.15));
			}
			
			
			model.addAttribute("forecasts", forecasts);
			model.addAttribute("fcKeys", AppConstants.FORECAST_COLS);
			return "report/singleload";
		}
		catch(Exception ex)
		{
			model.addAttribute("error", "Error retrieving the data..."+ex.toString());
			return "redirect:/";
		}
	}
	
	
	@RequestMapping(path = "/search-city",method = RequestMethod.POST)
	public String getWeather(
			@RequestParam(value=AppConstants.METRIC_PARAM_NAME, defaultValue = AppConstants.METRIC_CEL) String metric,
			@CookieValue(value=AppConstants.HEADER_STRING, defaultValue = "") String authCookie, LocationRequestModel locationData,  Model model) {
		
		try
		{	
			String city = locationData.getCity();
			
			String locationInfoStr = locationWeatherService.search(city, authCookie);
			JSONObject weatherJSON = new JSONObject(locationInfoStr);

			
		    model.addAttribute("city", locationData.getCity());
		    model.addAttribute("sortedKeys", AppConstants.DETAILS_COLS);
			model.addAttribute("weatherInfo", weatherJSON);
			
			ArrayList<JSONObject> forecasts = new ArrayList<JSONObject>();
			JSONArray jsonForecasts = weatherJSON.getJSONArray("forecasts");
			
			jsonForecasts.forEach(obj -> forecasts.add((JSONObject)obj));
			forecasts.sort((JSONObject j1, JSONObject j2)->Double.compare(j1.getDouble("timestamp"),j2.getDouble("timestamp")));
			forecasts.forEach(w -> w.put("date", new Date(w.getLong("timestamp")*1000).toString()));
			
			if (metric.compareTo(AppConstants.METRIC_CEL) == 0)
			{
				weatherJSON.put(AppConstants.TEMPRATURE, weatherJSON.getDouble(AppConstants.TEMPRATURE)-273.15);
			
				forecasts.forEach(w -> w.put(AppConstants.TEMPRATURE, w.getDouble(AppConstants.TEMPRATURE)-273.15));
			}
			
			model.addAttribute("forecasts", forecasts);
			model.addAttribute("fcKeys", AppConstants.FORECAST_COLS);
			
			return "report/single";
		}
		catch(Exception ex)
		{
			model.addAttribute("weatherInfo", locationData);
			model.addAttribute("error", "Error in searching for city: "+ex.toString());
			return "search/city";
		}
    }
	
	@RequestMapping(path = "/search-city",method = RequestMethod.GET)
	public String getWeather(@CookieValue(value=AppConstants.HEADER_STRING, defaultValue = "") String authCookie,  Model model) {
		model.addAttribute("weatherInfo", new LocationRequestModel());
		return "search/city";	
    }
	
	
	@RequestMapping(path = "/search-coord",method = RequestMethod.POST)
	public String getWeatherCoord(
			@RequestParam(value=AppConstants.METRIC_PARAM_NAME, defaultValue = AppConstants.METRIC_CEL) String metric,
			@CookieValue(value=AppConstants.HEADER_STRING, defaultValue = "") String authCookie, LocationRequestModel locationData,  Model model) {
		
		try
		{	
			double lat = locationData.getLat();
			double lon = locationData.getLon();
			String locationInfoStr = locationWeatherService.search(lat,lon, authCookie);
			JSONObject weatherJSON = new JSONObject(locationInfoStr);
			
			
		    model.addAttribute("city", locationData.getCity());
		    model.addAttribute("sortedKeys", AppConstants.DETAILS_COLS);
			model.addAttribute("weatherInfo", weatherJSON);
			
			ArrayList<JSONObject> forecasts = new ArrayList<JSONObject>();
			JSONArray jsonForecasts = weatherJSON.getJSONArray("forecasts");
			
			jsonForecasts.forEach(obj -> forecasts.add((JSONObject)obj));
			
			forecasts.sort((JSONObject j1, JSONObject j2)->Double.compare(j1.getDouble("timestamp"),j2.getDouble("timestamp")));
			forecasts.forEach(w -> w.put("date", new Date(w.getLong("timestamp")*1000).toString()));
			
			if (metric.compareTo(AppConstants.METRIC_CEL) == 0)
			{
				weatherJSON.put(AppConstants.TEMPRATURE, weatherJSON.getDouble(AppConstants.TEMPRATURE)-273.15);
			
				forecasts.forEach(w -> w.put(AppConstants.TEMPRATURE, w.getDouble(AppConstants.TEMPRATURE)-273.15));
				
			}
			
			model.addAttribute("forecasts", forecasts);
			model.addAttribute("fcKeys", AppConstants.FORECAST_COLS);
			
			return "report/single";
		}
		catch(Exception ex)
		{
			model.addAttribute("weatherInfo", locationData);
			model.addAttribute("error", "Error in searching for city: "+ex.toString());
			return "search/coord";
		}
    }
	
	
	@RequestMapping(path = "/search-coord",method = RequestMethod.GET)
	public String getWeatherCoord(@CookieValue(value=AppConstants.HEADER_STRING, defaultValue = "") String authCookie,  Model model) {
		model.addAttribute("weatherInfo", new LocationRequestModel());
		return "search/coord";	
    }
	
	
	@RequestMapping(path = "/favorites/{id}",method = RequestMethod.GET)
	public String addToFavorites(@PathVariable String id,@CookieValue(value=AppConstants.HEADER_STRING, defaultValue = "") String authCookie, Model model)
	{
		try
		{	
			JSONObject added = locationWeatherService.AddToFavorites(id, authCookie);
		}
		catch(Exception ex)
		{
			model.addAttribute("error", "Error in searching for city: "+ex.toString());
		}
		
		return "redirect:/show-item/"+id;
	}
	
	
	@RequestMapping(path = "/my-searches",method = RequestMethod.GET)
	public String mySearches(
			@RequestParam(value=AppConstants.METRIC_PARAM_NAME, defaultValue = AppConstants.METRIC_CEL) String metric,
			@CookieValue(value=AppConstants.HEADER_STRING, defaultValue = "") String authCookie,  Model model)
	{
		if (authCookie.isEmpty())
		{
			return "redirect:/";
		}
		
		ArrayList<JSONObject> userSearches = locationWeatherService.getAllSearches(authCookie);
		
		if (metric.compareTo(AppConstants.METRIC_CEL) == 0)
		{
		
			userSearches.forEach(w -> w.put(AppConstants.TEMPRATURE, w.getDouble(AppConstants.TEMPRATURE)-273.15));
		}
		model.addAttribute("sortedKeys", AppConstants.SEARCH_GRID_COLS);
		model.addAttribute("userSearches", userSearches);
		return "report/mysearches";
	}
	
	
	@RequestMapping(path = "/my-favorites",method = RequestMethod.GET)
	public String myFavorites(
			@RequestParam(value=AppConstants.METRIC_PARAM_NAME, defaultValue = AppConstants.METRIC_CEL) String metric,
			@CookieValue(value=AppConstants.HEADER_STRING, defaultValue = "") String authCookie,  Model model)
	{
		if (authCookie.isEmpty())
		{
			return "redirect:/";
		}
		
		
		ArrayList<JSONObject> userSearches = locationWeatherService.getAllFavorites(authCookie);
		
		if (metric.compareTo(AppConstants.METRIC_CEL) == 0)
		{
			userSearches.forEach(w -> w.put(AppConstants.TEMPRATURE, w.getDouble(AppConstants.TEMPRATURE)-273.15));
		}
		
		model.addAttribute("sortedKeys", AppConstants.SEARCH_GRID_COLS);
		model.addAttribute("userSearches", userSearches);
		
		return "report/myfavorites";
		
	}
	
	@RequestMapping(path = "/unfavorite/{id}",method = RequestMethod.GET)
	public String removeFromFavorite(@PathVariable String id,@CookieValue(value=AppConstants.HEADER_STRING, defaultValue = "") String authCookie, Model model)
	{
		try
		{	
			JSONObject deleted = locationWeatherService.removeFromFavorites(id, authCookie);	
		}
		catch(Exception ex)
		{
			model.addAttribute("error", "Error in searching for city: "+ex.toString());
		}
		
		return "redirect:/show-item/"+id;
	}
}
