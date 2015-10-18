package org.pbhatna.addressbook.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
	
	public static String getDateTime() throws Exception {
		
		PreparedStatement stmt = null;
		String myString = null;
		String returnString = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String connectionUrl = "jdbc:mysql://localhost";
			String connectionUser = "root";
			String connectionPassword = "";
				
			conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
			
			stmt = conn.prepareStatement("SELECT NOW()");
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				myString = rs.getString("NOW()");
				System.out.println("myString"+myString);
			}
			
			returnString = "<p> Database Status :</p>" + "<p>"+ myString + "</p>";
			
			rs.close();
			stmt.close();
			conn.close();
			
			System.out.println("Yay It works");
			
		} catch (ClassNotFoundException classNotFound) {
			System.out.println("JDBC driver class not found.");
			throw new Exception("JDBC driver class not found.", classNotFound);
		} catch (SQLException e) {
			System.out.println("SQLException.");
			throw new Exception("SQLException", e);
		} finally {
			closeConnections(rs,stmt, conn);
		}
		System.out.println("returnString"+returnString);
		return returnString;
	}
	
	private static void closeConnections(
			ResultSet rs, 
			PreparedStatement stmt, 
			Connection conn
	) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String args[]) {
		try {
			getDateTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}