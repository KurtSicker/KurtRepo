package com.techelevator.model.park;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.campground.JdbcCampgroundDAO;
import com.techelevator.model.reservation.JdbcReservationDAO;
import com.techelevator.model.site.JdbcSiteDAO;
import com.techelevator.model.site.SiteandCampground;

public class JdbcParkDAO implements ParkDAO {
	
	private JdbcTemplate       jdbcTemplate;

	public JdbcParkDAO(SingleConnectionDataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}


	/*****************************
	 * Get all of the Park Names *
	 ****************************/
	
	@Override
	public ArrayList<Park> getAllParks() {
		ArrayList<Park> parkList = new ArrayList<Park>();

		String sqlGetAllParks = "select * from park";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllParks);

		while (results.next()) {
			Park parks = mapRowToPark(results);
			parkList.add(parks);
		}

		return parkList;
	}
	
	
	/**********************************************************
	 * Display all of the Info for the SELECTED National Park *
	 *********************************************************/
	
	@Override
	public Park getParkInfo(Long parkId) {
		
		String sqlDisplayParkInfo = "Select * from park where park_id = ? ";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlDisplayParkInfo, parkId);
		Park thePark = mapRowToPark(results);
		return thePark;
	
		
	}
	
	/*******************
	 * Map Row to Park *
	 * @param results  *
	 * @return         *
	 ******************/
	
	private Park mapRowToPark(SqlRowSet results) {
		Park parks = new Park();
		parks.setParkId(results.getInt("park_id"));
		parks.setName(results.getString("name"));
		parks.setLocation(results.getString("location"));
		parks.setEstablishDate(results.getDate("establish_date").toLocalDate());
		parks.setArea(results.getInt("area"));
		parks.setVisitors(results.getInt("visitors"));
		parks.setDescription(results.getString("description"));
		
		return parks;
}
}
