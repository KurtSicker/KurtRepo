package com.techelevator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.campground.Campground;
import com.techelevator.model.campground.CampgroundDAO;
import com.techelevator.model.campground.JdbcCampgroundDAO;
import com.techelevator.model.park.JdbcParkDAO;
import com.techelevator.model.park.Park;
import com.techelevator.model.park.ParkDAO;
import com.techelevator.model.reservation.*;
import com.techelevator.model.site.JdbcSiteDAO;
import com.techelevator.model.site.SiteDAO;
import com.techelevator.view.*;

public class CampgroundApp {

	/****************************************************************************************************
	 * This is the Campground Reservation system application program
	 * 
	 * Any and all user interactions should be placed in the CampgroundUI class
	 * which is in the com.techelevator.view package.
	 * 
	 * This application program should instantiate a CampgroundUI object and use its
	 * methods to interact with the user.
	 * 
	 * This application program should contain no user interactions.
	 * 
	 * Any and all database accesses should be placed in the appropriate
	 * com.techelevator.model.tablename package component
	 * 
	 * This application program should instantiate DAO objects and use the methods
	 * in the DAO to interact with the database tables.
	 * 
	 * There should be no SQL in this application program
	 * 
	 ***************************************************************************************************/

	public static void main(String[] args) {

		String PARK_MENU_VIEW_CAMPGROUNDS = "View Campgrounds";
		String PARK_MENU_SEARCH_FOR_RESERVATION = "Search For Reservation";
		String PARK_MENU_RETURN_TO_PREVIOUS_SCREEN = "Return To Previous Screen";
		String[] PARK_MENU_OPTIONS = new String[] { PARK_MENU_VIEW_CAMPGROUNDS, PARK_MENU_SEARCH_FOR_RESERVATION,
				PARK_MENU_RETURN_TO_PREVIOUS_SCREEN };

		SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/testdb");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundUI userInterface = new CampgroundUI(); // Object to manage user interactions;
															// Feel free to change the name

		/****************************************************************************************************
		 * Instantiate any DAOs you will be using here
		 ***************************************************************************************************/

		JdbcCampgroundDAO campgroundDAO = new JdbcCampgroundDAO(dataSource);
		JdbcReservationDAO reservationDAO = new JdbcReservationDAO(dataSource);
		JdbcParkDAO parkDAO = new JdbcParkDAO(dataSource);
		

		/****************************************************************************************************
		 * Your application programming logic goes here
		 ***************************************************************************************************/

		ArrayList<Park> parkList = new ArrayList<Park>();
		parkList = parkDAO.getAllParks();
		userInterface.welcomeBanner();
		while (true) { // Surrounds the entire 
			Park thePark = userInterface.displayParks(parkList);
			if (thePark == null) {
				userInterface.exitMessage();
				System.exit(0);
			} else {
				userInterface.displayParkInfo(thePark);
			}
			
			boolean shouldLoop = true; // Loop control variable
			
			while (shouldLoop) { // Loop while loop control variable is true

				String choice = userInterface.selectParkCommand(PARK_MENU_OPTIONS);

				List<Campground> displayCampgroundList = campgroundDAO.campgroundByParkId(thePark.getParkId());

				switch (choice) {
				case "View Campgrounds": {
					userInterface.displayCampgrounds(displayCampgroundList);
					break;
				}

				case "Search For Reservation": {

					String userAnswer = userInterface.askForCampName();
					int theCampgroundId = campgroundDAO.getCampgroundIdFromName(userAnswer);
					LocalDate theFromDate = LocalDate.parse(userInterface.askForFromDate());
					LocalDate theToDate = LocalDate.parse(userInterface.askForToDate());
					boolean isOpen = campgroundDAO.checkSeason(theCampgroundId, theFromDate, theToDate);
					if (isOpen) {
						if (campgroundDAO.getAvailableCampSites(theCampgroundId, theFromDate, theToDate) == null) {
							userInterface.chooseAnotherDate();
							break;
						} else {
							userInterface.displayTheAvailableSites(
									campgroundDAO.getAvailableCampSites(theCampgroundId, theFromDate, theToDate),
									campgroundDAO.calculateFees(
											campgroundDAO.getDailyFeeByCampgroundId(theCampgroundId), theFromDate,
											theToDate));
						}

					} else {
						userInterface.parkClosedStatement();
						break;
					}

					String userSite = userInterface.askWhichSiteToReserve();
					int userSiteInt = Integer.parseInt(userSite);
					String userReservationName = userInterface.askForReservationName();
					Reservation theReservation = reservationDAO.createReservation(userSiteInt, userReservationName,
							theFromDate, theToDate);
					reservationDAO.saveReservation(theReservation);
					userInterface.thankYou(theReservation);
					break;
				}

				case "Return To Previous Screen": {

					shouldLoop = false;
					break;

				}

				}

			}
		}

	}

}
