package com.bank.DAO;

import java.util.*;

import com.bank.account.Account;

public interface CustomerDAO extends AccountDAO{
	
	
	List<Account> getAllMyAccounts();
	boolean startTransfer(String accNumber, double amount);
	boolean requestTransfer(String accNumber, double amount);
	Account getAccountInfo(String accNumber);
	
	

}
