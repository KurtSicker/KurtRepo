package com.techelevator.model.campground;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.site.SiteandCampground;

public class JdbcCampgroundDAO implements CampgroundDAO {
	public JdbcTemplate jdbcTemplate;

	
	public JdbcCampgroundDAO(SingleConnectionDataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}


	/***********************************************************
	 * Check to be sure that the Campground is in season(open) *
	 **********************************************************/
	
	@Override
	public boolean checkSeason(int theCampgroundId, LocalDate fromDate, LocalDate toDate) {

		String sqlCheckSeason = "select * from campground where campground_id = ? ";
		int userFrom = fromDate.getMonthValue();
		int userTo = toDate.getMonthValue();

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlCheckSeason, theCampgroundId);
		boolean isOpen = false;
		int tableFrom = 0;
		int tableTo = 0;

		if (results.next()) {
			tableFrom = results.getInt(4);
			tableTo = results.getInt(5);

			if (userFrom >= tableFrom && userTo < tableTo) {
				isOpen = true;
			}

		}
		return isOpen;
	}

	
	/**************************************
	 * List all Campground by the Park ID *
	 *************************************/
	
	@Override
	public List<Campground> campgroundByParkId(int parkId) {
		List<Campground> campgroundList = new ArrayList<Campground>();

		String sqlCampgroundByParkId = "select * from campground where park_id = ?";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlCampgroundByParkId, parkId);

		while (results.next()) {
			Campground campgrounds = mapRowToCampground(results);
			campgroundList.add(campgrounds);

		}

		return campgroundList;
	}
	/******************************************************
	 * Get campground Id given the name of the campground
	 ******************************************************/
	public int getCampgroundIdFromName(String name) {
		String sqlGetCampgroundIdFromName = "select * from campground where name = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetCampgroundIdFromName, name);
		Campground theCampground = null;
		while(results.next()) {
			theCampground = mapRowToCampground(results);
		}
		return theCampground.getCampgroundId();
	}
	/******************************************************
	 * Get campground List given a Park name
	 ******************************************************/
	
	public List<Campground> campgroundByParkName(String parkName) {
		List<Campground> campgroundList = new ArrayList<Campground>();

		String sqlCampgroundByParkName = "select * from campground "
				+ "join park on park.park_id = campground.park_id where park.name = ?";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlCampgroundByParkName, parkName);

		while (results.next()) {
			Campground campgrounds = mapRowToCampground(results);
			campgroundList.add(campgrounds);

		}

		return campgroundList;
	}
	
	/*********************************************************
	 *  Take User's Arrival and Departure date and retrieve  *
	 *   the Top (5) AVAILABLE Camp Sites (IF available!)	 *     
	 ********************************************************/
	
	@Override
	public List<SiteandCampground> getAvailableCampSites(int campgroundId, LocalDate fromDate, LocalDate toDate) {

		String sqlGetAvailableCampSite = ("select * from site, campground "
				+ " where site.campground_id = ? and site_id not in (Select site.site_id from reservation join site on site.site_id = reservation.site_id " 
						+ " join campground on campground.campground_id = site.campground_id "
						+ " where site.campground_id = ? and ((? between reservation.from_date and reservation.to_date) "
						+ " or (? between reservation.from_date and reservation.to_date) " 
						+ " or (reservation.from_date between ? and ?) " 
						+ " or (reservation.to_date between ? and ?))) "
						+ " Limit 5");
			
		List<SiteandCampground> siteList = new ArrayList<SiteandCampground>();
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAvailableCampSite, campgroundId, campgroundId, fromDate, toDate,
														fromDate, toDate, fromDate, toDate);
			while (results.next()) {
				SiteandCampground sites = mapRowToSite(results);
			siteList.add(sites);
				
			}
	return siteList;
	}
	/*********************************************************
	 *  Calculate the total fee of the stay given            *
	 *   the dates the reservation starts and ends       	 *     
	 ********************************************************/
	
	
	public BigDecimal calculateFees(BigDecimal dailyFee, LocalDate fromDate, LocalDate toDate) {
			
		String sqlCalculateFees = ("select date(?) - date(?)");
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlCalculateFees, toDate, fromDate);
		BigDecimal theTotalFee = new BigDecimal(0);
		while(results.next()) {
			BigDecimal theDays = results.getBigDecimal(1);
		 theTotalFee = (theDays.multiply(dailyFee));
		}
		return theTotalFee;
		
	}
	
	/******************************************
	 * Get the Daily fee by the Campground ID *
	 * @param campground_id                   *
	 * @return								  *
	 *****************************************/
	
	public BigDecimal getDailyFeeByCampgroundId(int campground_id) {
		String sqlGetDailyFeeByCampgroundId = ("select daily_fee from campground where campground_id = ?");
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetDailyFeeByCampgroundId, campground_id);
		BigDecimal dailyFee = new BigDecimal(0);
		while(results.next()) {
		dailyFee = results.getBigDecimal(1);
		}
		return dailyFee;
	}

	
	/*************************
	 * Map Row To Campground *
	 ************************/
	 
	private Campground mapRowToCampground(SqlRowSet results) {
		Campground campgrounds = new Campground();
		campgrounds.setCampgroundId(results.getInt("campground_id"));
		campgrounds.setParkId(results.getInt("park_id"));
		campgrounds.setName(results.getString("name"));
		campgrounds.setOpenFrom(results.getString("open_from_mm"));
		campgrounds.setOpenTo(results.getString("open_to_mm"));
		campgrounds.setDailyFee(results.getBigDecimal("daily_fee"));

		return campgrounds;
	}
	
	
	/*******************
	 * Map Row To Site *
	*******************/
	
	
	private SiteandCampground mapRowToSite(SqlRowSet results) {
		SiteandCampground sites = new SiteandCampground();
		sites.setSiteId(results.getLong("site_id"));
		sites.setCampgroundId(results.getInt("campground_id"));
		sites.setSiteNumber(results.getInt("site_number"));
		sites.setMaxOccupancy(results.getInt("max_occupancy"));
		sites.setAccessible(results.getBoolean("accessible"));
		sites.setMaxRvLength(results.getInt("max_rv_length"));
		sites.setUtilities(results.getBoolean("utilities"));
		
		return sites;
}
}


