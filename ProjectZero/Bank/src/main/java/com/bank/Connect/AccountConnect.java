package com.bank.Connect;

import java.sql.*;
import java.util.*;

import com.bank.DAO.AccountDAO;
import com.bank.Menus.BankStart;
import com.bank.account.Account;
import com.bank.account.Transfers;

public class AccountConnect implements AccountDAO{

	private Connection conn;
	
	//Constructor
	public AccountConnect() {	
		BankStart.LOGGER.info("INSTANCE ID: " + BankStart.instanceID + " || AccountConnect constructor");
		try {
		this.conn = DriverManager.getConnection("jdbc:postgresql://localhost/bank", "postgres", "a");
		}
		catch (SQLException e) {
			
		}
	}
	
	//Purpose: To close resources
	public void close() {
		BankStart.LOGGER.info("INSTANCE ID: " + BankStart.instanceID + " || AccountConnect.close()");
		try {
			this.conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Purpose: To validate if an email is associated with an employee
	//Returns: If the log in info is an employee returns true; else returns false;
	public boolean checkEmployeeLogIn(String email) {
		BankStart.LOGGER.info("INSTANCE ID: " + BankStart.instanceID + " || EmployeeAccountDAO.checkEmployeeLogIn() with email:  " + email+".");	
		try {
			PreparedStatement  stmt = conn.prepareStatement("select employee from log_info where email = ?");
			stmt.setString(1, email);
			ResultSet resSet = stmt.executeQuery();		
			if(resSet.next()) {
				return resSet.getBoolean(1);	
			}
			}catch(SQLException e){
				BankStart.LOGGER.error("INSTANCE ID: " + BankStart.instanceID + " || EmployeeAccountDAO.checkEmployeeLogIn() got SQLerror with email: "  + email+".");
				return false;	}
		return false;
		}
	
	//Purpose: To log in with email/password
	//Return: The accountID
	public int logIn(String email, String password) {
		BankStart.LOGGER.info("INSTANCE ID: " + BankStart.instanceID + " || AccountConnect.logIn() with: " +email+".");
		try {
			PreparedStatement  stmt = conn.prepareStatement("select * from log_info where email = ? and password = ?");
			stmt.setString(1, email);
			stmt.setString(2, password);	
			ResultSet resSet = stmt.executeQuery();
	
			if(resSet.next()) {
				return resSet.getInt(1);	
			}
			}catch(SQLException e){
				BankStart.LOGGER.error("INSTANCE ID: " + BankStart.instanceID + " || AccountConnect.logIn() with: " +email+".");
				return -1;	}
		return -1;
	}
			
	//needs work
	//Purpose: To get a list of transfers associated with an account number
	//Return: An ArrayList of transfers that is associated with the account
	public ArrayList<Transfers> getTransfer(Account acc){
		ArrayList<Transfers> tranList = new ArrayList<Transfers>();
		BankStart.LOGGER.info("INSTANCE ID: " + BankStart.instanceID + " || AccountConnect.getTransefer() with: " +acc+".");
		try {
			PreparedStatement stmt = conn.prepareStatement("select * from transfers where send_acc = ? or rec_acc = ?");
			stmt.setString(1, acc.getAccNumber());
			stmt.setString(2, acc.getAccNumber());
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				tranList.add(new Transfers(rs.getInt(1), rs.getString(4), rs.getString(3),rs.getDouble(2),  rs.getString(5)));
			}
			return tranList;
			}
			catch(SQLException e) {
				BankStart.LOGGER.error("INSTANCE ID: " + BankStart.instanceID + " || AccountConnect.getTransefer() with: " +acc+".");
				System.out.println("Failed getting Transfer");
			}
		
		return tranList;
		
	}
			
	//Purpose: to allow the customer to decide if they want to approve/deny a transfer
	//Return boolean if the code executes with no error, else return false;
	public boolean decideTransfer(Transfers trans, String decision) {
		BankStart.LOGGER.info("INSTANCE ID: " + BankStart.instanceID + " || AccountConnect.decideTransfer() with: " +trans+" "+ decision+".");
		try {
			PreparedStatement stmt = conn.prepareStatement("update transfers set state = ? where tid = ?");
			stmt.setString(1, decision);
			stmt.setInt(2, trans.getTid());
			stmt.execute();
			return true;
			}
			catch(SQLException e) {
				BankStart.LOGGER.error("INSTANCE ID: " + BankStart.instanceID + " || AccountConnect.decideTransfer() with: " +trans+" "+ decision+".");
				System.out.println("Failed getting Transfer");
			}
		
		return false;
	}
	
	
	
}
