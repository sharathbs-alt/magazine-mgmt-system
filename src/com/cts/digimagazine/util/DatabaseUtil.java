package com.cts.digimagazine.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

	private static final String DRIVER_PATH = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/digimagazine?useSSL=false"; 
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	
	public DatabaseUtil() {
		try {
			//Load and Register Driver
			Class.forName(DRIVER_PATH);
		}
		catch(Exception e) {
			throw new RuntimeException("Something went wrong." + e);
		}
	}
	
	public Connection getConnection() throws SQLException{
		return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	}
	
}
