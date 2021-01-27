package com.bank.account;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EmployeeTest {

	Employee em;
	@Before
	public void setUp() throws Exception {
		em = new Employee();
	}

	@Test
	public void testGetAllTransactions() {
		assertNotNull("Error getting Transactions", em.getAllTransactions().get(0));
	}

}
