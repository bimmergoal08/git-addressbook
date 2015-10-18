package org.pbhatna.addressbook.resource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.pbhatna.addressbook.model.Contact;
import org.pbhatna.addressbook.resource.beans.ContactFilterBean;
import org.pbhatna.addressbook.service.ContactService;
import org.pbhatna.addressbook.util.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/contacts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContactResource {
	
	private static final Logger logger = LoggerFactory.getLogger(ContactResource.class);

	
	ContactService contactService = new ContactService();
	
//	@GET
//	public List<Contact> getContacts(@BeanParam ContactFilterBean contactFilterBean) {
//		
//		logger.info("logger works!!!!!!!!!!!");
//		
//		// if search criteria is enabled, then narrow down the search
//		if (contactFilterBean.searchCriteriaEnabled()) {
//			System.out.println("yes");
//			return matchedContacts(contactFilterBean);
//		} else {
//			return contactService.getAllContacts();
//		}
//	}
	
	@GET
	public Collection<Contact> getContacts(@BeanParam ContactFilterBean contactFilterBean) throws Exception {
		
		if (contactFilterBean.searchCriteriaEnabled()) {
		 
		} else {
			
		}
		PreparedStatement stmt = null;
		Collection<Contact> contacts = new ArrayList<Contact>();
		Connection conn = null;
		ResultSet rs = null;
		logger.info("1");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			logger.info("2");
			String connectionUrl = "jdbc:mysql://localhost/Contacts";
			String connectionUser = "root";
			String connectionPassword = "";
		
			logger.info("3");
			conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
			
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
			
			rs.close();
			stmt.close();
			conn.close();
			logger.info("4");
			System.out.println("Yay It works");
			
		} catch (ClassNotFoundException classNotFound) {
			System.out.println("JDBC driver class not found.");
			throw new Exception("JDBC driver class not found.", classNotFound);
		} catch (SQLException e) {
			System.out.println("SQLException.");
			throw new Exception("SQLException", e);
		} finally {
			logger.info("5");
			closeConnections(rs,stmt, conn);
		}
		return contacts;
	}
	
	
	@POST
	public Contact addContact(Contact contact) {
		return contactService.addContact(contact);
	}
	
	@PUT
	@Path("/{contactId}")
	public Contact addContact(@PathParam("contactId")long contactId, Contact contact) {
		contact.setContactId(contactId);
		return contactService.updateContact(contact);
	}
	
	@DELETE
	@Path("/{contactId}")
	public Contact removeContact(@PathParam("contactId")long contactId) {
		return contactService.removeContact(contactId);
	}
	
	@GET
	@Path("/{contactId}")
	public Contact getContact(@PathParam("contactId")long contactId) {
		return contactService.getContact(contactId);
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
	
//	private List<Contact> matchedContacts(ContactFilterBean contactFilterBean) {
//		if (contactFilterBean.firstNameValid()) {
//			System.out.print("1");
//			return contactService.searchContact(Search.FIRST_NAME.getValue(), contactFilterBean.getFirstName());
//		} else if (contactFilterBean.lastNameValid()) {
//			System.out.print("2");
//			return contactService.searchContact(Search.LAST_NAME.getValue(), contactFilterBean.getLastName());
//		} else if (contactFilterBean.emailValid()) {
//			System.out.print("3");
//			return contactService.searchContact(Search.EMAIL_ADDRESS.getValue(), contactFilterBean.getEmail());
//		} else if (contactFilterBean.addressValid()) {
//			System.out.print("4");
//			return contactService.searchContact(Search.ADDRESS.getValue(), contactFilterBean.getAddress());
//		} else if (contactFilterBean.phoneValid()) {
//			System.out.print("5");
//			return contactService.searchContact(Search.PHONE_NUMBER.getValue(), contactFilterBean.getPhone());
//		} else {
//			return new ArrayList<Contact>();
//		}
//	}

	
}
