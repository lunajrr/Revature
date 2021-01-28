package com.bank.DAO;

import java.util.ArrayList;

import com.bank.account.Account;
import com.bank.account.Transfers;

public interface AccountDAO {
	
	public void close();
	public boolean checkEmployeeLogIn(String email);
	public int logIn(String email, String password);
	public ArrayList<Transfers> getTransfer(Account acc);
	public boolean decideTransfer(Transfers trans, String decision);

}
