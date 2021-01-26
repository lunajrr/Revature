package com.bank.account;

import java.util.*;

import com.bank.DAO.CustomerDAO;

public class Customer{

	
	private int accId;
	private List<Account> accList;
	
	private CustomerDAO cus;

	
	public Customer() {
		super();
		this.accId = -1;
		this.accList = new ArrayList<Account>();
		cus = new CustomerDAO();
		getAllMyAccounts();
	}
	//getter
	public List<Account> getAccList(){
		return this.accList;
	}
	
	
	//Purpose: To log in with user and password
	//Return: Boolean depending on successful login or not
	public boolean logIn(String user, String pass) {
		accId = cus.logIn(user, pass);
		if(accId== -1)
			return false;
		else
			return true;
		
	}

	//Purpose: To call the selected account withdraw
	//Return: String telling of success or not
	public String withdraw(int index, double amount) {
		return accList.get(index).withdraw(amount, cus);
	}
	
	//Purpose: To call the selected account deposit
	//Return: String telling of success or not
	public String deposit(int index, double amount) {
		return accList.get(index).deposit(amount, cus);
	}
	
	//Purpose: To get all accounts associated with this Customer (account ID: accId)
	//Return: A list of accounts
	public List<Account> getAllMyAccounts() {
			this.accList = cus.viewMyAccounts(accId);
		return this.accList;
		
	}

	//Purpose: To start an transfer with this account
	//Return: True/False depending on execution, Or fail if the amount is greater than the balance
	public boolean startTransfer(int index, String accNumber, double amount) {
		Account acc = accList.get(index);
		
		if(amount> acc.getBalance())
			return false;
		
		return acc.startTransfer(cus, accNumber, amount);
		
		
		
	}

	//Purpose: To get Pending transfers associated with the selected account
	//Return: A list of transfers
	public ArrayList<Transfers>getpending(int index) {
		// TODO Auto-generated method stub
		return accList.get(index).getPendingTransfer(cus);
		
	}
	
	//Purpose: Create a new account associated with this accId(Account id)
	//Return: True/False depending on if code executes
	public boolean createNewAccount(double balance, String type) {
		Account acc = new Account(accId, balance, type );
		if(cus.newAccount(acc)) {
			accList.add(acc);
			return true;
		}
		else return false;
		
	}

	//Purpose: Create a new Customer Account
	//Return: True/False if successful
	public boolean createNewUser(String email, String password, String first, String last) {
		
		return cus.newUser(email, password, first, last);
	}
}
