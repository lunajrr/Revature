package com.bank.Menu;

import java.util.*;

import com.bank.account.Customer;
import com.bank.account.Employee;

public class StartMenu implements MenuInterface {
	Scanner in = new Scanner(System.in);
	boolean exit = false;
	
	
	//display default options
	public void displayOptions() {
		
		String input;
		
		System.out.println("Welcome to Juan's Bank!");
		while(!exit) {
		System.out.println();
		System.out.println("What would you like to do?");
		System.out.println("To create an Account: Type 1");
		System.out.println("To log in as Customer: Type 2");
		System.out.println("To log in as Employee: Type 3");
		System.out.println("To Exit: Type Q");
		System.out.println("For a list of Commands: Type H");
		
		input = in.nextLine();
		checkOptions(input);
		}
	}
	

	//check default options
	public void checkOptions(String input) {
		// TODO Auto-generated method stub
		if(input.equalsIgnoreCase("1"))
			createAccount();
		else if(input.equalsIgnoreCase("2"))
			customerlogIn();
		else if(input.equalsIgnoreCase("3"))
			employeelogIn();
		else if(input.equalsIgnoreCase("Q"))
			exit();
		else if(input.equalsIgnoreCase("H"))
			listCommands();
		else {
			System.out.println("Error: Invalid Command");
			System.out.println("Type H for Valid Commands");
		}
	}
		
	//exit the app(works as planned)
	private void exit() {
		System.out.println("Closing in 5 seconds");
		exit = true;
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
	// List the help commands(works as planned)
	private void listCommands() {
		System.out.println("To create an Account: Type 1");
		System.out.println("To log in as Customer: Type 2");
		System.out.println("To log in as Employee: Type 3");
		System.out.println("To Exit: Type Q");
		System.out.println("For a list of Commands: Type H");
	}
	//#############create Account
	private void createAccount() {
		String firstName;
		String lastName;
		String email;
		String password;
		System.out.println("Enter your first Name: ");
		firstName = in.nextLine();
		System.out.println("Enter your last name: ");
		lastName = in.nextLine();
		System.out.println("Enter your email : ");
		email = in.nextLine();
		System.out.println("Enter your password: ");
		password = in.nextLine();
		
		Customer cus = new Customer();
		if(cus.createNewUser(email, password, firstName, lastName))
			System.out.println("New user Created");
		else
			System.out.println("Failed to create a user");
		System.out.println();
		
	}
	
	//Employee login(still needs to check to see if it is an employee)
	private void employeelogIn() {
		Employee emp = new Employee();
		Scanner in = new Scanner(System.in);
		System.out.println("");
		String username;
		String password;
		boolean logInSuccesful = false;
		
		while(!logInSuccesful) {
		System.out.println("Enter your username: ");
		username = in.nextLine();
		System.out.println("Enter your password: ");
		password = in.nextLine();
		logInSuccesful = emp.logIn(username,  password);
		if(!logInSuccesful) {
			System.out.println("Incorrect Password");
			System.out.println("Type Q to exit");
			System.out.println("Type any other key to try again");
			if(in.nextLine().equalsIgnoreCase("Q"))
				break;
		}	
		}
		
		if(logInSuccesful) {
			EmployeeMenu em = new EmployeeMenu(emp,in);
			em.displayOptions();
		}
		
	}
		
	
	
	//get logIn info(works as planned)
	private void customerlogIn() {
		
		Customer cus = new Customer();
		Scanner in = new Scanner(System.in);
		System.out.println("");
		String username;
		String password;
		boolean logInSuccesful = false;
		
		while(!logInSuccesful) {
		System.out.println("Enter your user name: ");
		username = in.nextLine();
		System.out.println("Enter your password: ");
		password = in.nextLine();
		logInSuccesful = cus.logIn(username,  password);
		if(!logInSuccesful) {
			System.out.println("Incorrect Password");
			System.out.println("Type Q to exit");
			System.out.println("Type any other key to try again");
			if(in.nextLine().equalsIgnoreCase("Q"))
				break;
		}	
		}
		
		if(logInSuccesful) {
			CustomerMenu cm = new CustomerMenu(cus,in);
			cm.displayOptions();
		}
		
	}

}
