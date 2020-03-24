package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import com.techelevator.CampgroundApp;
import com.techelevator.model.campground.Campground;
import com.techelevator.model.park.Park;
import com.techelevator.model.reservation.Reservation;
import com.techelevator.model.site.SiteandCampground;

public class CampgroundUI {

	/*******************************************************************************************************
	 * This is the CampgroundUI class
	 * 
	 * All user interactions should be coded here
	 * 
	 * The application program will instantiate an object of this class and use the
	 * object to interact with the user.
	 * 
	 * And data required from the user for the application will be acquired by
	 * methods of this class and sent back to the user either as the return value
	 * from the method or in an object returned from the method.
	 * 
	 * Any messages the application programmer wishes to display to the user may be
	 * sent to methods of this class as variables or objects. Any messaging method
	 * may also have "canned" messages the user may request by a message id.
	 * 
	 *******************************************************************************************************/

	/*******************************************************************************************************
	 * Menu class object
	 * 
	 * Use this Menu object for ALL Menu choices presented to the user
	 * 
	 * This is the same Menu class discussed in module 1 and made available in the
	 * module 1 capstone
	 * 
	 * 
	 ******************************************************************************************************/

	Scanner keyboard = new Scanner(System.in);

	String campNameInput = "";
	String fromDateInput = "";
	String toDateInput = "";
	String reserveSiteInput = "";
	String nameReservationInput = "";

	Menu CampMenu = new Menu(System.in, System.out); // Define menu for keyboard input and screen output
	
	/*******************************************************************************************************
	 * Class member variables, objects, constants and methods should be coded here.
	 ******************************************************************************************************/
	
	
	public String selectParkCommand(String[] PARK_MENU_OPTIONS) {
		String choice = (String) CampMenu.getChoiceFromOptions(PARK_MENU_OPTIONS);
		return choice;
		
	}
	
	/*********************************
	 * Display all of the Park Names *
	 ********************************/
	
	public Park displayParks(ArrayList<Park> parkList) {
		String[] parkNameArray = new String[parkList.size() + 1];
		for (int i = 0; i < parkNameArray.length - 1; i++) {
			parkNameArray[i] = parkList.get(i).getName();
		}
		parkNameArray[parkList.size()] = "Quit";
		String theSelection = (String) CampMenu.getChoiceFromOptions(parkNameArray);
		Park selectedPark = findParkByName(parkList, theSelection);
		return selectedPark;

	}

	/******************************
	 * Find National Park by Name *
	 *****************************/
	
	public Park findParkByName(ArrayList<Park> parkList, String parkName) {

		for (int i = 0; i < parkList.size(); i++) {
			String name = parkList.get(i).getName();
			if (name.equals(parkName)) {
				return parkList.get(i);
			}

		}

		return null;

	}

	/******************************************
	 * Display all the Park Info for The User *
	 *****************************************/
	
	public void displayParkInfo(Park selectedPark) {
		System.out.println(selectedPark.getName() + " National Park");
		System.out.println("Location: " + selectedPark.getLocation());
		System.out.println("Established: " + selectedPark.getEstablishDate());
		System.out.println("Area: " + selectedPark.getArea() + " sqkm");
		System.out.println("Annual Visitors " + selectedPark.getVisitors());
		System.out.println(selectedPark.getDescription());

	}
	/**************************************************
	 * Display the Campgrounds WITHIN the park chosen *
	 *************************************************/
	
	public void displayCampgrounds(List<Campground> campgroundList) {
		for (int i = 0; i < campgroundList.size(); i++) {
			System.out.println("Name: " + campgroundList.get(i).getName() + " Open from month: "
					+ campgroundList.get(i).getOpenFrom() + " Closes on month: " + campgroundList.get(i).getOpenTo()
					+ " Daily Fee: $" + campgroundList.get(i).getDailyFee());

		}

	}

	
	/******************************************
	 * Ask User to Select a Campground by NAME*
	 *****************************************/
	
	
	public String askForCampName() {

		System.out.println(" Enter Name of Campground you would you like to stay at (Case Sensitive): ");
		campNameInput = keyboard.nextLine();

		return campNameInput;

	}

	/*****************************
	 * Ask User for Arrival Date *
	 ****************************/
	
	public String askForFromDate() {
		System.out.println("What is the arrival date?: YYYY-MM-DD ");
		fromDateInput = keyboard.nextLine();
		return fromDateInput;
	}
	
	/*******************************
	 * Ask User for Departure Date *
	 ******************************/

	public String askForToDate() {
		System.out.println("What is the departure date?: YYYY-MM-DD ");
		toDateInput = keyboard.nextLine();
		return toDateInput;
	}
	
	/*******************************************
	 * Display the TOP (5) AVAILABLE Campsites *
	 ******************************************/

	public void displayTheAvailableSites(List<SiteandCampground> availableSites, BigDecimal totalCost) {
		for (int i = 0; i < availableSites.size(); i++) {
			System.out.println("Site No.: " + availableSites.get(i).getSiteNumber() + " Max Occup.: "
					+ availableSites.get(i).getMaxOccupancy() + " Accessible?: " + availableSites.get(i).isAccessible()
					+ " Max RV Lenghth: " + availableSites.get(i).getMaxRvLength() + " Utility: "
					+ availableSites.get(i).isUtilities() + " Total Cost: $" + totalCost);
		}
	}

	/****************************************************************
	 * If Dates entered are out of the Camping Season, Inform User! *
	 ***************************************************************/
	
	public void parkClosedStatement() {
		System.out.println("The campground is closed during this timeframe");
	}
	
	
	/*****************************************************
	 * Thank You Statement after reservation is complete *
	 ****************************************************/

	public void thankYou(Reservation theReservation) {
		System.out.println("Thank you, the reservation number is " + theReservation.getReservationId());
	}

	/**************************************************
	 * Ask user which site they would like to reserve *
	 *************************************************/
	
	public String askWhichSiteToReserve() {
		System.out.println("Which site should be reserved?: ");
		reserveSiteInput = keyboard.nextLine();
		return reserveSiteInput;
	}

	/**********************************************
	 * Ask user for the Name of thier Reservation *
	 *********************************************/
	
	public String askForReservationName() {
		System.out.println("What name should the reservation be made under?: ");
		nameReservationInput = keyboard.nextLine();
		return nameReservationInput;

	}
	
	/****************
	 * Exit Message *
	 ***************/
	
	public void exitMessage() {
		System.out.println("Exiting Program, Goodbye.");
	
	}
	
	/**************************************************************
	 * If date is unavailable, Inform user to chose another date  *
	 *************************************************************/
	
	public void chooseAnotherDate() {
		System.out.println("No sites are available for the specified dates, please choose set of dates.");
	}
	
	/*****************
	 * Wecome Banner *
	 ****************/
	
	public void welcomeBanner() {
		System.out.println("Welcome to the Campground Reservation App!");
	}
}
