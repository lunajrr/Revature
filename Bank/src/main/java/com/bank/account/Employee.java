package com.bank.account;

import java.util.ArrayList;
import com.bank.DAO.EmployeeAccountDAO;
import com.bank.JDBC.AccountConnect;

public class Employee extends Account implements EmployeeAccountDAO{
	private int accId;
	private AccountConnect ac;
	private ArrayList<Account> accList;
	private ArrayList<Account> cus;
	
	public Employee() {
		this.ac = new AccountConnect();
	}

	public ArrayList<Account> getAllAccounts() {
		// TODO Auto-generated method stub
		
		this.accList =ac.getAllAccounts();
		return accList;
	}

	public boolean logIn(String user, String pass) {
		accId = ac.logIn(user, pass);
		if(accId== -1)
			return false;
		else
			return true;
		
	}
	
	public ArrayList<Account> getAllPendingAccounts() {
		// TODO Auto-generated method stub
		getAllAccounts();
		ArrayList<Account> pendingAcc = new ArrayList<Account>();
		for(int i = 0; i<accList.size(); i++) {
			if(accList.get(i).getState().equalsIgnoreCase("P"))
				pendingAcc.add(accList.get(i));
		}
		return pendingAcc;
	}
	
	public boolean decideTransfer(int index,String decision) {

			return false;
			
	}

	public ArrayList<Transaction> getAllTransactions() {
		// TODO Auto-generated method stub
		
		
		return ac.getAllTransactions();
	}

	public Transaction searchTransaction(String accNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	public Account searchAccount(String accNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Transaction> getAllPendingTransactions() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ArrayList<Account> searchViaEmail(String email) {
		cus = ac.searchUserViaEmail(email);
		return cus;
}}

