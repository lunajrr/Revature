package com.bank.Connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.bank.DAO.EmployeeDAO;
import com.bank.Menus.BankStart;
import com.bank.account.Account;
import com.bank.account.Transaction;

public class EmployeeConnect extends AccountConnect implements EmployeeDAO{
	
	private Connection conn;
	
	//Constructor
	public EmployeeConnect() {
		BankStart.LOGGER.info("INSTANCE ID: " + BankStart.instanceID + " || EmployeeAccountDAO Constructor.");	
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/bank", "postgres", "a");
		} catch (SQLException e) {
			
		}
	}
	
	//purpose to close the Connection
	public void closeResources() {
		BankStart.LOGGER.info("INSTANCE ID: " + BankStart.instanceID + " || EmployeeAccountDAO.closeResources().");	
		try {
			super.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
		}
	}

	//Purpose: To retrieve all accounts associated with an email
	//Returns: A Array List of accounts associated to email
	//Improvement Ideas: Transfer the search to a function in SQL
	public ArrayList<Account> searchUserViaEmail(String email){
		ArrayList<Account> accList = new ArrayList<Account>();
		BankStart.LOGGER.info("INSTANCE ID: " + BankStart.instanceID + " || EmployeeAccountDAO.searchUserViaEmail() with email: " + email+".");	
		try {
			PreparedStatement  stmt = conn.prepareStatement("select acc_id, account_number, balance, acc_type, state from accounts inner join log_info on log_info.id = accounts.acc_id where log_info.email = ? ");
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();		
			while(rs.next())
				accList.add(new Account(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getString(5)));
			
			}catch(SQLException e){
				BankStart.LOGGER.error("INSTANCE ID: " + BankStart.instanceID + " || EmployeeAccountDAO.searchUserViaEmail() SQLEXCEPTION with email: " + email+".");
					}
		return accList;
			
		}

	//Purpose: To allow an employee to approve/deny an account
	//Returns: True if all the code executes
	public boolean decideAccount(Account acc, String decision) {
		BankStart.LOGGER.info("INSTANCE ID: " + BankStart.instanceID + " || EmployeeAccountDAO.decideAccount() with Account Number: " + acc.getAccNumber() + " " + decision+".");
		try {
			PreparedStatement stmt = conn.prepareStatement("update accounts set state = ? where account_number = ?");
			stmt.setString(1, decision);
			stmt.setString(2, acc.getAccNumber());
			stmt.execute();
			return true;
			}
			catch(SQLException e) {
				BankStart.LOGGER.error("INSTANCE ID: " + BankStart.instanceID + " || EmployeeAccountDAO.decideAccount() got SQLerror with Account Number: " + acc.getAccNumber() + " " + decision+".");
				System.out.println("Failed Changing Account State");
			}
		
		return false;
	}

	//Purpose: To get an list of all transactions
	//Returns: An arrayList of transactions to display to the employee
	public ArrayList<Transaction> getAllTransactions(){
		ArrayList<Transaction> tranList = new ArrayList<Transaction>();
		BankStart.LOGGER.info("INSTANCE ID: " + BankStart.instanceID + " || EmployeeAccountDAO.getAllTransactions()");
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from transactions order by acc_number, time");
			while(rs.next()) {
				tranList.add(new Transaction(rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getString(5), rs.getString(6)));
			}
			return tranList;
		} catch (SQLException e) {
			BankStart.LOGGER.error("INSTANCE ID: " + BankStart.instanceID + " || EmployeeAccountDAO.getAllTransactions() : Employee got an SQLerror: " + e.toString());
		}
		return tranList;
		
	}

	//Purpose: To get a list of all the accounts
	//Returns: A List of All accounts
	public ArrayList<Account> getAllAccounts(){
		ArrayList<Account> accList = new ArrayList<Account>();
		BankStart.LOGGER.info("INSTANCE ID: " + BankStart.instanceID + " || EmployeeAccountDAO.getAllAccounts() : Employee attempting to get all accounts.");
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from accounts order by acc_id");
			while(rs.next()) {
				accList.add(new Account(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getString(5)));
			}
			return accList;
		} catch (SQLException e) {
			BankStart.LOGGER.error("INSTANCE ID: " + BankStart.instanceID + " || EmployeeAccountDAO.getAllAccounts() : Employee got an SQLerror: " + e.toString());
		}
		return accList;
		
	}
	
	
}
