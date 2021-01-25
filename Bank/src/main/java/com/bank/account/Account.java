package com.bank.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.bank.DAO.AccountDAO;
import com.bank.JDBC.AccountConnect;

public class Account implements AccountDAO {
	private int accountID;
	private String accNumber;
	private double balance = 0;
	private String accType;
	private String state;
	private ArrayList<Transfers> tranList;
	
	
	
	
	public Account(int accID, double balance, String accType) {
		super();
		
		//create a random account number
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

	public String deposit(double amount, AccountConnect ac) {
		
		if(amount > 0) {
			if(ac.deposit(accNumber, amount))
				this.balanceUpdate(ac);
				return "Deposit Successful";}
		else if(amount <=0)
			return "Deposit Failed, Invalid Amount";
		
		return "Deposit Failed";
	}
	
	public String withdraw(double amount, AccountConnect ac) {
		if(amount > 0 && amount < balance) {
			if(ac.withdraw(accNumber, amount)) {
				this.balanceUpdate(ac);
				return "Withdraw successful";
			}}
		else if(amount < 0) {
			return "Invalid Amount";
		}
		else if(amount>balance) 
			return "Balance too low";
		return "Withdraw failed";
	}	
	 
	public String getAccNumber() {
		return accNumber;
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
	//update the balance

	private void balanceUpdate(AccountConnect ac) {
		double newBalance = ac.getBalance(this.accNumber);
		if (newBalance >=0) {
			this.balance = newBalance;
		}
	}


	public boolean startTransfer(AccountConnect ac, String accNumber, double amount) {
		
		if(amount> this.balance) {
			System.out.println("Balance can't be negative; Transfer request denied");
			return false;
		}
		return ac.startTransfer(this, accNumber, amount);	
		
	}


	public ArrayList<Transfers> getTransfers(AccountConnect ac) {
		this.tranList = ac.getTransfer(this);
		return this.tranList;
	}
	
	public ArrayList<Transfers> getPendingTransfer(AccountConnect ac){
		
		Transfers tran = null;
		ArrayList<Transfers> pendTran = this.getTransfers(ac);
		System.out.println(tranList);
		if(tranList != null) {
			for(int i = 0; i < tranList.size(); i++)
				tran = tranList.get(i);
				if(tran.getRecievingAcc().equals(this.accNumber) && tran.getState().equalsIgnoreCase("P"))
					pendTran.add(tran);
				
		}
		return pendTran;
		
	}
	
	public boolean logIn(String email, String password) {
		// TODO Auto-generated method stub
		return false;
	}




	
}
