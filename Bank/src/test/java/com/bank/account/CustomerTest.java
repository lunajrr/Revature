package com.bank.account;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CustomerTest {

	Customer cus;
	@Before
	public void setUp() throws Exception {
	cus = new Customer();
	cus.setAccId(1);
	}

	@Test
	public void testGetAllMyAccounts() {
		assertNotNull("Can't get my Accounts", cus.getAllMyAccounts().get(0));
	}



	@Test
	public void testGetpending() {
		assertNotNull("Can't get pending transfers", cus.getpending(0));
	}

	

	@Test
	public void testCreateNewAccount() {
		assertTrue("Couldn't create account", cus.createNewAccount(50, "C"));
	}

	@Test
	public void testCreateNewUser() {
		assertTrue("Couldn't create user", cus.createNewUser("email@email.com", "email", "email", "email"));
		assertFalse("Could create a user with the same user", cus.createNewUser("email@email.com", "email", "email", "email"));
	}

}
