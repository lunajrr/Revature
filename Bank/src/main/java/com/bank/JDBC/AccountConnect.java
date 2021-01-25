package com.bank.JDBC;

import java.sql.*;
import java.util.*;

import com.bank.account.Account;
import com.bank.account.Transaction;
import com.bank.account.Transfers;

public final class AccountConnect {

	private Connection conn;
	
	public AccountConnect() {	
		try {
		this.conn = DriverManager.getConnection("jdbc:postgresql://localhost/bank", "postgres", "a");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Account> searchUserViaEmail(String email){
		ArrayList<Account> accList = new ArrayList<Account>();
		try {
			PreparedStatement  stmt = conn.prepareStatement("select acc_id, account_number, balance, acc_type, state from accounts inner join log_info on log_info.id = accounts.acc_id where log_info.email = ? ");
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();		
			while(rs.next())
				accList.add(new Account(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getString(5)));
			
			}catch(SQLException e){
				e.printStackTrace();
					}
		return accList;
			
		}
	
	public boolean deposit(String accNumber, double amount) {
		
		try {
		PreparedStatement  stmt = conn.prepareStatement("select deposit(?, ?)");
		stmt.setString(1, accNumber);
		stmt.setDouble(2, amount);	
		ResultSet resSet = stmt.executeQuery();		
		return resSet.next();
		
		}catch(SQLException e){
			e.printStackTrace();
			return false;	}
		
	}
	
	
	public boolean withdraw(String accNumber, double amount) {
		
		try {
		PreparedStatement  stmt = conn.prepareStatement("select withdraw(?, ?)");
		stmt.setString(1, accNumber);
		stmt.setDouble(2, amount);	
		ResultSet resSet = stmt.executeQuery();		
		return resSet.next();
		
		}catch(SQLException e){
			e.printStackTrace();
			return false;	}
		
	}
	
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
				e.printStackTrace();
				return -1;	}
		return -1;
	}
	
	public List<Account> viewMyAccounts(int id) {
		ArrayList<Account> accList = new ArrayList<Account>();
		
		
		try {
		PreparedStatement  stmt = conn.prepareStatement("select * from accounts where acc_id = ?");
		
		stmt.setInt(1, id);
		ResultSet resSet = stmt.executeQuery();		
		while(resSet.next()){
			accList.add(new Account(id, resSet.getString("account_number"), resSet.getDouble("balance"), resSet.getString("acc_type"), resSet.getString("state")));
		}
		return accList;
			
		
		}catch(SQLException e){
			e.printStackTrace();
			return null;}
		
	}
		
	//needs work
	public boolean startTransfer(Account acc, String accNumber, double amount) {
		Random rnd = new Random();
		try {
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO transfers(tid, amount, rec_acc, send_acc, state) VALUES (?, ?, ?, ?, ?)");
		stmt.setInt(1, rnd.nextInt(999999));
		stmt.setDouble(2, amount);
		stmt.setString(3, accNumber);
		stmt.setString(4, acc.getAccNumber());
		stmt.setString(5, "P");
		stmt.execute();
		return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Failed posting Transfer");
		}
		
		return false;
	}

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
	
	public boolean newAccount(Account acc) {
		try {
		PreparedStatement  stmt = conn.prepareStatement("insert into accounts values (?, ? , ? , ? ,?)");
		stmt.setInt(1, acc.getAccID());
		stmt.setString(2, acc.getAccNumber());	
		stmt.setDouble(3, acc.getBalance());
		stmt.setString(4, acc.getAccType());
		stmt.setString(5, acc.getState());
		stmt.execute();
		return true;
		
		}catch(SQLException e){
			e.printStackTrace();
			return false;	}
		
	}
		

	public ArrayList<Account> getAllAccounts(){
		ArrayList<Account> accList = new ArrayList<Account>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from accounts");
			while(rs.next()) {
				accList.add(new Account(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getString(5)));
			}
			System.out.println(accList);
			return accList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accList;
		
	}
	
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
		
	public ArrayList<Transaction> getAllTransactions(){
		ArrayList<Transaction> tranList = new ArrayList<Transaction>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from transactions");
			while(rs.next()) {
				tranList.add(new Transaction(rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getString(5), rs.getString(6)));
			}
			return tranList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tranList;
		
	}

	public boolean decideTransfer(Transfers trans, String decision) {
		try {
			PreparedStatement stmt = conn.prepareStatement("update tranfsers set state = ? where tid = ?");
			stmt.setString(1, decision);
			stmt.setInt(2, trans.getTid());
			stmt.execute();
			return true;
			}
			catch(SQLException e) {
				e.printStackTrace();
				System.out.println("Failed getting Transfer");
			}
		
		return false;
		
	}
}
