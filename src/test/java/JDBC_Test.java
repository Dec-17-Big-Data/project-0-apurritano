import org.junit.Test;

import com.revature.dao.AccountOracle;
import com.revature.dao.UserOracle;
import com.revature.dao.AccountDao;
import com.revature.dao.AccountDao;
import com.revature.models.Account;
import com.revature.models.User;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;




public class JDBC_Test {

	UserOracle userOrcl = new UserOracle();
	AccountOracle accountOrcl = new AccountOracle();
	
	User user1 = new User(123, "Austin Purritano", "apurritano", "p4ssw0rd", "super");
	User user2 = new User(456, "Jack Daniels", "jdaniels", "whiskey", "normal");
	User user3 = new User(789, "Captain Morgan", "cmorgan", "rum", "normal");
	User user4 = new User(1, "Jose Cuervo", "jcuervo", "tequila", "normal");
	
	Account a1 = new Account(321, "Checking", 500, 123);
	Account a2 = new Account(213, "Savings", 1500, 123);
	Account a3 = new Account(654, "Checking", 100, 456);
	Account a4 = new Account(546, "Savings", 250, 456);
	Account a5 = new Account(987, "Checking", 100, 789);
	Account a6 = new Account(879, "Savings", 250, 789);
	
	@Test
	public void TestRegisterUser() {
	
		assertEquals(userOrcl.registerUser("Austin Purritano", "apurritano", "p4ssw0rd", "super"), false);
		assertEquals(userOrcl.registerUser("New User", "newUsername", "random", "normal"), true);
		
	}
	
	@Test
	public void TestGetAllUsers() {
		
		List<User> uList = userOrcl.getAllUsers();
		List<User> listOfUsers = new ArrayList<User>(); 
		
		listOfUsers.add(user1);
		listOfUsers.add(user2);
		listOfUsers.add(user3);
		listOfUsers.add(user4);
		
		assertTrue(listOfUsers.get(0).equals(uList.get(0)));
		assertTrue(listOfUsers.get(1).equals(uList.get(1)));
		assertTrue(listOfUsers.get(2).equals(uList.get(2)));
		assertTrue(listOfUsers.get(3).equals(uList.get(3)));
		
	}
	
	
}
