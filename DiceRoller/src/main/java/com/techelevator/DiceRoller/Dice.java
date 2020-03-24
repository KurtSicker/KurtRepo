package com.techelevator.DiceRoller;

import java.util.Random;

public class Dice {

	public Dice() {

	}

	public int rollD4() {
		Random oneFour = new Random();
		int theRoll = oneFour.nextInt(4) + 1;
		return theRoll;

	}

	public int rollD6() {
		Random oneSix = new Random();
		int theRoll = oneSix.nextInt(6) + 1;
		return theRoll;

	}

	public int rollD8() {
		Random oneEight = new Random();
		int theRoll = oneEight.nextInt(8) + 1;
		return theRoll;

	}

	public int rollD10() {
		Random oneTen = new Random();
		int theRoll = oneTen.nextInt(10) + 1;
		return theRoll;

	}

	public int rollD12() {
		Random oneTwelve = new Random();
		int theRoll = oneTwelve.nextInt(12) + 1;
		return theRoll;

	}

	public int rollD20() {
		Random oneTwenty = new Random();
		int theRoll = oneTwenty.nextInt(20) + 1;
		return theRoll;

	}

	public int rollD100() {
		Random one100 = new Random();
		int theRoll = one100.nextInt(100) + 1;
		return theRoll;

	}

}
