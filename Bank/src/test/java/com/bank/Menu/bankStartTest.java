package com.bank.Menu;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.bank.account.Account;

public class bankStartTest {
	Account acc;
	@Before
	public void beforeTest() {
		 acc = new Account(1, 100.00, "C");
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
