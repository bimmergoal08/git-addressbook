package org.pbhatna.addressbook.dao;

import java.sql.Connection;
import java.sql.DriverManager;
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
		Collection<Contact> contacts = new ArrayList<Contact>();
		
		try {
			conn = DatabaseConnection.getConnection();
			stmt = conn.prepareStatement("select * from Persons");
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
			
		} catch (Exception e){
			logger.error("Error retriveing all contacts :" + e.getMessage());
			throw new Exception("Error retriveing all contacts :"+ e.getMessage());
		} finally {
			closeConnections(rs,stmt, conn);
		}
		logger.info("Contacts retrived Successfully :");
		return contacts;
	}
	
	public Contact addContact(Contact contact) throws Exception {
		
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Contact addedContact = new Contact();
	
		try {
			conn = DatabaseConnection.getConnection();
			stmt = conn.prepareStatement("select * from Persons");
			rs = stmt.executeQuery();
			
		} catch (Exception e){
			logger.error("Error adding contact with the contact id : "+ contact.getContactId() +" e.getMessage()");
			throw new Exception("Error adding contact with the contact id : "+ contact.getContactId() +" e.getMessage()");
		} finally {
			closeConnections(rs,stmt, conn);
		}
		logger.info("Contacts retrived Successfully :");
		return contacts;
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
