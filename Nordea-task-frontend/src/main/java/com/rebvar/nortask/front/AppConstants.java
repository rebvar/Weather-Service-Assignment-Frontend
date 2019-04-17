package com.rebvar.nortask.front;

public class AppConstants {
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";
    public static final String LOGIN_URL = "/users/login";
    public static final String USER_DETAILS_URL = "/users";
    public static final String WEATHER_CITY_ENDPOINT = "/weather/city/*";
    public static final String WEATHER_LAT_LON_ENDPOINT = "/weather/lat/*/lon/*";
    public static final String WEATHER_LON_LAT_ENDPOINT = "/weather/lon/*/lat/*";
    public static final String INVALID_AUTH_DEFAULT_VALUE = "!INVALID_AUTH_TOKEN!";
    public static final String REST_BASE = "http://localhost:8080/nordea-task-ws";
    public static final String APPLICATION_JSON = "application/json";
    public static final String WEATHER_BASE_URL = "/weather";
    public static final String FAVORITES_ENDPOINT = WEATHER_BASE_URL + "/favorites";
    public static final String SEARCHES_ENDPOINT = WEATHER_BASE_URL+ "/searches";
	public static final String[] DETAILS_COLS = {"city","countryCode","lat","lon","date","temprature","humidity", 
													"skyCloud","pressure","windSpeed","windDirection"};
	public static final String[] FORECAST_COLS = {"temprature","humidity","skyCloud","windSpeed","pressure", "date"};
	public static final String[] SEARCH_GRID_COLS = {"city","countryCode","date","temprature"};
	public static final String METRIC_CEL = "CEL";
	public static final String METRIC_KEL = "KEL";
	public static final String METRIC_PARAM_NAME = "metric";
	public static final String TEMPRATURE = "temprature";
}
