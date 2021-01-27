package com.bank.Menus;

import java.util.Random;

public class BankStart {
	

	
	public static void main(String args[]) {
		

		StartMenu sm = new StartMenu();
		sm.displayOptions();
		
	}
	public static int generateRandomToken() {
		Random rnd = new Random();
		return rnd.nextInt(999999999);
	}
	
}
