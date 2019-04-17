package com.rebvar.nortask.front.common.models;

import java.util.Date;

public abstract class AbstractWeatherModel {
	
	protected double temprature;
	protected String extra;
	protected double lat;
	protected double lon;
	protected boolean locationValid = false;
	protected String city;
	protected long timestamp;
	protected String countryCode;
	protected Date date;
	protected String uniqueId;
	
	
	
	
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public boolean isLocationValid() {
		return locationValid;
	}
	public void setLocationValid(boolean locationValid) {
		this.locationValid = locationValid;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getTemprature() {
		return temprature;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String raw) {
		this.extra = raw;
	}
	
	public void setTemprature(double temprature) {
		this.temprature = temprature;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}



