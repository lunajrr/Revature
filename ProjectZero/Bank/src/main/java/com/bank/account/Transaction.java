package com.bank.account;


public class Transaction {

	String accNumber;
	double amount;
	String type;
	String description;
	String timeStamp;
	
	public Transaction(String accNumber, double amount, String type, String description, String timeStamp) {
		super();
		this.accNumber = accNumber;
		this.amount = amount;
		this.description = description;
		this.type = type;
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return "Account Number: " + accNumber + ", Amount: " + amount + ", Type: " + type + ", Description:"
				+ description + ", TimeStamp: " + timeStamp;
	}
	
	
}
