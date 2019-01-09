package com.revature.services;

import java.util.List;


import com.revature.dao.AccountDao;
import com.revature.dao.AccountOracle;
import com.revature.models.Account;

public class AccountService {
	private static AccountService accountService;
	final static AccountDao accountDao = AccountOracle.getDao() ;
	private static final int Integer = 0;
	private static final String String = null;
	private static final Float Float = null;
	private AccountService() {
	}
	
	public static AccountService getSerivce() {
		if (accountService == null) {
			accountService = new AccountService();
		}
		return accountService;
	}
	
	public List<Account> getAccounts(){
		return accountDao.getAccounts(String);
	}
	
	public boolean createNewAccount(){
		return accountDao.createNewAccount(String, String);
	}
	
	public boolean deleteAccount(){
		return accountDao.deleteAccount(Integer);
	}
	
	public boolean checkForAccount() {
		return accountDao.checkForAccount(Integer);
	}
	
	public boolean withdrawFromAccount(){
		return accountDao.withdrawFromAccount(Integer, Float);
	}
	
	public boolean depositToAccount(){
		return accountDao.depositToAccount(Integer, Float);
	}
}
