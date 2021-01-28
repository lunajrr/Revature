package com.bank.Menus;

import java.util.*;

import com.bank.account.Account;
import com.bank.account.Customer;
import com.bank.account.Transfers;



public class CustomerMenu implements MenuInterface{
	private Customer cus;
	private boolean exit;
	private Scanner in;
	
	public CustomerMenu(Customer cus, Scanner in) {
		super();
		this.cus = cus;
		this.in = in;
		exit = false;		
	}
	
	//Purpose: To display options
	//Example: View all accounts, create a new account, go back to main menu, quit
	public void displayOptions() {
		String input;
		
		while(!exit) {	
			System.out.println();
			System.out.println("To view all your accounts info and select an option: Type 1");
			System.out.println("To create an new Account: Type 2");
			System.out.println("To go back: Type B");
			System.out.println("To Quit: Type Q");
			System.out.println("For a list of Commands: Type H");
			input = in.nextLine().toUpperCase();
			checkOptions(input);
			}
		}
			
	//Purpose: To check based on user input
	public void checkOptions(String input) {
		input=input.toUpperCase();
		switch(input) {
		case "1":
			accountOptions();
			break;
		case "2":
			createNewAccount();
			break;
		case "B":
			cus.closeResources();
			exit = true;
			break;
		case "H":
			break;
		case "Q":
			cus.closeResources();
			exit = true;
			exit();
			break;	
		default:
			System.out.println("Error: Incorrect Option Choice");
		}
	}

	//Purpose: To exit the program gracefully
	public void exit() {
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
	
	//Purpose: To display the options after viewing the accounts
	private void accountOptions() {
		if(viewAccounts() == -1) 
			return;

		String input = "";
		System.out.println("To make a deposit: type 1");
		System.out.println("To make a withdraw: type 2");
		System.out.println("To attempt a transfer: type 3");
		System.out.println("To accept a transfer: type 4");
		System.out.println("To go back: type B");
		System.out.println("To Quit: type Q");
		System.out.println("Choose an option");
		input = in.nextLine().toUpperCase();
		switch(input) {
		case "1":
			makeADeposit(displayAccountWithNumberOptions());
			break;
		case "2":
			makeAWithdraw(displayAccountWithNumberOptions());
			break;
		case "3":
			postTransfer(displayAccountWithNumberOptions());
			break;
		case "4":
			acceptTransfer(displayAccountWithNumberOptions());
		case "B": 
			break;
		case"Q":
			exit();
			break;
		case "H":
			accountOptions();
			break;
		default:
			System.out.println("Invalid Input");
			if(shallContinue());
				accountOptions();
			
		}
		
	}
	
	//Purpose: To accept Transfers associated with an account
	private void acceptTransfer(int index) {
		// TODO Auto-generated method stub
		if(index == -1)
			return;
		int option = -1;
		ArrayList<Transfers> tranList = cus.getpending(index);

			do {
				if(index >= 0 && tranList.size() != 0) {
				for(int i = 0; i<tranList.size(); i++) {
					System.out.println("Option: " +i + "  "+tranList.get(i));
				}}
				else {
					System.out.println("No pending Transfers");
					return;
				}
				System.out.println("Choose an option to approve/deny");

				
				try { option = in.nextInt();
					if(option < 0 || option > tranList.size()-1) {
						System.out.println("Invalid entry");
						in.nextLine();
						if(!shallContinue())
							return;
					}
				}
				catch(Exception e) {
					System.out.println("Invalid entry");
					if(!shallContinue())
						return;
				}
			}while(option <0 || option > tranList.size()-1 );
			approveDeny(tranList, option);
	}
				
	//Purpose: To allow the customer to approve or deny an transfer
	public void approveDeny(ArrayList<Transfers> tranList, int option) {
			in.nextLine();
			boolean loop= true;
			String decision = "";
			while(loop) {
				System.out.println();
				System.out.println("To Approve: Type A");
				System.out.println("To Deny: Type D");
				decision = in.nextLine().toUpperCase();
				if(decision.equals("A") || decision.equals("D")) {
					loop = false;}
				else{
					System.out.println("Invalid Input");
					if(!shallContinue())
						return;
				}
			}
			
			if(cus.approveTransfer(tranList, option, decision))
					System.out.println("Transfer updated");
			else
					System.out.println("Transfer update failed");
			System.out.println();
		}
		
	//To allow the user to post a transfer
	private void postTransfer(int index) {
		ArrayList<Account> accList = cus.getAllValidAccounts();
		
		if(accList.size()== 0 ||index == -1)
			return;
		
		if(index >= 0) {
			double amount;
			String accNumber;
			System.out.println("Enter the Account Number you want to transfer to");
			accNumber = in.nextLine();
			do {
			System.out.println("Enter amount to Transfer");
			
			amount = tryParse(in.nextLine());
			if(amount == -1)
				if(shallContinue())
					postTransfer(index);
				else
					return;
			
			if(amount<=0 )
				System.out.println("Invalid amount: amount can't be 0 or less");
			if(accList.get(index).getBalance()<amount)
				System.out.println("Invalid amount: balance low");
			} while(amount<0 || accList.get(index).getBalance()<amount);
			

			
			System.out.println(cus.startTransfer(index, accNumber, amount));
			
		}}

	//Purpose: to allow the user to make a deposit
	private void makeADeposit(int index) {
		if(cus.getAllMyAccounts().get(0) == null ||index == -1)
			return;
		
		if(index >= 0) {
			double amount;
			System.out.println("Enter amount to deposit");
			
			amount = tryParse(in.nextLine());	
			if(amount == -1)
				if(shallContinue())
					makeADeposit(index);
				else
					return;
			
			if(amount > 0 )
			System.out.println(cus.deposit(index, amount));
			else if(amount<0)
				System.out.println("Invalid amount: Needs to be postive");
			
		}}
	
	//Purpose: to allow the user to withdraw money
	private void makeAWithdraw(int index) {
		//testing
		if(cus.getAllMyAccounts().get(0) == null ||index == -1)
			return;
		
		
		if(index >= 0) {
			double amount;
			System.out.println("Enter amount to withdraw");
		
			//getting the values
			amount = tryParse(in.nextLine());
			
			if(amount == -1)
				if(shallContinue())
					makeAWithdraw(index);
				else
					return;
				
			if(amount > 0 && cus.getAllValidAccounts().get(index).getBalance() > amount)
				System.out.println(cus.withdraw(index, amount));
				else if(amount<0)
					System.out.println("Invalid amount: Needs to be postive");
				else
					System.out.println("Invalid amount: Balance is too small");
		}
		
	}
	
	//Purpose: to view all your accounts with option numbers
	private int displayAccountWithNumberOptions() {
		List<Account> accList = cus.getAllValidAccounts();
		int counter = -1;
		int index;
		Account acc;
		
		System.out.println();
		//Checks to see if there is any valid accounts
		if(accList.size() == 0) {
		System.out.println("No valid accounts");
			return -1;
		}
		
		//Display accounts with options
		
		for(int i = 0; i<accList.size(); i++) {
			acc = accList.get(i);
			if(!acc.getState().equalsIgnoreCase("P")) {
				counter++;
				System.out.printf("Option: %d Account Number: %s Balance: %.02f Type: %s \n", i, acc.getAccNumber(), acc.getBalance(), acc.getAccType());
		}}
		
		System.out.println("Choose an account");
		
		//Get the input from the user
		index = (int)tryParse(in.nextLine());
		if(!(index < 0) && index < accList.size()  && counter >= index)
			return index;
		
		//input problem; shall continue?
		System.out.println("Invalid Account Choice");
		if(shallContinue())
			return displayAccountWithNumberOptions();
		else
			return -1;
		
	}
	
	//Purpose: to view all your accounts without option numbers
	private int viewAccounts() {
		System.out.println();
		List<Account> accList = cus.getAllMyAccounts();
		if(accList.size()==0) {
			System.out.println("No valid accounts");
			return -1;
		}
		System.out.println();
		for(int i = 0; i<accList.size(); i++) 
			System.out.println(accList.get(i));
		return 1;
	}
	
	//Purpose: To all the user to create an new pending account
	private void createNewAccount() {
		String accType;
		double balance;
		
		//Getting Account Type (recursive restart);
		System.out.println("Enter what kind of account:");
		System.out.println("C for Checking; S for Savings");	
		accType = in.nextLine().toUpperCase();
		if(accType.equals("C") || accType.equals("S")) 
			System.out.println();
			else {
				System.out.println("Invalid input");
				if(shallContinue()) {
					createNewAccount();
					return;}
				else
					return;			
		}
		
		//Getting Starting balance
		while(true) {
		System.out.println("Enter Starting balance");
		balance = tryParse(in.nextLine());
		
		if(balance<0) {
			if(balance==-1) 
				System.out.println("Invalid input: Expected number");
			else
				System.out.println("Invalid starting balance");
			if(!shallContinue())
				return;
		}
		else
			break;	
		}
		
		
		if(cus.createNewAccount(balance, accType))
			System.out.println("Successful request");
		else 
			System.out.println("Failed request");
		
	}
	
	
	
	//To try to parse the input into a string
	private double tryParse(String number) {
		try {
			return Double.parseDouble(number);
		}
		catch(NumberFormatException e) {
			return -1;
		}
	}


	//Purpose: To continue program when an issue has arrived
	public  boolean shallContinue() {
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
	
	
