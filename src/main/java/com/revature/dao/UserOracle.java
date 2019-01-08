package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class UserOracle implements UserDao {
	private static UserOracle userOracle;
	public UserOracle(){}
	public static UserDao getDao() {
		if (userOracle == null) {
			userOracle = new UserOracle();
		}
		return userOracle;
	}
	
	
	//check if username exists
	public User checkUsername(String username) {
		Connection con = ConnectionUtil.getConnection();
		if (con == null) {
			return null;
		}
		
		try {
			String sql = "select * from users where users.username = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next() == false) {
				//System.out.println("result set returning false");
				return null;
			}
			else {
				//System.out.println("attempting to get password");
				int user_password = rs.findColumn("username_password");
				return new User(username, rs.getString(user_password));
			}
			
		}catch (SQLException e) {
			System.out.println("sql exception");
		}
		System.out.println("try block failing");
		return null;
	}
	
	//register new user
	public boolean registerUser(String full_name, String username, String username_password, String user_type) {
		Connection con = ConnectionUtil.getConnection();
		if (con == null) {
			System.out.println("no connection");
			return false;
		}
		
		
		try {
			String sql = "insert into users (user_id, full_name, username, username_password, user_type) values (user_id_sequence.nextval, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			//ps.executeQuery();
			
			
			ps.setString(1, full_name);
			ps.setString(2, username);
			ps.setString(3, username_password);
			ps.setString(4, user_type);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("sql exception");
		}
		return false;
	}
	
	//SUPER USER:
	
	//check for super keyword
	public User ifSuper(String username) {
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			return null;
		}
		
		try {
			String sql = "select user_type from users where username = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()){
				String user_type = rs.getString("user_type");

				if (user_type.equals("super")){
					return new User(user_type);
				}else {
					return null;
				}
			}			
		
		}catch (SQLException e) {
			System.out.println("sql exeption");
		}

		return null;
	}
	
	
	//view all users
	public List<User> getAllUsers() {
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			return null;
		}
		
		try {
			String sql = "select * from users order by user_id";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			List<User> listOfUsers = new ArrayList<User>();
			
			while (rs.next()) {
				listOfUsers.add(new User(rs.getInt("user_id"),
						rs.getString("full_name"),
						rs.getString("username"),
						rs.getString("username_password"),
						rs.getString("user_type")));
			}
			System.out.println("Here is a list of all active users: ");
			System.out.println("---------------------------------------------------------------------");
			System.out.println("UserID:\tFull Name:\tUsername:\tPassword:\tUser Type:");
			for (User uL : listOfUsers) {
				System.out.println(uL.toString());	
			}
			
			return (listOfUsers);
		} catch (SQLException e) {
			System.out.println("sql exception");
		}
		return null;
	}
	
	//update users
	public boolean updateUsers(int user_id, String username) throws SQLException {
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			return false;
		}
		else {
			String sql = "select * from users where user_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ps.setInt(1, user_id);
			
			if(rs.next() == false) {
				return false;
			}
			else {
				String sql1 = "update users set username = ? where user_id = ?";
				PreparedStatement ps1 = con.prepareStatement(sql1);
				ps1.executeQuery();
				ps1.setString(1, username);
				ps1.setInt(2, user_id);
			}
			return true;
		}
	}
	
	//delete users
	public boolean deleteUser(int user_id) {
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			return false;
		}
		
		try {
			String sql = "delete from users where user_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, user_id);
			ps.executeQuery();
			return true;
		
		}catch (SQLException e) {
			System.out.println("SQL exception");
		} 
		return false;
	}
	
	public boolean deleteAllUsers() {
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			return false;
		}
		
		try {
			String sql = "delete from users where user_type = normal";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.executeQuery();
			return true;
		} catch (SQLException e) {
			System.out.println("SQL Exception");
		}
	
	return false;
	}

}
