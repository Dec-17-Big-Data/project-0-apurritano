package com.revature.dao;

import java.util.List;


import com.revature.models.Account;

public interface AccountDao {
	
	//user views own accounts + balances
	public List<Account> getAccounts(String username);
	
	//create account 
	public boolean createNewAccount(String username, String accountType);
	
	//delete account
	public boolean deleteAccount(int account_id);
	
	//check if account exists
	public boolean checkForAccount(int account_id);

	//withdraw from account balance
	public boolean withdrawFromAccount(int account_id_in, double withdraw_amount_in);
	
	//deposit to account balance
	public boolean depositToAccount(int account_id_in, double deposit_amount_in);

}
