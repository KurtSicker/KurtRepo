package com.techelevator.model.campground;

import java.time.LocalDate;
import java.util.List;

import com.techelevator.model.site.SiteandCampground;

public interface CampgroundDAO {

	
	/**************************************************************************
	 *            Get all Campgrounds within chosen Park by ID                *
	 * Return List of campgrounds (name, open_from_mm, open_to_mm, daily_fee) *
	 *************************************************************************/
	
	public List<Campground> campgroundByParkId(int parkId);
	
	

	
	
	/***********************************************************************
	 * Check if Campground is Open based on the "open" and "closed" season *
	 *   If open, lets user continue...If not, Return to previous menue    *
	 **********************************************************************/
	
	public boolean checkSeason(int campgroundId, LocalDate fromDate, LocalDate toDate);
	
	
	
	
	
	/**************************************
	 * Get the top 5 Available camp sites *
	 *************************************/
	
	public List<SiteandCampground> getAvailableCampSites(int campgroundId, LocalDate fromDate, LocalDate toDate);
	
	
	
	/***********************************
	 * Get camoground by the Park Name *
	 * @param parkName				   *
	 * @return						   *
	 **********************************/
	
	public List<Campground> campgroundByParkName(String parkName);
	
	
	/**********************************
	 * Get campground ID fro the name *
	 * @param name					  *
	 * @return						  *
	 *********************************/
	
	public int getCampgroundIdFromName(String name);
	
}


