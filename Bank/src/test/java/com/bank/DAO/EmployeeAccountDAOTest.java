package com.bank.DAO;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.bank.account.Account;

public class EmployeeAccountDAOTest {

	EmployeeAccountDAO em;
	@Before
	public void setUp() throws Exception {
		em = new EmployeeAccountDAO();
	}

	@Test
	public void testSearchUserViaEmail() {
		assertNotNull("Can't find from email", em.searchUserViaEmail("TESTCUS@TEST.COM").get(0));
		assertEquals("Found impossible email",0, em.searchUserViaEmail("tesst").size());
	}

	@Test
	public void testCheckEmployeeLogIn() {
		assertTrue("Employee can't log in", em.checkEmployeeLogIn("TESTEMP@TEST.COM"));
		assertFalse("Non-Employee can log in", em.checkEmployeeLogIn("TESTCUS@TEST.COM"));
	}

	@Test
	public void testDecideAccount() {
		Account acc = new Account();
		acc.setAccNumber("2446413988608");
		assertTrue("Did not update account's state", em.decideAccount(acc, "A"));
	}

	@Test
	public void testGetAllAccounts() {
		assertNotNull("Can't get accounts", em.getAllAccounts());
	}

}
