package com.bank.Menus;

import java.util.Random;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BankStart {
	public static final int instanceID =  generateRandomToken();
	public static final Logger LOGGER = LogManager.getLogger(BankStart.class.getName());
	
	public static void main(String args[]) {
		LOGGER.info("INSTANCE ID: " + instanceID + " || BankStart.main(): Started the program") ;
		StartMenu sm = new StartMenu();
		sm.displayOptions();
		
	}
	public static int generateRandomToken() {
		Random rnd = new Random();
		return rnd.nextInt(999999999);
	}
	
}
