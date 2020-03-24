package com.techelevator.model;

public class Weather {
	
	private String parkCode;
	private int fiveDayValue;
	private int low;
	private int high;
	private String forecast;
	private String weatherAdvisory;
	private String tempAdvisory;
	private String differenceAdvisory;
	
	
	
	
	
	
	public String getWeatherAdvisory() {
		return weatherAdvisory;
	}
	public void setWeatherAdvisory(String weatherAdvisory) {
		this.weatherAdvisory = weatherAdvisory;
	}
	public String getTempAdvisory() {
		return tempAdvisory;
	}
	public void setTempAdvisory(String tempAdvisory) {
		this.tempAdvisory = tempAdvisory;
	}
	public String getDifferenceAdvisory() {
		return differenceAdvisory;
	}
	public void setDifferenceAdvisory(String differenceAdvisory) {
		this.differenceAdvisory = differenceAdvisory;
	}
	public String getParkCode() {
		return parkCode;
	}
	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}
	public int getFiveDayValue() {
		return fiveDayValue;
	}
	public void setFiveDayValue(int fiveDayValue) {
		this.fiveDayValue = fiveDayValue;
	}
	public int getLow() {
		return low;
	}
	public void setLow(int low) {
		this.low = low;
	}
	public int getHigh() {
		return high;
	}
	public void setHigh(int high) {
		this.high = high;
	}
	public String getForecast() {
		return forecast;
	}
	public void setForecast(String forecast) {
		this.forecast = forecast;
	}
	
	
	
	

}
