package com.bank.DAO;

import java.util.ArrayList;

import com.bank.account.Account;
import com.bank.account.Transaction;

public interface EmployeeAccountDAO extends AccountDAO{
	
	 ArrayList<Account> getAllAccounts();
	 ArrayList<Account> getAllPendingAccounts();
	 ArrayList<Transaction> getAllTransactions();
	 ArrayList<Transaction> getAllPendingTransactions();
	Transaction searchTransaction(String accNumber);
	Account	searchAccount(String accNumber);

}
