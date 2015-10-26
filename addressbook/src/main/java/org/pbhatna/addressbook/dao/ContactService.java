package org.pbhatna.addressbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pbhatna.addressbook.database.DatabaseConnection;
import org.pbhatna.addressbook.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContactService {
	
	private static final transient Logger logger = LoggerFactory.getLogger(ContactService.class);
	
	public List<Contact> getContacts() {
		
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Contact> contacts = new ArrayList<Contact>();
		int counter = 0;
		try {
			conn = DatabaseConnection.getConnection();
			stmt = conn.prepareStatement("Select * From Persons");
			rs = stmt.executeQuery();
			
			while(rs.next()) {	
				Contact contact = new Contact();
				contact.setContactId(rs.getLong("PersonID"));
				contact.setFirstName(rs.getString("FirstName"));
				contact.setLastName(rs.getString("LastName"));
				contact.setPrimaryAddress(rs.getString("PrimaryAddress"));
				contact.setPrimaryEmailAddress(rs.getString("PrimaryEmail"));
				contact.setPrimaryPhoneNumber(rs.getString("PrimaryPhone"));
				contact.setCity(rs.getString("City"));
				contact.setState(rs.getString("State"));
				contact.setZip(rs.getString("Zip"));
				contact.setCountry(rs.getString("Country"));
				contacts.add(contact);
				counter ++;
			}
			
		} catch (SQLException e){
			logger.error("SQLException retriveing all contacts: " + e.getMessage());
		} catch (Exception e) {
			logger.error("Error retriveing all contacts :"+ e.getMessage());
		}finally {
			closeConnections(rs,stmt, conn);
		}
		logger.debug(counter + " rows processed");
		logger.info("Get contacts results:" + contacts.toString());
		return contacts;
	}

	public List<Contact> searchContacts(String fieldType, String search) {
		
	 	logger.info("searchContacts inputs: "+ "fieldType: "+ fieldType+ " search: "+search);
		
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String baseQuery = null;
		List<Contact> contacts = new ArrayList<Contact>();
		
		int counter = 0;
		try {
			conn = DatabaseConnection.getConnection();
			baseQuery = "SELECT * FROM Persons WHERE p1 LIKE ?";
			String query = baseQuery.replace("p1", fieldType); 
			stmt = conn.prepareStatement(query);
			stmt.setString(1, "%"+search+"%");	
			rs = stmt.executeQuery();
			
			while(rs.next()) {	
				Contact contact = new Contact();
				contact.setContactId(rs.getLong("PersonID"));
				contact.setFirstName(rs.getString("FirstName"));
				contact.setLastName(rs.getString("LastName"));
				contact.setPrimaryAddress(rs.getString("PrimaryAddress"));
				contact.setPrimaryEmailAddress(rs.getString("PrimaryEmail"));
				contact.setPrimaryPhoneNumber(rs.getString("PrimaryPhone"));
				contact.setCity(rs.getString("City"));
				contact.setState(rs.getString("State"));
				contact.setZip(rs.getString("Zip"));
				contact.setCountry(rs.getString("Country"));
				contacts.add(contact);
				counter ++;
			}
		} catch (SQLException e) {
			logger.error("SQLException searching contacts :"+ e.getMessage());
		} catch (Exception e) {
			logger.error("Error searching contacts :"+ e.getMessage());
		} finally {
			closeConnections(rs,stmt, conn);
		}
		logger.info(counter + " rows processed");
		logger.info("Search contacts results:" + contacts.toString());
		return contacts;
	}
	
	public List<Contact> sortContacts(String columnName, String orderBy) {
		
		logger.info("sortContacts inputs :"+ "columnName: "+ columnName + "orderBy: " + orderBy);
		
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String baseQuery = null;
		String query = null;
		List<Contact> contacts = new ArrayList<Contact>();
		
		int counter = 0;
		try {
			conn = DatabaseConnection.getConnection();
			if (orderBy != null) {
				baseQuery = "SELECT * FROM Persons ORDER by p1 q1";
				query = baseQuery.replace("p1", columnName);
				query = query.replace("q1", orderBy);
				logger.info("query"+ query.toString());
			} else {
				baseQuery = "SELECT * FROM Persons ORDER by p1 ASC";
				query = baseQuery.replace("p1", columnName);
			}
			
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
				contact.setCity(rs.getString("City"));
				contact.setState(rs.getString("State"));
				contact.setZip(rs.getString("Zip"));
				contact.setCountry(rs.getString("Country"));
				contacts.add(contact);
				counter ++;
			}
			
		} catch (SQLException e) {
			logger.error("SQLException sorting contacts: "+ e.getMessage());
		} catch (Exception e) {
			logger.error("Error sorting contacts: "+ e.getMessage());
		} finally {
			closeConnections(rs,stmt, conn);
		}
		logger.info(counter + " rows processed");
		logger.info("Sorted contacts results:" + contacts.toString());
		return contacts;
	}
	
	
	public boolean updateContact(Contact contact) {
		
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String query = null;
		boolean updateStatus = false;
		
		try {
			conn = DatabaseConnection.getConnection();
			query = "UPDATE Persons SET FirstName = ?,LastName = ?,"+ 
					"PrimaryAddress = ?,PrimaryEmail = ? ,PrimaryPhone= ?,"+
					"City = ?,State = ?,Zip = ?,Country = ? " +
					"where PersonID = ?";
			
			stmt = conn.prepareStatement(query);
			stmt.setString(1, contact.getFirstName());
			stmt.setString(2, contact.getLastName());
			stmt.setString(3, contact.getPrimaryAddress());
			stmt.setString(4, contact.getPrimaryEmailAddress());
			stmt.setString(5, contact.getPrimaryPhoneNumber());
			stmt.setString(6, contact.getCity());
			stmt.setString(7, contact.getState());
			stmt.setString(8, contact.getZip());
			stmt.setString(9, contact.getCountry());
			stmt.setLong(10, contact.getContactId().longValue());
			
			int count = stmt.executeUpdate();
			if (count > 0) {
				updateStatus = true;
			}
			
		} catch (SQLException e) {
			logger.error("SQLException updating contact :"+ e.getMessage());
		} catch (Exception e) {
			logger.error("Error updating contact :"+ e.getMessage());
		} finally {
			closeConnections(rs,stmt, conn);
		}
		logger.info("updateStatus" + updateStatus);
		return updateStatus;
	}
	
	public boolean addContact(Contact contact) {
			
			PreparedStatement stmt = null;
			Connection conn = null;
			ResultSet rs = null;
			String query = null;
			boolean addStatus = false;
			
			try {
				conn = DatabaseConnection.getConnection();
				query = "INSERT INTO Persons (FirstName, LastName, PrimaryAddress, PrimaryEmail,"
						+ " PrimaryPhone, City, State, Zip, Country)"+
						"VALUES (?,?,?,?,?,?,?,?,?);";
				
				stmt = conn.prepareStatement(query);
				stmt.setString(1, contact.getFirstName());
				stmt.setString(2, contact.getLastName());
				stmt.setString(3, contact.getPrimaryAddress());
				stmt.setString(4, contact.getPrimaryEmailAddress());
				stmt.setString(5, contact.getPrimaryPhoneNumber());
				stmt.setString(6, contact.getCity());
				stmt.setString(7, contact.getState());
				stmt.setString(8, contact.getZip());
				stmt.setString(9, contact.getCountry());
				
				int count = stmt.executeUpdate();
				if (count > 0) {
					addStatus = true;
				} 
			} catch (SQLException e){
				logger.error("SQLException adding contact :"+ e.getMessage());
			} catch (Exception e) {
				logger.error("Error adding contact :"+ e.getMessage());
			} finally {
				closeConnections(rs,stmt, conn);
			}
			logger.info("addStatus:"+ addStatus);
			return addStatus;
	}
	
	public Contact getContact(Long id) {
		
			PreparedStatement stmt = null;
			Connection conn = null;
			ResultSet rs = null;
			Contact contact = null;
			
			try {
				conn = DatabaseConnection.getConnection();
				stmt = conn.prepareStatement("Select * From Persons where PersonID=?");
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
					contact.setCity(rs.getString("City"));
					contact.setState(rs.getString("State"));
					contact.setZip(rs.getString("Zip"));
					contact.setCountry(rs.getString("Country"));
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
	
	public boolean removeContact(Long id) {
		
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		boolean deletedStatus = false;
		
		try {
			conn = DatabaseConnection.getConnection();
			stmt = conn.prepareStatement("delete from Persons where PersonID=?");
			stmt.setLong(1, id.longValue());
				
			int count = stmt.executeUpdate();
			if (count > 0) {
				deletedStatus = true;
			}
		} catch (SQLException e) {
			logger.error("SQLException removing contact id : "+ id + e.getMessage());
		} catch (Exception e) {
			logger.error("Exception removing contact id : "+ id + e.getMessage());
		} finally {
			closeConnections(rs,stmt, conn);
		}
		logger.info("deletedStatus :"+ deletedStatus);
		return deletedStatus;
	}
	
	private static void closeConnections(ResultSet rs, PreparedStatement stmt, Connection conn) {
		
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
