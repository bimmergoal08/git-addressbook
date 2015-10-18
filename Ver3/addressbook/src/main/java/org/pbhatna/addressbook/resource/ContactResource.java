package org.pbhatna.addressbook.resource;

import java.util.ArrayList;
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


@Path("/contacts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContactResource {
	
	ContactService contactService = new ContactService();
	
	@GET
	public List<Contact> getContacts(@BeanParam ContactFilterBean contactFilterBean) {
		
		// if search criteria is enabled, then narrow down the search
		if (contactFilterBean.searchCriteriaEnabled()) {
			System.out.println("yes");
			return matchedContacts(contactFilterBean);
		} else {
			return contactService.getAllContacts();
		}
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
	
	private List<Contact> matchedContacts(ContactFilterBean contactFilterBean) {
		if (contactFilterBean.firstNameValid()) {
			System.out.print("1");
			return contactService.searchContact(Search.FIRST_NAME.getValue(), contactFilterBean.getFirstName());
		} else if (contactFilterBean.lastNameValid()) {
			System.out.print("2");
			return contactService.searchContact(Search.LAST_NAME.getValue(), contactFilterBean.getLastName());
		} else if (contactFilterBean.emailValid()) {
			System.out.print("3");
			return contactService.searchContact(Search.EMAIL_ADDRESS.getValue(), contactFilterBean.getEmail());
		} else if (contactFilterBean.addressValid()) {
			System.out.print("4");
			return contactService.searchContact(Search.ADDRESS.getValue(), contactFilterBean.getAddress());
		} else if (contactFilterBean.phoneValid()) {
			System.out.print("5");
			return contactService.searchContact(Search.PHONE_NUMBER.getValue(), contactFilterBean.getPhone());
		} else {
			return new ArrayList<Contact>();
		}
	}
		
}
