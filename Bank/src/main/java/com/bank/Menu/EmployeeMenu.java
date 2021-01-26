package com.bank.Menu;

import java.util.ArrayList;
import java.util.Scanner;

import com.bank.account.Account;
import com.bank.account.Employee;
import com.bank.account.Transaction;

public class EmployeeMenu implements MenuInterface{
	Employee em;
	Scanner in;
	
	EmployeeMenu(Employee em, Scanner in){
		this.em = em;
		this.in = in;
	}
	
	@Override
	public void displayOptions() {
		boolean exit = false;
		while(!exit) {
		System.out.println(" ");
		System.out.println("Welcome! What would you like to do?");
		System.out.println("To view Pending Accounts: Press 1");
		System.out.println("To view a customer's account: Press 2");
		System.out.println("To view all Transactions: Press 3");
		System.out.println("To view all Accounts: Press 4");
		System.out.println("To go back: press B");
		System.out.println("To Quit: Press Q");
		switch(in.nextLine().toUpperCase()) {
		case "1":
			viewAllPendingAccounts();
			break;
		case"2":
			viewCustomerAccounts();
			break;
		case "3":
			viewAllTransactions();
			break;
		case "4":
			viewAllAccounts();
			break;
		case "B":
			em.closeResources();
			exit =true;
			break;
		case "Q":
			exit = true;
			exit();
			break;
		default:
			System.out.println("Invalid Entry");

		}
		}
		
	}

	private void viewAllAccounts() {
		// TODO Auto-generated method stub
		ArrayList<Account> accList = em.getAllAccounts();
		System.out.println("List of All Accounts");
		if(accList.size()!=0) {
			for(int i = 0; i< accList.size(); i++) {
				System.out.println(accList.get(i));
			}
		}
	}

	private void viewAllTransactions() {
		// TODO Auto-generated method stub
		ArrayList<Transaction> tranList = em.getAllTransactions();
		
		for(int i = 0; i <tranList.size(); i++)
			System.out.println(tranList.get(i));
		
		
	}

	private void viewCustomerAccounts() {
		System.out.println();
		System.out.println("Type in Customer's Email");
		String email = in.nextLine();
		ArrayList<Account> accs = em.searchViaEmail(email);
		if(accs.size() != 0) {
		for(int i = 0;i < accs.size(); i++) {
			System.out.println(accs.get(i));
		}}
		else {
			System.out.println("No accounts");
		}	
		
	}

	private void viewAllPendingAccounts() {
		// TODO Auto-generated method stub
		ArrayList<Account> accList = em.getAllPendingAccounts();
		if(accList.size() != 0) {
		for(int i = 0; i<accList.size(); i++ )
			System.out.println(accList.get(i));}
		else {
			System.out.println("No pending Accounts");
			return;
		}
		System.out.println("Would you like to Approve/Deny an Account: Y/N");
		if(in.nextLine().equalsIgnoreCase("Y"));
			decideAccount();
	}
	
	private void decideAccount() {
		ArrayList<Account> accList; 
		boolean going= true;
		while(going) {
			accList =  em.getAllPendingAccounts();
		try {
		for(int i = 0; i<accList.size(); i++ )
			System.out.println("Option " + i + "  "+accList.get(i));
		
		System.out.println("Select an option");
		int index = in.nextInt();
		in.nextLine();
		System.out.println("Type A to Approve; Type D to Deny");
		String decision = in.nextLine().toUpperCase();
		if(decision.equals("A")||decision.equals("D")) {
			em.decideAccount(index, decision, accList);
			System.out.println("Account Updated");
			System.out.println();
			going = shallContinue();
		}
		else {
			System.out.println("Incorrect Input: Expected Input: A or D");
			System.out.println();
			going = shallContinue();
		}}
		catch(Exception e) {
			System.out.println("Oops, an exception happened at deciding to appove/deny an account.");
			System.out.println();
			going = shallContinue();
		}
		}
		}
	
		
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
		
	public void exit() {
		em.closeResources();
		System.out.println("Closing in 5 seconds");
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


}
