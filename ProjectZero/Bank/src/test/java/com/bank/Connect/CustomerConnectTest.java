package com.bank.Connect;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bank.account.Account;

class CustomerConnectTest {

	private CustomerConnect cus;
	
	@BeforeEach
	public void setUp() {
		this.cus = new CustomerConnect();
	}

	@Test
	public void testDeposit() {
		this.cus = new CustomerConnect();
		assertTrue("Deposit Failed", this.cus.deposit("5585445435867", 50));
	}

	@Test
	public void testWithdraw() {
		this.cus = new CustomerConnect();
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
		acc.setAccType("C");
		acc.setAccState("P");
		assertTrue("Can't Start Transfer", cus.startTransfer(acc, "2446413988608", 50));
	}

	@Test
	public void testNewAccount() {
		Account acc = new Account(1, 100, "C");
		assertTrue("Couldn't Create a new Account", cus.newAccount(acc));
		
	}


}
