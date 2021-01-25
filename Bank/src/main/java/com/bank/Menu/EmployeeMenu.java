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
		switch(in.nextLine()) {
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
			exit =true;
			break;
		case "Q":
			exit = true;
			quit();
			break;
		default:
			System.out.println("Invalid Entry");

		}
		}
		
	}

	private void viewAllAccounts() {
		// TODO Auto-generated method stub
		ArrayList<Account> accList = new ArrayList<Account>();
		accList.addAll(em.getAllAccounts());
		System.out.println(accList);
	}

	private void viewAllTransactions() {
		// TODO Auto-generated method stub
		ArrayList<Transaction> tranList = em.getAllTransactions();
		
		for(int i = 0; i < tranList.size(); i++)
			System.out.println(tranList.get(i));
		
		
	}

	private void viewCustomerAccounts() {
		System.out.println();
		System.out.println("Type in Customer's Email");
		String email = in.nextLine();
		ArrayList<Account> accs = em.searchViaEmail(email);
		for(int i = 0;i < accs.size(); i++) {
			System.out.println(accs.get(i));
		}
		
		
		
		
		
		
	}

	private void viewAllPendingAccounts() {
		// TODO Auto-generated method stub
		ArrayList<Account> accList = em.getAllPendingAccounts();
		for(int i = 0; i<accList.size(); i++ )
			System.out.println(accList.get(i));
	}
	
	private void quit() {
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

	@Override
	public void checkOptions(String input) {
		// TODO Auto-generated method stub
		
		
		
	}

}
