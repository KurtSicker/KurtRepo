package com.techelevator.model.site;

public interface SiteDAO {

	/***************************************************************
	 * Check to see if the site within the campground is available *
	 * (no reservations already within the user's dates)
	 **************************************************************/
	
	public boolean isSiteAvailable();
	
	
	
	
	
}
