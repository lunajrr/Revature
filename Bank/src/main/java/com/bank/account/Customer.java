package com.bank.account;

import java.util.*;
import org.apache.logging.log4j.*;

import com.bank.Connect.CustomerConnect;

public class Customer{

	
	private int accId;
	private ArrayList<Account> accList;
	private ArrayList<Account> tempList;
	private CustomerConnect cus;
	private static final Logger LOGGER = LogManager.getLogger(Customer.class.getName());

	
	public Customer() {
		super();
		this.accId = -1;
		this.accList = new ArrayList<Account>();
		this.tempList = new ArrayList<Account>();
		cus = new CustomerConnect();
		getAllMyAccounts();
		LOGGER.info("Inside Constructor of Customer Constructor");
	}
	//getter
	public List<Account> getAccList(){
		return this.accList;
	}
	public void setAccId(int accId) {
		this.accId = accId;
	}
	
	public void closeResources() {
		cus.closeResources();
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
		return tempList.get(index).withdraw(amount, cus);
	}
	
	//Purpose: To call the selected account deposit
	//Return: String telling of success or not
	public String deposit(int index, double amount) {
		return tempList.get(index).deposit(amount, cus);
	}
	
	//Purpose: To get all accounts associated with this Customer (account ID: accId)
	//Return: A list of accounts
	public ArrayList<Account> getAllMyAccounts() {
			this.accList = cus.viewMyAccounts(accId);
		return this.accList;
		
	}

	//Purpose: To start an transfer with this account
	//Return: True/False depending on execution, Or fail if the amount is greater than the balance
	public String startTransfer(int index, String accNumber, double amount) {
		Account acc = tempList.get(index);
		
		if(amount> acc.getBalance())
			return "Invalid: balance too low";
		if(acc.startTransfer(cus, accNumber, amount))
		return "Transfer Initiaited";
		else
			return "Transfer failed";	
		
	}

	//Purpose: To get Pending transfers associated with the selected account
	//Return: A list of transfers
	public ArrayList<Transfers>getpending(int index) {
		// TODO Auto-generated method stub.
		
		ArrayList<Transfers> transList = accList.get(index).getPendingTransfer(cus);
		ArrayList<Transfers> tempList = new ArrayList<Transfers>();
		for(int i = 0; i< transList.size(); i++) {
			if(transList.get(i).getRecievingAcc().equalsIgnoreCase(accList.get(index).getAccNumber()) && transList.get(i).getState().equalsIgnoreCase("P")) { 
				
				tempList.add(transList.get(i));
				}}
		return tempList;
		
	}
	
	public boolean approveTransfer(ArrayList<Transfers> trans, int index, String decision) {
		if(!decision.equals(""))
			return cus.decideTransfer(trans.get(index), decision);
		else
			System.out.println("Error with decision");
		return false;
	}
	
	
	public ArrayList<Transfers>getAllAssociatedTransfers(int index){
		
		return cus.getTransfer(accList.get(index));
		
	}
	//Purpose: Create a new account associated with this accId(Account id)
	//Return: True/False depending on if code executes
	public boolean createNewAccount(double balance, String type) {
		if(balance <0) 
			return false;
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
	public ArrayList<Account> getAllValidAccounts() {
		// TODO Auto-generated method stub
		List<Account> temp = new ArrayList<Account>();
		temp = cus.viewMyAccounts(accId);
			tempList.clear();
		
		for(int i = 0; i<temp.size(); i++)
			if(temp.get(i).getState().equalsIgnoreCase("A"))
				this.tempList.add(temp.get(i));
		
	return this.tempList;
	}
}
