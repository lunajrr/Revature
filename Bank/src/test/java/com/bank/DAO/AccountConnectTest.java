package com.bank.DAO;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.bank.account.Account;

public class AccountConnectTest {
	private AccountConnect ac;
	
	@Before
	public void beforeTests() {
		ac = new AccountConnect();
	}
	
	
	@Test
	public void testLogIn() {
		assertEquals("Default Login Fail", 1, ac.logIn("TESTCUS@TEST.COM", "test"));
	}

	@Test
	public void testGetTransfer() {
		Account acc = new Account();
		acc.setAccNumber("2446413988608");
		assertNotNull("getTransfers Failed", ac.getTransfer(acc));
	}

	@Test
	public void testGetBalance() {
		assertNotNull("getBalance Failed" , ac.getBalance("2446413988608"));
	}

}