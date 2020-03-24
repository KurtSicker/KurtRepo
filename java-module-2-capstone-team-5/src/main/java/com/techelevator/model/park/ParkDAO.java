package com.techelevator.model.park;

import java.util.ArrayList;
import java.util.List;

public interface ParkDAO {

	/****************************************
	 * Get all Park Names from the database *
	 * @return all the Park Names as a List *
	 ***************************************/

	public ArrayList<Park> getAllParks();


	
	
	/***************************************************
	 * Get all the Park Info from database and display * 
	 * 				the info to user.     	   		   *
	 **************************************************/
	
	public Park getParkInfo(Long parkId);




}
