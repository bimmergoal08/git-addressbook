package org.pbhatna.addressbook.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseConnection {
	
	private static final transient Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
	
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String CONNECTION_URL = "jdbc:mysql://localhost/Contacts";
	private static final String CONNECTION_USER = "root";
	private static final String CONNECTION_PASSWORD = "";
	
	public static Connection getConnection() throws Exception {

		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(CONNECTION_URL, CONNECTION_USER, CONNECTION_PASSWORD);
			
		} catch (ClassNotFoundException e) {
			logger.error("JDBC driver class not found :");
			throw new Exception("JDBC driver class not found :" + e.getMessage());
		} catch (SQLException e) {
			logger.error("SQLException :" + e.getMessage());
			throw new Exception("SQLException :", e);
		}
		logger.info("Connection Successful:");
		return conn;
	}
}
