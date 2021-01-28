package com.bank.DAO;

import java.util.ArrayList;

import com.bank.account.Account;
import com.bank.account.Transfers;

public interface CustomerDAO extends AccountDAO{
	
	public void closeResources();
	public boolean newUser(String email, String password, String first, String last);
	public boolean deposit(String accNumber, double amount);
	public boolean withdraw(String accNumber, double amount);
	public double getBalance(String accNumber);
	public ArrayList<Account> viewMyAccounts(int id);
	public boolean startTransfer(Account acc, String accNumber, double amount);
	public ArrayList<Transfers> getTransfer(Account acc);
	public boolean newAccount(Account acc);

}
