package org.pbhatna.addressbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.pbhatna.addressbook.database.DatabaseConnection;
import org.pbhatna.addressbook.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContactService {
	
	private static final transient Logger logger = LoggerFactory.getLogger(ContactService.class);
	
	public Collection<Contact> getContacts() throws Exception {
		
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String query = null;
		Collection<Contact> contacts = new ArrayList<Contact>();
		
		try {
			conn = DatabaseConnection.getConnection();
			query = "Select PersonID, FirstName, LastName, PrimaryAddress, PrimaryEmail, PrimaryPhone "+
					"From Persons";
			
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			
			while(rs.next()) {	
				Contact contact = new Contact();
				contact.setContactId(rs.getLong("PersonID"));
				contact.setFirstName(rs.getString("FirstName"));
				contact.setLastName(rs.getString("LastName"));
				contact.setPrimaryAddress(rs.getString("PrimaryAddress"));
				contact.setPrimaryEmailAddress(rs.getString("PrimaryEmail"));
				contact.setPrimaryPhoneNumber(rs.getString("PrimaryPhone"));
				contacts.add(contact);
			}
			
		} catch (SQLException e){
			logger.error("Error retriveing all contacts :" + e.getMessage());
			throw new Exception("Error retriveing all contacts :"+ e.getMessage());
		} finally {
			closeConnections(rs,stmt, conn);
		}
		logger.info("All contacts retrived successfully :");
		return contacts;
	}
	
	public boolean addContact(Contact contact) throws Exception {
		
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String query = null;
		boolean addStatus = false;
		
		try {
			conn = DatabaseConnection.getConnection();
			query = "INSERT INTO Persons (FirstName, LastName, PrimaryAddress, PrimaryEmail, PrimaryPhone)"+
					"VALUES (?,?,?,?,?);";
			
			stmt = conn.prepareStatement(query);
			stmt.setString(1, contact.getFirstName());
			stmt.setString(2, contact.getLastName());
			stmt.setString(3, contact.getPrimaryAddress());
			stmt.setString(4, contact.getPrimaryEmailAddress());
			stmt.setString(5, contact.getPrimaryPhoneNumber());
			stmt.executeUpdate();
//			if (count > 0) {
//				addStatus = true;
//			} 
		} catch (SQLException e){
			logger.error("Error adding contact :"+ e.getMessage());
			throw new Exception("Error adding contact :"+ e.getMessage());
		} finally {
			closeConnections(rs,stmt, conn);
		}
		logger.info("Contact inserted successfully:");
		return addStatus;
	}
	
	public Contact getContact(Long id) throws Exception {
		
			PreparedStatement stmt = null;
			Connection conn = null;
			ResultSet rs = null;
			String query = null;
			Contact contact = new Contact();
			
			try {
				conn = DatabaseConnection.getConnection();
				query = "select * from Persons where PersonID = ? ";
				stmt = conn.prepareStatement(query);
				stmt.setString(1, id.toString());
				rs = stmt.executeQuery();
				
				if (rs.next()) {
					contact.setContactId(rs.getLong("PersonID"));
					contact.setFirstName(rs.getString("FirstName"));
					contact.setLastName(rs.getString("LastName"));
					contact.setPrimaryAddress(rs.getString("PrimaryAddress"));
					contact.setPrimaryEmailAddress(rs.getString("PrimaryEmail"));
					contact.setPrimaryPhoneNumber(rs.getString("PrimaryPhone"));
				} else {
					throw new Exception("Contact Not found!!");
				}
			} catch (SQLException e) {
				logger.error("Error getting contact id"+ id + e.getMessage());
				throw new Exception("Error getting contact id"+ id + e.getMessage());
			} finally {
				closeConnections(rs,stmt, conn);
			}
			logger.info("Contacts retrived successfully with the contact id :"+ id.toString());
			return contact;
		}
	
	public boolean removeContact(Long id) throws Exception {
		
		PreparedStatement stmt = null;
		Connection conn = null;
		String query = null;
		ResultSet rs = null;
		boolean deletedStatus = false;
		
		try {
			conn = DatabaseConnection.getConnection();
			query = "delete from Persons where PersonID = ? ";
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, id.longValue());
				
			if (stmt.execute()) {	
				deletedStatus = true;	
				logger.info("Contacts removed successfully with the contact id :"+ id.toString());
			}
			
		} catch (SQLException e) {
			logger.error("Error removing contact id : "+ id + e.getMessage());
			throw new Exception("Error removing contact id"+ id + e.getMessage());
		} finally {
			closeConnections(rs,stmt, conn);
		}
		return deletedStatus;
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
				logger.error("Error closing result set :" + e.getMessage());
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error("Error closing prepared statement :" + e.getMessage());
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("Error closing connection :" + e.getMessage());
			}
		}
		logger.debug("Connection closed :");
	}
}
