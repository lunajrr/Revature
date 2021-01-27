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
	
	public void displayOptions() {
		String input = "";
		while(!exit) {
		System.out.println();
		System.out.println("To view all acount's info and select an option: Type 1");
		System.out.println("To create an new Account: Type 2");
		System.out.println("To go back: Type B");
		System.out.println("To Quit: Type Q");
		System.out.println("For a list of Commands: Type H");

			input = in.nextLine();
			checkOptions(input);}
		}
			
	//Check options(works as intended)
	public void checkOptions(String input) {
		input=input.toUpperCase();
		switch( input) {
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
	//options when you choose an account
	private void accountOptions() {
		if(viewAccounts() == -1) 
			return;

		String input = "";
		System.out.println("Choose an option");
		System.out.println("Make a deposit: type 1");
		System.out.println("Make a withdraw: type 2");
		System.out.println("To attempt a transfer: type 3");
		System.out.println("To accept a transfer: type 4");
		System.out.println("To go back: type B");
		System.out.println("To Quit: type Q");
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
	
	//TEST
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
		

	//TEST
	private void postTransfer(int index) {
		if(cus.getAllMyAccounts().get(0) == null ||index == -1)
			return;
		
		if(index >= 0) {
			double amount;
			String accNumber;
			do {
			System.out.println("Enter amount to Transfer");
			amount = in.nextDouble();
			in.nextLine();
			if(amount<0)
				System.out.println("Invalid amount");
			} while(amount<0);
			System.out.println("Enter the Account Number you want to transfer to");
			accNumber = in.nextLine();
			System.out.println(cus.startTransfer(index, accNumber, amount));
			
		}}

	
	private void makeADeposit(int index) {
		if(cus.getAllMyAccounts().get(0) == null ||index == -1)
			return;
		
		if(index >= 0) {
			double amount;
			System.out.println("Enter amount to deposit");
			amount = in.nextDouble();
			in.nextLine();
			if(amount > 0 )
			System.out.println(cus.deposit(index, amount));
			else if(amount<0)
				System.out.println("Invalid amount: Needs to be postive");
			
		}}
	
	private void makeAWithdraw(int index) {
		if(cus.getAllMyAccounts().get(0) == null ||index == -1)
			return;
		
		if(index >= 0) {
			double amount;
			System.out.println("Enter amount to withdraw");
			amount = in.nextDouble();
			in.nextLine();
			if(amount > 0 && cus.getAllValidAccounts().get(index).getBalance() > amount)
				System.out.println(cus.withdraw(index, amount));
				else if(amount<0)
					System.out.println("Invalid amount: Needs to be postive");
				else
					System.out.println("Invalid amount: Balance is too small");
		}
		
	}
	
	
	private int displayAccountWithNumberOptions() {
		List<Account> accList = cus.getAllValidAccounts();
		if(accList.size() == 0) {
		System.out.println("No valid accounts");
			return -1;
		}
		int counter = -1;
		int index;
		Account acc;
		System.out.println("Choose an account");
		for(int i = 0; i<accList.size(); i++) {
			acc = accList.get(i);
			if(!acc.getState().equalsIgnoreCase("P")) {
				counter++;
				System.out.printf("Option: %d Account Number: %s Balance: %.02f Type: %s \n", i, acc.getAccNumber(), acc.getBalance(), acc.getAccType());
		}}
		try {
			index = Integer.parseInt(in.nextLine());
			if(index < accList.size()  && counter >= index)
				return index;
		}catch (Exception e) {
			System.out.println("Invalid Input");
		}
		System.out.println("Invalid Account Choice");
		return -1;
		
	}
	
	//view your accounts
	private int viewAccounts() {
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
	
	//create a new account
	private void createNewAccount() {
		String accType;
		double balance;
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
			
		System.out.println("Enter Starting balance");
		balance = in.nextDouble();
		in.nextLine();
		
		if(cus.createNewAccount(balance, accType))
			System.out.println("Successful request");
		else 
			System.out.println("Failed request");
		
	}

	
	public  boolean shallContinue() {
		in.nextLine();
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
	
	
