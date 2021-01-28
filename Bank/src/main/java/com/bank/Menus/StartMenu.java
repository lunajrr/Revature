package com.bank.Menus;

import java.util.*;

import com.bank.Connect.AccountConnect;
import com.bank.account.Customer;
import com.bank.account.Employee;

public class StartMenu implements MenuInterface {
	Scanner in = new Scanner(System.in);
	boolean going = true;
	
	//Purpose: To display all the default options
	//EX: create new account, log in, exit, help
	public void displayOptions() {
		
		String input;	
		System.out.println("Welcome to The First Bank of Juan!");
		while(going) {
		System.out.println();
		System.out.println("What would you like to do?");
		listCommands();
		input = in.nextLine();
		checkOptions(input);
		}
	}
	
	//Purpose: To validate commands from the default menu
	private void checkOptions(String input) {
		// TODO Auto-generated method stub
		if(input.equalsIgnoreCase("1"))
			createAccount();
		else if(input.equalsIgnoreCase("2"))
			logIn();
		else if(input.equalsIgnoreCase("Q"))
			exit();
		else if(input.equalsIgnoreCase("H"))
			listCommands();
		else {
			System.out.println("Error: Invalid Command");
			System.out.println("Type H for Valid Commands");
		}
	}
		
	//Purpose: To Exit the Program.
	public void exit() {
		System.out.println("Closing in 5 seconds");
		in.close();
		going = false;
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
		System.exit(0);
		}
	}
	
	// Purpose:To List Commands to the command prompt
	private void listCommands() {
		System.out.println("To Create an Account: Type 1");
		System.out.println("To Log in: Type 2");
		System.out.println("To QUIT: Type Q");
		System.out.println("For HELP: Type H");
	}
	
	//Purpose: to allow the user to create an account
	private void createAccount() {
		String firstName;
		String lastName;
		String email;
		String password;
		boolean whileLoop = true;
		while(whileLoop) {
		System.out.println("Enter your first Name: ");
		firstName = in.nextLine();
		System.out.println("Enter your last name: ");
		lastName = in.nextLine();
		System.out.println("Enter your email: ");
		email = in.nextLine().toUpperCase();
		System.out.println("Enter your password: ");
		password = in.nextLine();
		
		Customer cus = new Customer();
		if(cus.createNewUser(email, password, firstName, lastName)) {
			System.out.println("New User Created");
			whileLoop = false;}
		else {
			System.out.println("Failed to Create a User");
			System.out.println();
			whileLoop = shallContinue();
		}
		System.out.println();
		}
	}
	
	//factory design pattern
	//Purpose: To log in with email/password: it decides if the user is a customer or employee based on email
	private void logIn() {
		
		AccountConnect ac = new AccountConnect();
		System.out.println("");
		String username;
		String password;
		boolean whileLoop = true;
		
		while(whileLoop) {
		System.out.println("Enter your email: ");
		username = in.nextLine().toUpperCase();
		System.out.println("Enter your password: ");
		password = in.nextLine();
		if(ac.checkEmployeeLogIn(username)) {	
			if(employeeLogIn(username, password)) {
				return;}}
		else if(customerLogIn(username, password)) 
			return;
		}
		ac.close();
	}
	
	//Employee login(still needs to check to see if it is an employee)
	private boolean employeeLogIn(String user, String password) {
		Employee emp = new Employee();

		if(!emp.logIn(user, password)) {
			emp = null;
			System.out.println("Couldn't log in as Employee");
			return false;}
		else
		{
			EmployeeMenu empM = new EmployeeMenu(emp, in);
			empM.displayOptions();
			return true;
		}
		
	}
	//get logIn info(works as planned)
	private boolean customerLogIn(String user, String password) {
		
		Customer cus = new Customer();
		
		if(!cus.logIn(user, password)){
			cus = null;
			System.out.println("Couldn't log in as Customer");
			return false;}
		else
		{
			CustomerMenu cusM = new CustomerMenu(cus, in);
			cusM.displayOptions();
			return true;
		}
		

		
	}

	//Purpose: to decide if the user wants to keep trying after something happened
	public boolean shallContinue() {
		boolean loop = true;
		while(loop) {
		System.out.println("Should we continue: Y/N");
		switch(in.nextLine().toUpperCase()) {
		case "Y":
			loop = false;
			return true;
		case "N":
			loop = false;
			return false;
		default:
		System.out.println("Invalid Entry");
		}	
	}
		return false;
		}
}
