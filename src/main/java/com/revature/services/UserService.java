package com.revature.services;

import java.sql.SQLException;
import java.util.List;


import com.revature.dao.UserDao;
import com.revature.dao.UserOracle;
import com.revature.models.User;

public class UserService {
	private static UserService userService;
	final static UserDao userDao = UserOracle.getDao();
	private static final String String = null;
	private static final int Integer = 0;
	private UserService() {
	}
	
	public static UserService getSerivce() {
		if (userService == null) {
			userService = new UserService();
		}
		return userService;
	}

	public User checkUsername() {
		return userDao.checkUsername(String);
	}
	
	public boolean registerUser(){
		return userDao.registerUser(String, String, String, String);
	}
		
	public User ifSuper() {
		return userDao.ifSuper(String);
	}
	
	public List<User> getAllUsers(){
		return userDao.getAllUsers();
	}
		
	public boolean updateUsers() throws SQLException{
		return userDao.updateUsers(String, Integer);
	}
		
	public boolean deleteUser(){
		return userDao.deleteUser(Integer);
	}
	
	public boolean deleteAllUsers() {
		return userDao.deleteAllUsers();
	}
}
