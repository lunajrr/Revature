package com.bank.account;

import java.util.ArrayList;

import com.bank.Connect.EmployeeConnect;
import com.bank.Menus.BankStart;

public class Employee {
	private int accId;
	private EmployeeConnect eDAO;
	private ArrayList<Account> accList;
	private ArrayList<Account> cus;
	
	public Employee() {
		this.eDAO = new EmployeeConnect();
		BankStart.LOGGER.info("Inside Constructor of Employee Constructor");
	}

	//Purpose: Get all accounts in the database
	//Return: A list of accounts
	public ArrayList<Account> getAllAccounts() {
		this.accList =eDAO.getAllAccounts();
		return accList;
	}

	//Purpose: To log in
	//Return: True if the user/pass is valid and is an employee; False if it fails either case
	public boolean logIn(String user, String pass) {
		accId = eDAO.logIn(user, pass);
		if(accId== -1) 
			return false;
		
		return true;
	}
	
	//Purpose: To get all accounts that have the pending state in the database
	//Return: A list on  f pending accounts
	public ArrayList<Account> getAllPendingAccounts() {
		// TODO Auto-generated method stub
		ArrayList<Account> accList = getAllAccounts();;
		ArrayList<Account> pendingAcc = new ArrayList<Account>();
		for(int i = 0; i<accList.size(); i++) {
			if(accList.get(i).getState().equalsIgnoreCase("P"))
				pendingAcc.add(accList.get(i));
		}
		return pendingAcc;
	}
	 
	//Purpose: To allow the employee to decide to approve or deny an account
	//Return: True if the query executes; else false
	public boolean decideAccount(int index,String decision, ArrayList<Account> pendingList) {
		
		if(decision.equals("A") || decision.equals("D"))
			if(index >= 0 && index< pendingList.size()) 
				return eDAO.decideAccount(pendingList.get(index), decision);
		
			return false;
			
	}

	//Purpose: to get all transactions in the database
	//Return: A list of transactions
	public ArrayList<Transaction> getAllTransactions() {

		return eDAO.getAllTransactions();
	}

	//Purpose: To get all the accounts associated with an email
	//Return: A list of accounts
	public ArrayList<Account> searchViaEmail(String email) {
		this.cus = eDAO.searchUserViaEmail(email);
		return this.cus;
}
	
	//Purpose: close resources
	public void closeResources() {
		eDAO.closeResources();
	}
	}

