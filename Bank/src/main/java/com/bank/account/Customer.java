package com.bank.account;

import java.util.*;

import com.bank.DAO.CustomerDAO;
import com.bank.JDBC.AccountConnect;

public class Customer implements CustomerDAO{

	
	private int accId;
	private List<Account> accList;
	
	private AccountConnect ac;

	
	public Customer() {
		super();
		this.accId = -1;
		this.accList = new ArrayList<Account>();
		ac = new AccountConnect();
		getAllMyAccounts();
	}
	
	public boolean logIn(String user, String pass) {
		accId = ac.logIn(user, pass);
		if(accId== -1)
			return false;
		else
			return true;
		
	}

	public String withdraw(int index, double amount) {
		return accList.get(index).withdraw(amount, ac);
	}
	
	public String deposit(int index, double amount) {
		return accList.get(index).deposit(amount, ac);
	}
	
	public List<Account> getAccList(){
		return this.accList;
	}
	
	public List<Account> getAllMyAccounts() {
		// TODO Auto-generated method stub
		if(accId != -1)
			accList = ac.viewMyAccounts(accId);
		return accList;
		
	}

	public boolean startTransfer(int index, String accNumber, double amount) {
		// TODO Auto-generated method stub
		Account acc = accList.get(index);
		
		if(amount> acc.getBalance())
			return false;
		
		return acc.startTransfer(ac, accNumber, amount);
		
		
		
	}

	public ArrayList<Transfers>getpending(int index) {
		// TODO Auto-generated method stub
		return accList.get(index).getPendingTransfer(ac);
		
	}
	

	public Account getAccountInfo(String accNumber) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean createNewAccount(double balance, String type) {
		Account acc = new Account(accId, balance, type );
		if(ac.newAccount(acc)) {
			accList.add(acc);
			return true;
		}
		else return false;
		
	}

	public boolean createNewUser(String email, String password, String first, String last) {
		
		return ac.newUser(email, password, first, last);
	}

	@Override
	public boolean startTransfer(String accNumber, double amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean requestTransfer(String accNumber, double amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
