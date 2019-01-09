package com.revature.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.revature.models.Account;
import com.revature.util.ConnectionUtil;

public class AccountOracle implements AccountDao {

	private static AccountOracle accountOracle;
	
	public AccountOracle() {
	}

	public static AccountDao getDao() {
		if (accountOracle == null) {
			accountOracle = new AccountOracle();
		}
		return accountOracle;
	}
	
	//view own account
	public List<Account> getAccounts(String username) {
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			System.out.println("No connection was found");
			return null;
		}
		try {
			String sql = "select * from users where username = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				System.out.println("Username does not match any users");
				return null;	
			}
			else {
				int u_id = rs.getInt(1);
				String sql1 = "select * from accounts where user_id = ? order by account_id";
				con.close();
				con = ConnectionUtil.getConnection();
				
				PreparedStatement ps1 = con.prepareStatement(sql1);
				ps1.setInt(1, u_id);
				ResultSet rs1 = ps1.executeQuery();
				
				if(rs1.next() == false) {
					return null;
				}
				
				ArrayList<Account> listOfAccounts = new ArrayList<Account>();
				do {
					int a_id = rs1.findColumn("account_id");
					int type = rs1.findColumn("account_type");
					int bal = rs1.findColumn("account_balance");
					Account a = new Account(rs1.getInt(a_id), rs1.getString(type), rs1.getDouble(bal), u_id);
					listOfAccounts.add(a);
					
					} while (rs1.next());
					rs.close();
					con.close();
					rs1.close();
					con.close();
				
					System.out.println("AccountID:\tAccount Type:\tAccount Balance:\tUserID:");
					System.out.println("------------------------------------------------------------------------------");
					//System.out.println(listOfAccounts);
					for (Account aL : listOfAccounts) {
						System.out.println(aL.toString());	
					}
					
					return listOfAccounts;
			
			}
		} catch (SQLException e) {
			System.out.println("sql exception");
		}
		return null;
	}
	
	//create new account
	public boolean createNewAccount(String username, String account_type) {
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			return false;
		}
		
		try {
			String sql = "select * from users where username = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				System.out.println("We could not find a user with that username");
				return false;
			} else {
			
			int user_Id = rs.getInt(1);
			
			String sql2 = "insert into accounts (account_id, account_type, account_balance, user_id) values (account_id_sequence.nextval, ?, 0, ?)";
			con.close();
			con = ConnectionUtil.getConnection();
			
			PreparedStatement ps2 = con.prepareStatement(sql2);
			ps2.setString(1, account_type);
			ps2.setInt(2, user_Id);
			ps2.executeQuery();
			
			System.out.println("Account created.");
			return true;
			}
		} catch (SQLException e) {
			
		} 
		return false;	
	}	
	
	//delete own account IF empty 
	public boolean deleteAccount(int account_id) {
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			System.out.println("No connection");
			return false;
		}
		
		String sql = "{CALL DELETE_ACCOUNT (?)}";
		try {
			CallableStatement cs = con.prepareCall(sql);
			cs.setInt(1, account_id);
			cs.execute();
			return true;
		}catch (SQLException e) {
			
		}
		return false;
}

	public boolean checkForAccount(int account_id) {
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			return false;
		}
		
		try {
			String sql = ("select * from accounts where account_id = ?");
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, account_id);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				System.out.println("We were unable to find an account with that account ID");
				return false;
			
			}else {
				return true;
			}
			
		}catch (SQLException e) {
			System.out.println("SQL Exception");
		}
		
		return false;
	}
	
	//withdraw from account
	public boolean withdrawFromAccount(int drawAccount, double withAmountD) {
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			return false;
		}
			
		try {
			CallableStatement cs = null;
			String sql = "{call withdraw_account (?, ?)}";
			cs = con.prepareCall(sql);
			cs.setInt(1, drawAccount);
			cs.setDouble(2, withAmountD);
			cs.execute();
			return true;
		}catch (SQLException e) {
			System.out.println("SQL exception");
		}
		
		return false;
	}

	//deposit into account
	public boolean depositToAccount(int account_id_in, double deposit_amount_in) {
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			return false;
		}
			
		try {
			CallableStatement cs = null;
			String sql = "{call deposit_account (?, ?)}";
			cs = con.prepareCall(sql);
			cs.setInt(1, account_id_in);
			cs.setDouble(2, deposit_amount_in);
			cs.execute();
			return true;
		}catch (SQLException e) {
			
		}
		
		return false;
	}



}
