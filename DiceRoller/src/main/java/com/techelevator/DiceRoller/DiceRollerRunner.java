package com.techelevator.DiceRoller;

import java.io.FileNotFoundException;



public class DiceRollerRunner {

	public static void main(String[] args) throws FileNotFoundException {
	
			System.out.println("Welcome to Kurt's Dice Roller!");
			System.out.println("Please choose what kind of die to roll.");
			Menu appMenu = new Menu(System.in, System.out);                // Instantiate a menu for Vending Machine CLI main program to use
			DiceRollerProgram rollerProgram = new DiceRollerProgram(appMenu); // Instantiate the Vending Machine CLI main program passing it the Menu object to use
			rollerProgram.run();                                              // Tell the Vending MachineCLI process to start running
		}
		

	}


