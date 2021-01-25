package com.bank.Menu;

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
			checkOptions(input);}}
		
	
	//Check options(works as intended)
	public void checkOptions(String input) {
		input=input.toUpperCase();
		switch( input) {
		case "1":
			viewAccounts();
			accountOptions();
			break;
		case "2":
			createNewAccount();
			break;
		case "B":
			exit = true;
			break;
		case "H":
			break;
		case "Q":
			exit = true;
			exit();
			break;	
		default:
			System.out.println("Error: Incorrect Option Choice");
		}
	}
	
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
	//options when you choose an account
	private void accountOptions() {
		String input = "";
		System.out.println("Choose an option");
		System.out.println("Make a deposit: type 1");
		System.out.println("Make a withdraw: type 2");
		System.out.println("To attempt a transfer: type 3");
		System.out.println("To accept a transfer: type 4");
		in.nextLine();
		input = in.nextLine();
		switch(input) {
		case "1":
			makeADeposit(displayAccountWithNumberOptions());
			break;
		case "2":
			makeAWithdraw(displayAccountWithNumberOptions());
		case "3":
			postTransfer(displayAccountWithNumberOptions());
		case "4":
			acceptTransfer(displayAccountWithNumberOptions());
		case"Q":
			break;
			
		}
		
	}
	
	private void acceptTransfer(int index) {
		// TODO Auto-generated method stub
		
		ArrayList<Transfers> tranList = cus.getpending(index);
		System.out.println(tranList);
		if(tranList != null)
			if(index >= 0) {
			for(int i = 0; i<tranList.size(); i++) {
				System.out.println("Option: " +i + "  "+tranList.get(i));
			}
		}
		
	}

	private void postTransfer(int index) {
		if(cus.getAllMyAccounts().get(0) == null)
			return;
		
		if(index >= 0) {
			double amount;
			String accNumber;
			System.out.println("Enter amount to Transfer");
			amount = in.nextDouble();
			in.nextLine();
			System.out.println("Enter the Account Number you want to transfer to");
			accNumber = in.nextLine();
			System.out.println(cus.startTransfer(index, accNumber, amount));
			
		}}

	
	private void makeADeposit(int index) {
		if(cus.getAllMyAccounts().get(0) == null)
			return;
		
		if(index >= 0) {
			double amount;
			System.out.println("Enter amount to deposit");
			amount = in.nextDouble();
			in.nextLine();
			System.out.println(cus.deposit(index, amount));
			
		}}
	
	private void makeAWithdraw(int index) {
		if(cus.getAllMyAccounts().get(0) == null)
			return;
		
		if(index >= 0) {
			double amount;
			System.out.println("Enter amount to withdraw");
			amount = in.nextDouble();
			in.nextLine();
			System.out.println(cus.withdraw(index, amount));
		}
		
	}
	
	
	private int displayAccountWithNumberOptions() {
		List<Account> accList = cus.getAllMyAccounts();
		int index;
		Account acc;
		System.out.println("Choose an account");
		for(int i = 0; i<accList.size(); i++) {
			acc = accList.get(i);
			if(!acc.getState().equalsIgnoreCase("P"))
				System.out.printf("Option: %d Account Number: %s Balance: %.02f Type: %s \n", i, acc.getAccNumber(), acc.getBalance(), acc.getAccType());
		}
		try {
			index = Integer.parseInt(in.nextLine());
			if(index <= accList.size())
				return index;
		}catch (Exception e) {
			System.out.println("Invalid Input");
		}
		return -1;
		
	}
	
	//view your accounts
	private void viewAccounts() {
		List<Account> accList = cus.getAllMyAccounts();
		System.out.println();
		for(int i = 0; i<accList.size(); i++) 
			System.out.println(accList.get(i));
		
	}
	//create a new account
	private void createNewAccount() {
		String accType;
		double balance;
		System.out.println("Enter what kind of account:");
		System.out.println("C for Checking; S for Savings");
		in.nextLine();
		accType = in.nextLine();
		System.out.println("Enter Starting balance");
		balance = in.nextDouble();
		in.nextLine();
		if(cus.createNewAccount(balance, accType))
			System.out.println("Successful request");
		else 
			System.out.println("Failed request");
		
	}
	
	
}
