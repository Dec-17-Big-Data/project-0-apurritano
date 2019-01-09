package com.revature.dao;

import java.sql.SQLException;
import java.util.List;
import com.revature.models.User;

public interface UserDao {
	
	//check if username is available
	public User checkUsername(String username);
	
	//create new username and password
	public boolean registerUser(String full_name, String username, String username_password, String user_type);
	
	//SuperUser:
	//check if super user
	public User ifSuper(String username);
	
	//view all users
	public List<User> getAllUsers();
	
	//update users information
	public boolean updateUsers(String user_type, int user_id)throws SQLException;
	
	//delete users
	public boolean deleteUser(int user_id);
	
	//delete all users
	public boolean deleteAllUsers();

}	

