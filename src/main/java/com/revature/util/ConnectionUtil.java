package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

	public class ConnectionUtil {
		//this class will be a singleton
		
		private static Connection connectionInstance = null;
		//private static final Logger Log = LogManager.getLogger(ConnectionUtil.class);
		
		private ConnectionUtil() {}
		
		public static Connection getConnection() {
			if (ConnectionUtil.connectionInstance != null) {
				return ConnectionUtil.connectionInstance;
			}
			
			InputStream in = null;
			
			try {
				//load info from properties files
				Properties props = new Properties();
				in = new FileInputStream("C:\\Users\\apurr\\Documents\\dev\\Revature\\workspace-spring-tool-suite-4-4.0.2.RELEASE\\Project_0\\src\\main\\resources\\connection.properties");
				props.load(in);
				
				//get the connection object
				Class.forName("oracle.jdbc.driver.OracleDriver"); //example of reflection
				Connection con = null;
				
				String endpoint = props.getProperty("jdbc.url");
				String username = props.getProperty("jdbc.username");
				String password = props.getProperty("jdbc.password");
				
				con = DriverManager.getConnection(endpoint, username, password);
				//connectionInstance = con;
				return con;
				
			} catch (Exception e) {
				
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					
				}
			}
			return null;
		}
	}
