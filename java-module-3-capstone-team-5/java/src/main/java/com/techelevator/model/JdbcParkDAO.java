package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;


@Component
public class JdbcParkDAO implements ParkDAO{
	
	private JdbcTemplate jdbcTemplate;

	public JdbcParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Park> getAllParks() {
		
		List<Park> allParks = new ArrayList<Park>();

		String sqlGetAllParks = "select * " +
							    "from park ";
								
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllParks);

		while (results.next()) {
			Park thePark = mapRowToPark(results);
			allParks.add(thePark);
		}

		return allParks;
	}
	
	private Park mapRowToPark(SqlRowSet results) {

		Park thePark = new Park();

		thePark.setParkCode(results.getString("parkcode"));
		thePark.setParkName(results.getString("parkname"));
		thePark.setState(results.getString("state"));
		thePark.setAcreage(results.getInt("acreage"));
		thePark.setElevationInFeet(results.getInt("elevationinfeet"));
		thePark.setMilesOfTrail(results.getDouble("milesoftrail"));
		thePark.setNumberOfCampsites(results.getInt("numberofcampsites"));
		thePark.setClimate(results.getString("climate"));
		thePark.setYearFounded(results.getInt("yearfounded"));
		thePark.setAnnualVisitorCount(results.getInt("annualvisitorcount"));
		thePark.setQuote(results.getString("inspirationalquote"));
		thePark.setQuoteSource(results.getString("inspirationalquotesource"));
		thePark.setDescription(results.getString("parkdescription"));
		thePark.setEntryFee(results.getInt("entryfee"));
		thePark.setAnimalSpecies(results.getInt("numberofanimalspecies"));
	
		return thePark;
	}

	@Override
	public Park getParkByParkCode(String parkCode) {
		
		Park aPark = new Park();
		
		String sqlGetParkByParkCode = "select * " +
								   "from park " +
								   "where parkcode = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetParkByParkCode, parkCode);

		while (results.next()) {
			aPark = mapRowToPark(results);
		}
		return aPark;
	}

}
