package org.pbhatna.addressbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.pbhatna.addressbook.database.DatabaseConnection;
import org.pbhatna.addressbook.model.Contact;
import org.pbhatna.addressbook.util.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContactService {
	
	private static final transient Logger logger = LoggerFactory.getLogger(ContactService.class);
	
	public Collection<Contact> getContacts() {
		
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String query = null;
		Collection<Contact> contacts = new ArrayList<Contact>();
		
		int counter = 0;
		try {
			conn = DatabaseConnection.getConnection();
			query = "Select * From Persons";
			
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
				counter ++;
			}
			
		} catch (SQLException e){
			logger.error("Error retriveing all contacts :" + e.getMessage());
		} catch (Exception e) {
			logger.error("Error retriveing all contacts :"+ e.getMessage());
		}finally {
			closeConnections(rs,stmt, conn);
		}
		logger.debug(counter + " rows processed");
		logger.info("All contacts retrived successfully :");
		return contacts;
	}
	
	public Collection<Contact> searchContacts(String fieldType, String search) {
		logger.info("fieldType "+ fieldType+ " search "+ search);
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String query = null;
		Collection<Contact> contacts = new ArrayList<Contact>();
		
		int counter = 0;
		try {
			conn = DatabaseConnection.getConnection();
			query = "SELECT * FROM Persons WHERE ? LIKE '%?%'";
			
			stmt = conn.prepareStatement(query);
			stmt.setString(1, fieldType);
			stmt.setString(1, search);
			

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
				counter ++;
			}
			
		} catch (SQLException e) {
			logger.error("SQLException with fieldtype and search criteria :"+ e.getMessage());
		} catch (Exception e) {
			logger.error("Error retriveing contacts with fieldtype and search criteria: "+ e.getMessage());
		} finally {
			closeConnections(rs,stmt, conn);
		}
		logger.info(counter + " rows processed");
		logger.info("Retrieved Contacts with fieldtype :"+ fieldType +" and search criteria :"+ search);
		return contacts;
	}
	
	public boolean updateContact(Contact contact) throws Exception {
		
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String query = null;
		boolean updateStatus = false;
		
		try {
			conn = DatabaseConnection.getConnection();
			query = "UPDATE Persons SET FirstName = ?,LastName = ?,"+ 
					"PrimaryAddress = ?,PrimaryEmail = ? ,PrimaryPhone= ? where PersonID = ?";
			
			stmt = conn.prepareStatement(query);
			stmt.setString(1, contact.getFirstName());
			stmt.setString(2, contact.getLastName());
			stmt.setString(3, contact.getPrimaryAddress());
			stmt.setString(4, contact.getPrimaryEmailAddress());
			stmt.setString(5, contact.getPrimaryPhoneNumber());
			stmt.setLong(6, contact.getContactId().longValue());
			
			int count = stmt.executeUpdate();
			if (count > 0) {
				updateStatus = true;
			}
			
		} catch (SQLException e) {
			logger.error("Error updating contact :"+ e.getMessage());
			throw new Exception("Error updating contact :"+ e.getMessage());
		} finally {
			closeConnections(rs,stmt, conn);
		}
		logger.info("Contact updated successfully:");
		return updateStatus;
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
				int count = stmt.executeUpdate();
				if (count > 0) {
					addStatus = true;
				} 
			} catch (SQLException e){
				logger.error("Error adding contact :"+ e.getMessage());
				throw new Exception("Error adding contact :"+ e.getMessage());
			} finally {
				closeConnections(rs,stmt, conn);
			}
			logger.info("Contact inserted successfully:");
			return addStatus;
	}
	
	public Contact getContact(Long id) {
		
			PreparedStatement stmt = null;
			Connection conn = null;
			ResultSet rs = null;
			String query = null;
			Contact contact = null;
			
			try {
				conn = DatabaseConnection.getConnection();
				query = "Select * From Persons where PersonID=?";
				stmt = conn.prepareStatement(query);
				stmt.setLong(1, id.longValue());
				rs = stmt.executeQuery();
				
				if (rs.next()) {
					contact = new Contact();
					contact.setContactId(rs.getLong("PersonID"));
					contact.setFirstName(rs.getString("FirstName"));
					contact.setLastName(rs.getString("LastName"));
					contact.setPrimaryAddress(rs.getString("PrimaryAddress"));
					contact.setPrimaryEmailAddress(rs.getString("PrimaryEmail"));
					contact.setPrimaryPhoneNumber(rs.getString("PrimaryPhone"));
				} 
			} catch (SQLException e) {
				logger.error("SQLException :"+ id + e.getMessage());
			} catch (Exception e) {
				logger.error("Error getting contact id"+ id + e.getMessage());
			} finally {
				closeConnections(rs,stmt, conn);
			}
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
			query = "delete from Persons where PersonID=?";
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
