package com.bank.account;

import java.util.ArrayList;
import java.util.Random;

import com.bank.DAO.CustomerDAO;

public class Account  {
	private int accountID;
	private String accNumber;
	private double balance = 0;
	private String accType;
	private String state;
	private ArrayList<Transfers> tranList;
	
	
	
	
	public Account(int accID, double balance, String accType) {
		super();
		
		//create a random account number (Better Option: Auto increment in database)
		Random rnd = new Random();
		int firstHalf = rnd.nextInt(9999999);
		int secondHalf = rnd.nextInt(999999);
		this.accNumber = String.format("%07d%06d", firstHalf, secondHalf);
		
		this.accountID = accID;
		this.balance = balance;
		this.accType = accType;
		this.state = "P";}
	
	
	public Account(int accID,String accNumber, double balance, String accType, String accState) {
		super();
		this.accountID = accID;
		this.accNumber = accNumber;
		this.balance = balance;
		this.accType = accType;
		this.state = accState;
	
	}




	public Account() {	
	}

	public String getAccTypeChar() {
		return this.accType;
	}
	
	@Override
	public String toString() {
		String type = accType;
		String pending = state;
		if(accType.equalsIgnoreCase("C"))
			type = "Checking";
		else
			type = "Saving";
		if(state.equalsIgnoreCase("P"))
			pending = "Pending";
		else if(state.equalsIgnoreCase("A"))
			pending = "Approve";
			else if(state.equalsIgnoreCase("D"))
			pending = "Denied";
		
		return String.format( "User ID: %d  Account Number: %s  Account Balance: $%.2f  Account Type: %s Account Status: %s",accountID, accNumber, balance, type, pending);
	}

	//Getters
	public String getAccNumber() {
		return accNumber;
	}
	
	public void setAccNumber(String accNumber) {
		this.accNumber =accNumber;
	}

	public String getAccType() {
		if(accType.equalsIgnoreCase("P"))
			return "Pending";
		else if(accType.equalsIgnoreCase("C"))
			return "Checking";
		else if(accType.equalsIgnoreCase("S"))
			return "Saving";
		return this.accType;
	}
	
	public int getAccID() {
		return this.accountID;
	}
	
	public String getState() {
		return this.state;
	}
	
	public double getBalance() {
		return balance;
	}
	
	//Purpose: To Deposit the amount
	//Return: A String: Successful or failed depending on invalid amount
	public String deposit(double amount, CustomerDAO cDAO) {
		
		if(amount > 0) {
			if(cDAO.deposit(accNumber, amount))
				this.balanceUpdate(cDAO);
				return "Deposit Successful";}
		else if(amount <=0)
			return "Deposit Failed, Invalid Amount";
		
		return "Deposit Failed";
	}
	
	//Purpose: To withdraw the amount from the account
	//Return: A string: Successful or failed depending on the cause
	public String withdraw(double amount, CustomerDAO cDAO) {
		if(amount > 0 && amount < balance) {
			if(cDAO.withdraw(accNumber, amount)) {
				this.balanceUpdate(cDAO);
				return "Withdraw successful";
			}}
		else if(amount < 0) {
			return "Invalid Amount";
		}
		else if(amount>balance) 
			return "Balance too low";
		return "Withdraw failed";
	}	
	 

	//Purpose: To update this balance via getting balance from server
	private void balanceUpdate(CustomerDAO cDAO) {
		double newBalance = cDAO.getBalance(this.accNumber);
		if (newBalance >=0) {
			this.balance = newBalance;
		}
	}


	//Purpose: to start a transfer from this account
	//Return: A boolean depending on successful execution or not. (or amount is too high for the balance)
	public boolean startTransfer(CustomerDAO cDAO, String accNumber, double amount) {
		
		if(amount> this.balance) {
			System.out.println("Balance can't be negative; Transfer request denied");
			return false;
		}
		return cDAO.startTransfer(this, accNumber, amount);	
		
	}

	//Purpose: to get all transfers this account is associated with.
	//Return: A list of transfers
	public ArrayList<Transfers> getTransfers(CustomerDAO cDAO) {
		this.tranList = cDAO.getTransfer(this);
		return this.tranList;
	}
	
	//Purpose: To get all transfers that are waiting for approval from this account
	//Return: A list of transfers that are pending	
	public ArrayList<Transfers> getPendingTransfer(CustomerDAO cDAO){
		
		ArrayList<Transfers> tempTran = new ArrayList<Transfers>();
		ArrayList<Transfers> pendTran = this.getTransfers(cDAO);
		if(tranList != null) {
			for(int i = 0; i < tranList.size(); i++)
				if(pendTran.get(i).getRecievingAcc().equals(this.accNumber))
					tempTran.add(pendTran.get(i));
		}
		return tempTran;
		
	}
	



	
}
