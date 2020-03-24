package com.techelevator.DiceRoller;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class DiceRollerProgram {

	private static final String DICE_MENU_OPTION_D4 = "D4";
	private static final String DICE_MENU_OPTION_D6 = "D6";
	private static final String DICE_MENU_OPTION_D8 = "D8";
	private static final String DICE_MENU_OPTION_D10 = "D10";
	private static final String DICE_MENU_OPTION_D12 = "D12";
	private static final String DICE_MENU_OPTION_D20 = "D20";
	private static final String DICE_MENU_OPTION_D100 = "D100";
	private static final String DICE_MENU_OPTION_EXIT = "Exit Dice Roller";
	private static final String[] DICE_MENU_OPTIONS = { DICE_MENU_OPTION_D4, DICE_MENU_OPTION_D6, DICE_MENU_OPTION_D8
			                                          , DICE_MENU_OPTION_D10, DICE_MENU_OPTION_D12, DICE_MENU_OPTION_D20
			                                          , DICE_MENU_OPTION_D100, DICE_MENU_OPTION_EXIT };

	private Menu diceMenu;

	private Dice theDice;

	public DiceRollerProgram(Menu menu) throws FileNotFoundException { // Constructor - user will pas a menu for this
																		// class to use
		this.diceMenu = menu; // Make the Menu the user object passed, our Menu
		this.theDice = new Dice();
	}

	public void run() {

		boolean shouldProcess = true; // Loop control variable

		while (shouldProcess) { // Loop until user indicates they want to exit

			String choice = (String) diceMenu.getChoiceFromOptions(DICE_MENU_OPTIONS); // Display menu and get choice

			switch (choice) {

			case DICE_MENU_OPTION_D4:
				System.out.println("You rolled: " + theDice.rollD4());
				break;

			case DICE_MENU_OPTION_D6:
				System.out.println("You rolled: " + theDice.rollD6());
				break;

			case DICE_MENU_OPTION_D8:
				System.out.println("You rolled: " + theDice.rollD8());
				break;

			case DICE_MENU_OPTION_D10:
				System.out.println("You rolled: " + theDice.rollD10());
				break;

			case DICE_MENU_OPTION_D12:
				System.out.println("You rolled: " + theDice.rollD12());
				break;

			case DICE_MENU_OPTION_D20:
				System.out.println("You rolled: " + theDice.rollD20());
				break;

			case DICE_MENU_OPTION_D100:
				System.out.println("You rolled: " + theDice.rollD100());
				break;

			case DICE_MENU_OPTION_EXIT:
				System.out.println("Thank you! Goodbye!");
				shouldProcess = false;
				break;
			}
		}
		return;
	}

}
