package com.bank.DAO;

import java.util.ArrayList;

import com.bank.account.Account;
import com.bank.account.Transaction;

public interface EmployeeDAO extends AccountDAO {

	public void closeResources();
	public ArrayList<Account> searchUserViaEmail(String email);
	public boolean decideAccount(Account acc, String decision);
	public ArrayList<Transaction> getAllTransactions();
	public ArrayList<Account> getAllAccounts();
	
}
