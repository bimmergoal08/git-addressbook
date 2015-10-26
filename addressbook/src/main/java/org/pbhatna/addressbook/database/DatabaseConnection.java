package org.pbhatna.addressbook.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible for establishing database connection to the Mysql database
 * with the credential's provided.
 */
public class DatabaseConnection {
	
	private static final transient Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
	
	// Driver information
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String CONNECTION_URL = "jdbc:mysql://localhost/addressbook_db";
	
	// Connection credentials
	private static final String CONNECTION_USER = "root";
	private static final String CONNECTION_PASSWORD = "";
	
	/**
	 * This method's gets connection object providing connection url,
	 * username and password for the mysql database. Logs error if connection
	 * is not successful.
	 */
	public static Connection getConnection() throws Exception {

		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(CONNECTION_URL, CONNECTION_USER, CONNECTION_PASSWORD);
		} catch (ClassNotFoundException e) {
			logger.error("JDBC driver class not found :");
			throw new Exception("JDBC driver class not found :" + e.getMessage());
		} catch (SQLException e) {
			logger.error("SQLException connecting to the database :" + e.getMessage());
			throw new Exception("SQLException connecting to the database :", e);
		}
		return conn;
	}
}
