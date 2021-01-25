package com.bank.JDBC;

import static org.junit.Assert.*;

import org.junit.*;

public class AccountConnectTest {

	AccountConnect ac;
	@Before
	public void setUp() {
		 ac = new AccountConnect();
	}
	
	//testing login Successful
	@Test
	public void logInSuccessful() {
		assertEquals(ac.logIn("juan@gmail.com","123"),1);
	}
	
	//testing login Fail
	@Test
	public void logInFailure() {
		assertEquals(-1,ac.logIn("john.gmail.com","123"));
	}
	
	//Testing deposit
	@Test
	public void testDeposit() {

		assertTrue("Deposit fail", ac.deposit("123456789", 40.25));
	}
	
	//testing Withdrawing
	@Test
	public void testWithdraw() {
		assertTrue("Withdraw fail", ac.withdraw("123456789", 40.25));
	}

}
