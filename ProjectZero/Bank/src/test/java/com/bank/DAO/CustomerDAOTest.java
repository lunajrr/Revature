package com.bank.DAO;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.bank.Connect.CustomerConnect;
import com.bank.account.Account;

public class CustomerDAOTest {

	private CustomerConnect cus;
	
	@Before
	public void setUp() {
		cus = new CustomerConnect();
	}

	@Test
	public void testDeposit() {
		assertTrue("Deposit Failed", cus.deposit("5585445435867", 50));
	}

	@Test
	public void testWithdraw() {
		assertTrue("Withdraw Failed", cus.withdraw("5585445435867", 50));
	}

	@Test
	public void testViewMyAccounts() {
		assertNotNull("Can't View Accounts", cus.viewMyAccounts(1).get(1));
	}

	@Test
	public void testStartTransfer() {
		Account acc =new Account();
		acc.setAccNumber("5585445435867");
		assertTrue("Can't Start Transfer", cus.startTransfer(acc, "2446413988608", 50));
	}

	@Test
	public void testNewAccount() {
		Account acc = new Account(1, 100, "C");
		assertTrue("Couldn't Create a new Account", cus.newAccount(acc));
		
	}

}
