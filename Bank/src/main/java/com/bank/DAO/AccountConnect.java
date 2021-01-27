package com.bank.DAO;

import java.sql.*;
import java.util.*;

import com.bank.account.Account;
import com.bank.account.Transfers;

public class AccountConnect {

	private Connection conn;
	
	public AccountConnect() {	
		try {
		this.conn = DriverManager.getConnection("jdbc:postgresql://localhost/bank", "postgres", "a");
		}
		catch (SQLException e) {
			
		}
	}
	
	
	
	//Purpose: To log in with email/password
	//Return: The accountID
	public int logIn(String email, String password) {
		
		try {
			PreparedStatement  stmt = conn.prepareStatement("select * from log_info where email = ? and password = ?");
			stmt.setString(1, email);
			stmt.setString(2, password);	
			ResultSet resSet = stmt.executeQuery();
	
			if(resSet.next()) {
				return resSet.getInt(1);	
			}
			}catch(SQLException e){
				
				return -1;	}
		return -1;
	}
			
	//needs work
	

	public ArrayList<Transfers> getTransfer(Account acc){
		ArrayList<Transfers> tranList = new ArrayList<Transfers>();
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
				
				System.out.println("Failed getting Transfer");
			}
		
		return tranList;
		
	}
	
	public double getBalance(String accNumber) {
		
		try {
			PreparedStatement stmt = conn.prepareStatement("select balance from accounts where account_number = ?");
			stmt.setString(1, accNumber);
			ResultSet rs = stmt.executeQuery();
			
			rs.next();
			return rs.getDouble(1);
			
		}catch(SQLException e){
			
			System.out.println("Erorr: getBalance: Can't get balance");
			
		}
		return -1;
	}
			

	public boolean decideTransfer(Transfers trans, String decision) {
		try {
			PreparedStatement stmt = conn.prepareStatement("update transfers set state = ? where tid = ?");
			stmt.setString(1, decision);
			stmt.setInt(2, trans.getTid());
			stmt.execute();
			return true;
			}
			catch(SQLException e) {
				
				System.out.println("Failed getting Transfer");
			}
		
		return false;
	}
	
	
	
}
