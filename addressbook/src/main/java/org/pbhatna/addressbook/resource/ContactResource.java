package org.pbhatna.addressbook.resource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.pbhatna.addressbook.database.DatabaseConnection;
import org.pbhatna.addressbook.model.Contact;
import org.pbhatna.addressbook.service.ContactService;


@Path("/contacts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContactResource {
	
	ContactService contactService = new ContactService();
	
	@GET
	public List<Contact> getContacts() {
		return contactService.getAllContacts();
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
	
//	@GET
//	@Path("/database")
//	@Produces(MediaType.TEXT_HTML)
//	public String getConnectionStatus() {
//		String str = "";
//		try {
//			
//			str = DatabaseConnection.getDateTime();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return str;
//	}
		
		
}
