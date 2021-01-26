package com.bank.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.bank.JDBC.*;
import com.bank.account.Account;
import com.bank.account.Transfers;

public class CustomerDAO extends AccountConnect{
	
	private Connection conn;
	
	public CustomerDAO() {	
		//initialize connection
		try {
		this.conn = DriverManager.getConnection("jdbc:postgresql://localhost/bank", "postgres", "a");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closeResources() {
		try {
			if(!conn.isClosed())
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//Purpose: To Create a new User by setting up log in info
	//Returns: True if code executes correctly. False if code comes across and exception
	//Potential Improvement Ideas: Transfer the random number generator to the SQL
	public boolean newUser(String email, String password, String first, String last) {
		Random rnd = new Random();
		try {
		PreparedStatement  stmt = conn.prepareStatement("insert into log_info values (?,?, ? , ? , ? ,?)");
		stmt.setInt(1, rnd.nextInt(999999));
		stmt.setString(2, email);
		stmt.setString(3, password);	
		stmt.setString(4, first);
		stmt.setString(5, last);
		stmt.setBoolean(6,false);
		stmt.execute();
		return true;
		
		}catch(SQLException e){
			e.printStackTrace();
			return false;	}
	}
	
	//Purpose: To deposit money to an account based on account number.
	//Returns: True if the code is executed correctly. False if there is an exception caught. (or invalid account number)
	public boolean deposit(String accNumber, double amount) {
		try {
		PreparedStatement  stmt = conn.prepareStatement("select deposit(?, ?)");
		stmt.setString(1, accNumber);
		stmt.setDouble(2, amount);	
		ResultSet rs = stmt.executeQuery();
		rs.next();
		return rs.getBoolean(1);
		}catch(SQLException e){
			e.printStackTrace();
			return false;	}
	}
	
	//Purpose: To withdraw money from an account based on account number
	//Returns: True if the code is executed correctly: False if there is an exception caught. (or invalid account number)
	public boolean withdraw(String accNumber, double amount) {
		try {
		PreparedStatement  stmt = conn.prepareStatement("select withdraw(?, ?)");
		stmt.setString(1, accNumber);
		stmt.setDouble(2, amount);	
		ResultSet resSet = stmt.executeQuery();	
		resSet.next();
		return resSet.getBoolean(1);
		}catch(SQLException e){
			e.printStackTrace();
			return false;	}
		
	}
	
	//Purpose: To get the balance of an account by the account number.
	//Returns: The balance or -1 if there was no balance found.
	public double getBalance(String accNumber) {
		
		try {
			PreparedStatement stmt = conn.prepareStatement("select balance from accounts where account_number = ?");
			stmt.setString(1, accNumber);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			return rs.getDouble(1);
			
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("Erorr: getBalance: Can't get balance");	
		}
		return -1;
		
	}
	
	//Purpose: To find all the accounts related to the customer based on id.
	//Returns: A list of all the accounts associated to the id.
	public ArrayList<Account> viewMyAccounts(int id) {
		ArrayList<Account> accList = new ArrayList<Account>();
		try {
		PreparedStatement  stmt = conn.prepareStatement("select * from accounts where acc_id = ?");
		stmt.setInt(1, id);
		ResultSet resSet = stmt.executeQuery();		
		//Initialize an account and add it to the list
		while(resSet.next()){
			accList.add(new Account(id, resSet.getString("account_number"), resSet.getDouble("balance"), resSet.getString("acc_type"), resSet.getString("state")));
		}
		return accList;
		
		}catch(SQLException e){
			e.printStackTrace();
			return null;}
		
	}
	
	//Purpose: To post an transfer from this account to another account based on account number.
	//Returns: True if the code is executed correctly. False if there is an exception caught.
	public boolean startTransfer(Account acc, String accNumber, double amount) {
		try {
		PreparedStatement stmt = conn.prepareStatement("select start_transfer(?,?,?)");
		stmt.setString(1, acc.getAccNumber());
		stmt.setString(2, accNumber);
		stmt.setDouble(3, amount);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		return rs.getBoolean(1);
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Failed posting Transfer");
		}
		
		return false;
	}
	
	//Purpose: To get all transfers associated with the account
	//Return : A list of transfers
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
				e.printStackTrace();
				System.out.println("Failed getting Transfer");
			}
		
		return tranList;
		
	}

	//Purpose: To create a new account
	//Return: True/false depending on if account is created or not
	public boolean newAccount(Account acc) {
		try {
		PreparedStatement  stmt = conn.prepareStatement("select create_new_acc(?, ?, ?, ?)");
		stmt.setInt(1, acc.getAccID());
		stmt.setString(2, acc.getAccNumber());	
		stmt.setDouble(3, acc.getBalance());
		stmt.setString(4, acc.getAccType());
		ResultSet rs = stmt.executeQuery();
		rs.next();
		return rs.getBoolean(1);
		}catch(SQLException e){
			e.printStackTrace();
			return false;	
			}
	}
	
	
}
