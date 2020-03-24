package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcWeatherDAO implements WeatherDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JdbcWeatherDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Weather> getWeatherByParkCode(String parkCode, String preferedTemp) {
		
		List<Weather> parkWeather = new ArrayList<Weather>();

		String sqlGetWeatherByParkCode = "select * " +
							    		 "from weather " +
							    		 "where parkcode = ?";
								
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetWeatherByParkCode, parkCode);

		while (results.next()) {
			Weather theWeather = mapRowToWeather(results, preferedTemp);
			parkWeather.add(theWeather);
			
		}

		return parkWeather;
		
	}
	
	private Weather mapRowToWeather(SqlRowSet results, String preferedTemp) {

		Weather parkWeather = new Weather();
		
		parkWeather.setParkCode(results.getString("parkcode"));
		parkWeather.setFiveDayValue(results.getInt("fivedayforecastvalue"));
		parkWeather.setForecast(results.getString("forecast"));
		
		if (preferedTemp.equals("F")) {
			parkWeather.setLow(results.getInt("low"));
			parkWeather.setHigh(results.getInt("high"));
			
			if(parkWeather.getHigh() > 75) {
				parkWeather.setTempAdvisory("Bring an extra gallon of water");
			}
			if(parkWeather.getLow() < 20 ) {
				parkWeather.setTempAdvisory("Cold exposure warning");
			}
			if(parkWeather.getHigh() < 75 && parkWeather.getLow() > 20) {
				parkWeather.setTempAdvisory("");
			}
			if(parkWeather.getHigh() - parkWeather.getLow() > 20) { 
				parkWeather.setDifferenceAdvisory("Wear breathable layers");
			}else { parkWeather.setDifferenceAdvisory("");
			}
			
		}
		else {
			parkWeather.setLow((results.getInt("low") - 32) * 5 / 9);
			parkWeather.setHigh((results.getInt("high") - 32) * 5 / 9);
			
			if(parkWeather.getHigh() > 23) {
				parkWeather.setTempAdvisory("Bring an extra gallon of water");
			}
			if(parkWeather.getLow() < -6 ) {
				parkWeather.setTempAdvisory("Cold exposure warning");
			}
			
			if(parkWeather.getHigh() < 23 && parkWeather.getLow() > -6) {
				parkWeather.setTempAdvisory("");
			}
			
			if(parkWeather.getHigh() - parkWeather.getLow() > 6) { 
				// Frank said this logic is fine
				parkWeather.setDifferenceAdvisory("Wear breathable layers");
			}else { parkWeather.setDifferenceAdvisory("");
			}	
		}
		
		if(parkWeather.getForecast().equals("snow")) {
			parkWeather.setWeatherAdvisory("Pack snowshoes");
		}
		if(parkWeather.getForecast().equals("thunderstorms")) {
			parkWeather.setWeatherAdvisory("Seek shelter and avoid hiking on exposed ridges");
		}
		if(parkWeather.getForecast().equals("rain")) {
			parkWeather.setWeatherAdvisory("Pack rain gear and wear waterproof shoes");
		}
		if(parkWeather.getForecast().equals("sunny")) {
			parkWeather.setWeatherAdvisory("Pack sunblock");
		}
		if(parkWeather.getForecast().equals("partly cloudy")) {
			parkWeather.setWeatherAdvisory("");
		}
		
		
		return parkWeather;
	}

	
	

}
