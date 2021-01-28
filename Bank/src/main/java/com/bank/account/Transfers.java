package com.bank.account;

public class Transfers {
	private int tid;
	private String sendingAcc;
	private String recievingAcc;
	private double amount;
	private String state;
	
	//constructor
	public Transfers(int tid,  String sendingAcc , String recievingAcc, double amount, String state) {
		super();
		this.tid = tid;
		this.sendingAcc = sendingAcc;
		this.recievingAcc = recievingAcc;
		this.amount = amount;
		this.state = state;
	}
	
	//getters
	public int getTid() {
		return this.tid;
	}
	
	public String getSendingAcc() {
		return sendingAcc;
	}
	
	public String getRecievingAcc() {
		return recievingAcc;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public String getState() {
		return state;
	}
	
	@Override
	public String toString() {
		String pending = "Pending";
		if(state.equalsIgnoreCase("P"))
			pending = "Pending";
		else if(state.equalsIgnoreCase("A"))
			pending = "Approve";
			else if(state.equalsIgnoreCase("D"))
			pending = "Denied";
		return String.format( "Transfer ID: %d     Sending from: %s  to  %s    amount: $%.2f    state:  %s",tid, sendingAcc, recievingAcc, amount, pending);}


}
