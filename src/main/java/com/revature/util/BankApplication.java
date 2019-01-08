package com.revature.util;

import java.util.List;
import java.util.Scanner;

import com.revature.dao.AccountDao;
import com.revature.dao.AccountOracle;
import com.revature.dao.UserDao;
import com.revature.dao.UserOracle;
import com.revature.models.Account;
import com.revature.models.User;

public class BankApplication {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		boolean exit = false;
		while (!exit) {
			System.out.println("Welcome to the bank Homepage");
			System.out.println("1. Register 2. Login 3. Exit");
			System.out.println("Please enter the number of the action you would like to make: ");
			String action = input.nextLine();
			
			//Register User
			if (action.equals("1")) {
				System.out.println("You chose register.");
				boolean registered = false;
				boolean cancel = false;
				
				while (!registered && !cancel) {
					System.out.println("Please enter a desired username: ");
					System.out.println("Type 'cancel' to cancel");
					String uName = input.nextLine();
					
					if (uName.equals("cancel")) {
						System.out.println("You chose cancel.");
						System.out.println("Back to main menu.");
						System.out.println("");
						cancel = true;
					} else {
						UserDao method = new UserOracle();
						User futureUser = method.checkUsername(uName);
						if (futureUser != null) {
							System.out.println("That username already exsists, please try again.");
						} else {
							System.out.println("That username is available");
							System.out.println("Please enter a password: ");
							String uPassword = input.nextLine();
							System.out.println("Please enter your full name: ");
							String name = input.nextLine();
							System.out.println("Please enter either 'super' for super user or 'normal' for non-superuser: ");
							System.out.println("Enter cancel to cancel registrating user");
							String uType = input.nextLine();
							if (uType.equals("super")|| uType.equals("normal")) {
								UserDao ud = new UserOracle();
								boolean success = ud.registerUser(name, uName, uPassword, uType);
								
								if (success) {
									System.out.println("User registered");
									registered = true;
								}
							}else if (uType.equals("cancel")) {
								cancel = true;
							}else {
								System.out.println("Invalid user type input.");
								System.out.println("Please try again.");
								System.out.println("");
							}
						}
					}
				}	
			//Login 
			} else if (action.equals("2")) {
				UserDao method = new UserOracle();
				System.out.println("You chose login");
				boolean exitlogin = false;
				while (!exitlogin) {
					System.out.println("Please enter your username: ");
					System.out.println("Type 'cancel' to cancel.");
					String username = input.nextLine();
					if (username.equals("cancel")) {
						System.out.println("canceled.");
						exitlogin = true;
					} else {
						System.out.println("You entered: " + username);
						User checkUser = method.checkUsername(username);
						if (checkUser == null) {
							System.out.println("The username you entered does not match any on file");
							System.out.println("Please try again.");
							System.out.println("");
						} else {
							System.out.println("Please enter your password");
							String password = input.nextLine();
							if (!checkUser.getPassword().equals(password)) {
								System.out.println("This password does not match username");
								System.out.println("Please try again");
								System.out.println("");
							} else {
								AccountDao bankAction = new AccountOracle();
								System.out.println("Welcome " + username);
								while (!exitlogin) {
									System.out.println("1. View accounts 2. Create new account 3. Delete empty account 4. Withdraw from accounts 5. Deposit to accounts 6. Superuser actions 7. Exit");
									System.out.println("Please enter the number of the action you would like to make: ");
									String action2 = input.nextLine();
									
									//View accounts
									if (action2.equals("1")) {
										System.out.println("You chose to view your accounts.");
										List<Account> aL = bankAction.getAccounts(username);
										if (aL == null) {
											System.out.println("You do not have any open accounts");
											System.out.println("Please select another option");
											System.out.println("");
											
										} else {
											System.out.println("");
											System.out.println("Is there something else you would like to do?");
											System.out.println("If you would like to exit, please enter 7.");
											System.out.println("");
										}
										
									//create new account	
									} else if (action2.equals("2")) {
										System.out.println("Great! You want to open a new account.");
										boolean stop = false;
										while (!stop) {
											System.out.println("Please specify either Checking or Savings: ");
											System.out.println("If you would like to cancel, please type cancel");
											String newAccountType = input.nextLine();
											if (newAccountType.equals("cancel")){
												stop = true;
											} else if (newAccountType.equals("Checking")) {
												AccountDao makeAccount = new AccountOracle();
												makeAccount.createNewAccount(username, newAccountType);
												stop = true;
											} else if (newAccountType.equals("Savings")) {
												AccountDao makeAccount = new AccountOracle();
												makeAccount.createNewAccount(username, newAccountType);
												stop = true;
											} else {
												System.out.println("Input error!");
												System.out.println("");
												System.out.println("Make sure to check spelling and capitalization");
												System.out.println("");
											}
										}
										
									//delete empty account	
									} else if (action2.equals("3")) {
										System.out.println("You selected delete account.");
										
										boolean cancel = false;
										while (!cancel) {
										
											System.out.println("To ensure you do not lost any money, we will only close the account if the account balance is $0");
											System.out.println("");
											System.out.println("Please type the account ID of the account you would like to delete: ");
											System.out.println("If you would like to cancel, please type 0");
											String emptyAccount = input.nextLine();
											try {
												int delAccount = Integer.parseInt(emptyAccount);
												if (delAccount == 0) {
													System.out.println("You chose cancel");
													System.out.println("Is there something else you would like to do?");
													System.out.println("");
													cancel = true;
												}
												else {
												AccountDao removeAccount = new AccountOracle();
												boolean removed = removeAccount.deleteAccount(delAccount);
													if (removed) {
														System.out.println("Account deleted.");
														System.out.println("Is there something else you would like to do?");
														System.out.println("Or enter 7 to exit.");
														System.out.println("");
													} else {
														System.out.println("We were unable to delete your account");
														System.out.println("Please try again.");
														System.out.println("");
													}
												}
												} catch (NumberFormatException e){
													System.out.println("Invalid input.");
													System.out.println("Please try again.");
													System.out.println("");
												}	
											}	
									
										
									//withdraw	
									} else if (action2.equals("4")) {
										System.out.println("You chose to withdraw from an account.");
										boolean cancel = false;
										while (!cancel) {
										
										System.out.println("Please enter the account ID of the account you would like to withdraw from");
										System.out.println("If you would like to cancel enter 0");
										String accWithdraw = input.nextLine();
										try {
											int drawAccount = Integer.parseInt(accWithdraw);
											if (drawAccount == 0) {
												System.out.println("You chose cancel");
												System.out.println("Is there something else you would like to do?");
												System.out.println("");
												cancel = true;
											} else {
											AccountDao findAccount = new AccountOracle();
											boolean checkAccount = findAccount.checkForAccount(drawAccount);
											if (checkAccount) {
												System.out.println("Account found.");
												System.out.println("How much would you like to withdraw?");
												String withAmount = input.nextLine();
												double withAmountD = Double.parseDouble(withAmount);
												AccountDao withdraw = new AccountOracle();
												boolean withdrawed = withdraw.withdrawFromAccount(drawAccount, withAmountD);
												if (withdrawed) {
													System.out.println("The given amount was withdrawed from your account.");
												} else {
													System.out.println("We were unable to withdraw the given amount from your account.");
													System.out.println("Please try again.");
													System.out.println("");
												}
											}
										}
										} catch (NumberFormatException e){
											System.out.println("Invalid input.");
											System.out.println("Please try again.");
											System.out.println("");	
										}
									}
										
									//deposit	
									} else if (action2.equals("5")) {
										System.out.println("You chose to deposit to an account.");
										boolean cancel = false;
										while (!cancel) {
										
										System.out.println("Please enter the account ID of the account you would like to deposit into");
										System.out.println("If you would like to cancel please type 0");
										String accDeposit = input.nextLine();
										try {
										int sitAccount = Integer.parseInt(accDeposit);
										if (sitAccount == 0) {
											System.out.println("You chose cancel");
											System.out.println("Is there something else you would like to do?");
											System.out.println("");
											cancel = true;
										}else {
										AccountDao findAccount = new AccountOracle();
										boolean checkAccount = findAccount.checkForAccount(sitAccount);
										if (checkAccount) {
											System.out.println("Account found.");
											System.out.println("How much would you like to deposit?");
											String depositAmount = input.nextLine();
											double depositAmountD = Double.parseDouble(depositAmount);
											AccountDao deposit = new AccountOracle();
											boolean deposited = deposit.depositToAccount(sitAccount, depositAmountD);
											if (deposited) {
												System.out.println("The given amount was deposited into your account.");
											} else {
												System.out.println("We were unable to deposit the given amount into your account.");
												}
											}	
										}
										}catch (NumberFormatException e){
											System.out.println("Invalid input.");
											System.out.println("Please try again.");
											System.out.println("");			
										}	
									
									}	
									//superUser menu	
									} else if (action2.equals("6")) {
										//check if they are a superUser
										System.out.println("You chose superuser actions.");
										System.out.println("First, we need to check if you are a superuser");
										System.out.println("Checking...");
										System.out.println("");
										
										UserDao superCheck = new UserOracle();
										User checkSuper = superCheck.ifSuper(username);
										
										if (checkSuper == null) {
											System.out.println("Sorry you do not have access to these actions.");
											System.out.println("Please choose another option.");
											System.out.println("");
										}else {
										//give them new actions	
										System.out.println("We confirmed that you are a superuser");
										System.out.println("");
										
										boolean supermenu = false;
										while (!supermenu) {
											System.out.println("1. View all users 2. Update users 3. Delete users 4. Exit superuser menu");
											System.out.println("Please enter the number of the action you would like to make: ");
											String superAction = input.nextLine();
											
											//view all users
											if (superAction.equals("1")) {
												System.out.println("You chose to view all users.");
												UserDao getUsers = new UserOracle();
												List<User> uList = getUsers.getAllUsers();
												if (uList == null){
													System.out.println("There are currently no active users");
													System.out.println("Please enter another options");
												}else {
													System.out.println("");
													System.out.println("Is there something else you would like to do?");
													System.out.println("If you would like to exit, please enter 4.");	
												System.out.println("");
											}	
												
											//update users
											}else if (superAction.equals("2")) {
												System.out.println("You chose to update users.");
												//implement updates
												
												
											//delete users	
											}else if (superAction.equals("3")) {
												System.out.println("You chose to delete user(s)");
												
												boolean deletingUser = false;
												while(!deletingUser) {
												System.out.println("Would you like to 1. Delete a user by user ID or 2. Delete all non-superusers 3. Cancel");
												String delUser = input.nextLine();
												if (delUser.equals("1")) {
													System.out.println("You chose to delete by user ID.");
													System.out.println("Please enter the user ID of the user you would like to delete: ");
													String delUserId = input.nextLine();
													int delUserIdInt = Integer.parseInt(delUserId);
													UserDao delete = new UserOracle();
													boolean remove = delete.deleteUser(delUserIdInt);
													if (remove) {
														System.out.println("User deleted.");
														System.out.println("");
														System.out.println("Would you like to 1. Return to main superuser menu or 2. Delete another user");
														String choice = input.nextLine();
														
														if(choice.equals("1")) {
															System.out.println("Back to main superuse menu.");
															System.out.println("");
															deletingUser = true;
													}else if(choice.equals("2")){
															System.out.println("Okay, choose your deleting option again.");
															System.out.println("");
													
													}else {
														System.out.println("Invalid input.");
														System.out.println("Please enter either 1 or 2.");
														System.out.println("Back to deleting option.");
														System.out.println("");
													}
												
												}else if (delUser.equals("2")) {
													System.out.println("You chose to delete all non-superusers.");
													System.out.println("Are you sure you want to complete this?");
													System.out.println("This action cannot be reversed.");
													System.out.println("Enter YES to delete all non-superusers: ");
													System.out.println("Enter NO to cancel: ");
													String important = input.nextLine();
													if (important.equals("YES")) {
														//call function 
													
													}else if (important.equals("NO")) {
														System.out.println("You have canceled this delete option.");
														deletingUser = true;
													}else {
														System.out.println("This was not one of the options.");
														System.out.println("For saftey we have canceled the delete option.");
														deletingUser = true;
													}
												
												}else if(delUser.equals("3")) {
													System.out.println("Cancelled, back to main superuser menu.");
													System.out.println("");
													deletingUser = true;
												}else 
													System.out.println("Please enter either 1, 2, or 3: ");
													System.out.println("");
												}
												
											}
												
											//exit	
											}else if(superAction.equals("4")) {
												System.out.println("You chose exit superuser menu.");
												System.out.println("Is there anything else you would like to do?");
												System.out.println("If you would like to exit type 7.");
												System.out.println("");
												supermenu = true;
											}else {
												System.out.println("Invalid input.");
												System.out.println("Please enter a whole number between 1 and 4.");
												System.out.println("");
											}
										}
									}
										
									} else if (action2.equals("7")) {
										System.out.println("Thank you, returned to homepage.");
										System.out.println("");
										exitlogin = true;
									} else {
										System.out.println("Invalid Input");
										System.out.println("Please enter an interger from 1 to 7");
										System.out.println("");
										exitlogin = false;
									}
								}
							}	
						} 
					}
				}
				
			} else if (action.equals("3")) {
				System.out.println("You chose exit.");
				System.out.println("Thank you, goodbye.");
				exit = true;
				
			} else {
				System.out.println("Please enter either 1, 2, or 3");
				System.out.println("");
			}
		}
	input.close();
	}
}
