package com.bank.Connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.bank.DAO.CustomerDAO;
import com.bank.Menus.BankStart;
import com.bank.account.Account;
import com.bank.account.Transfers;

public class CustomerConnect extends AccountConnect implements CustomerDAO{
	
	private Connection conn;
	//Constructor
	public CustomerConnect() {	
		//initialize connection
		BankStart.LOGGER.info("INSTANCE ID: " + BankStart.instanceID + " || CustomerDAO Constructor.");
		try {
		this.conn = DriverManager.getConnection("jdbc:postgresql://localhost/bank", "postgres", "a");
		}
		catch (SQLException e) {
			
		}
	}
	
	//To close the connection
	public void closeResources() {
		BankStart.LOGGER.info("INSTANCE ID: " + BankStart.instanceID + " || CustomerDAO.closeResources().");	
		try {
			super.close();
			if(!conn.isClosed())
			conn.close();
		} catch (SQLException e) {
			
		}
	}

	//Purpose: To Create a new User by setting up log in info
	//Returns: True if code executes correctly. False if code comes across and exception
	//Potential Improvement Ideas: Transfer the random number generator to the SQL
	public boolean newUser(String email, String password, String first, String last) {
		Random rnd = new Random();
		BankStart.LOGGER.info("INSTANCE ID: " + BankStart.instanceID + " || CustomerDAO.newUser().");	
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
			BankStart.LOGGER.error("INSTANCE ID: " + BankStart.instanceID + " || CustomerDAO.newUser().");	
			return false;	}
	}
	
	//Purpose: To deposit money to an account based on account number.
	//Returns: True if the code is executed correctly. False if there is an exception caught. (or invalid account number)
	public boolean deposit(String accNumber, double amount) {
		BankStart.LOGGER.info("INSTANCE ID: " + BankStart.instanceID + " || CustomerDAO.Deposit() with "+accNumber+" " + amount + ".");	
		try {
		PreparedStatement  stmt = conn.prepareStatement("select deposit(?, ?)");
		stmt.setString(1, accNumber);
		stmt.setDouble(2, amount);	
		ResultSet rs = stmt.executeQuery();
		rs.next();
		return rs.getBoolean(1);
		}catch(SQLException e){
			BankStart.LOGGER.error("INSTANCE ID: " + BankStart.instanceID + " || CustomerDAO.Deposit() with "+accNumber+" " + amount + ".");	
			return false;	}
	}
	
	//Purpose: To withdraw money from an account based on account number
	//Returns: True if the code is executed correctly: False if there is an exception caught. (or invalid account number)
	public boolean withdraw(String accNumber, double amount) {
		BankStart.LOGGER.info("INSTANCE ID: " + BankStart.instanceID + " || CustomerDAO.withdraw() with account Number: "+accNumber+".");
		try {
		PreparedStatement  stmt = conn.prepareStatement("select withdraw(?, ?)");
		stmt.setString(1, accNumber);
		stmt.setDouble(2, amount);	
		ResultSet resSet = stmt.executeQuery();	
		resSet.next();
		return resSet.getBoolean(1);
		}catch(SQLException e){
			BankStart.LOGGER.error("INSTANCE ID: " + BankStart.instanceID + " || CustomerDAO.withdraw() with account Number: "+accNumber+".");
			return false;	}
		
	}
	
	//Purpose: To get the balance of an account by the account number.
	//Returns: The balance or -1 if there was no balance found.
	public double getBalance(String accNumber) {
		BankStart.LOGGER.info("INSTANCE ID: " + BankStart.instanceID + " || CustomerDAO.getBalance() with account Number: "+accNumber+".");
		try {
			PreparedStatement stmt = conn.prepareStatement("select balance from accounts where account_number = ?");
			stmt.setString(1, accNumber);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			return rs.getDouble(1);
			
		}catch(SQLException e){
			BankStart.LOGGER.error("INSTANCE ID: " + BankStart.instanceID + " || CustomerDAO.getBalance() with account Number: "+accNumber+".");
			System.out.println("Erorr: getBalance: Can't get balance");	
		}
		return -1;
		
	}
	
	//Purpose: To find all the accounts related to the customer based on id.
	//Returns: A list of all the accounts associated to the id.
	public ArrayList<Account> viewMyAccounts(int id) {
		BankStart.LOGGER.info("INSTANCE ID: " + BankStart.instanceID + " || CustomerDAO.viewMyAccount() with ID: "+id+".");	
		ArrayList<Account> accList = new ArrayList<Account>();
		try {
		PreparedStatement  stmt = conn.prepareStatement("select * from accounts where acc_id = ? order by account_number ");
		stmt.setInt(1, id);
		ResultSet resSet = stmt.executeQuery();		
		//Initialize an account and add it to the list
		while(resSet.next()){
			accList.add(new Account(id, resSet.getString("account_number"), resSet.getDouble("balance"), resSet.getString("acc_type"), resSet.getString("state")));
		}
		return accList;
		
		}catch(SQLException e){
			BankStart.LOGGER.error("INSTANCE ID: " + BankStart.instanceID + " || CustomerDAO.viewMyAccount() with ID: "+id+".");	
			return null;}
		
	}
	
	//Purpose: To post an transfer from this account to another account based on account number.
	//Returns: True if the code is executed correctly. False if there is an exception caught.
	public boolean startTransfer(Account acc, String accNumber, double amount) {
		BankStart.LOGGER.info("INSTANCE ID: " + BankStart.instanceID + " || CustomerDAO.startTransfer() with  "+acc+" to" +accNumber+"");	
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
			BankStart.LOGGER.error("INSTANCE ID: " + BankStart.instanceID + " || CustomerDAO.startTransfer() with  "+acc+" to" +accNumber+"");	
			System.out.println("Failed posting Transfer");
		}
		
		return false;
	}
	
	//Purpose: To get all transfers associated with the account
	//Return : A list of transfers
	public ArrayList<Transfers> getTransfer(Account acc){
		BankStart.LOGGER.info("INSTANCE ID: " + BankStart.instanceID + " || CustomerDAO.getTransfer() with  "+acc+" ");	
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
				BankStart.LOGGER.error("INSTANCE ID: " + BankStart.instanceID + " || CustomerDAO.getTransfer() with  "+acc+" ");	
				System.out.println("Failed getting Transfer");
			}
		
		return tranList;
		
	}

	//Purpose: To create a new account
	//Return: True/false depending on if account is created or not
	public boolean newAccount(Account acc) {
		BankStart.LOGGER.info("INSTANCE ID: " + BankStart.instanceID + " || CustomerDAO.newAccount() with  "+acc+" ");	
		try {
		PreparedStatement  stmt = conn.prepareStatement("select create_new_acc(?, ?, ?, ?)");
		stmt.setInt(1, acc.getAccID());
		stmt.setString(2, acc.getAccNumber());	
		stmt.setDouble(3, acc.getBalance());
		stmt.setString(4, acc.getAccTypeChar());
		ResultSet rs = stmt.executeQuery();
		rs.next();
		return rs.getBoolean(1);
		}catch(SQLException e){
			BankStart.LOGGER.error("INSTANCE ID: " + BankStart.instanceID + " || CustomerDAO.newAccount() with  "+acc+" ");	
			System.out.println("User already exists");
			return false;	
			}
	}
	
	
}
