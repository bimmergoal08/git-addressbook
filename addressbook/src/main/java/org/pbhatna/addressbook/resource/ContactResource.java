package org.pbhatna.addressbook.resource;

import java.util.Collection;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.pbhatna.addressbook.dao.ContactService;
import org.pbhatna.addressbook.model.Contact;
import org.pbhatna.addressbook.resource.beans.ContactFilterBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/contacts")
//To do : Check header type and format your response accordingly
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContactResource {
	
	private static final transient Logger logger = LoggerFactory.getLogger(ContactResource.class);
	
	ContactService contactService = new ContactService();
	
	@GET
	public Collection<Contact> getContacts(@BeanParam ContactFilterBean contactFilterBean) throws Exception {		
		if (contactFilterBean.searchCriteriaEnabled()) {
		
		} 
		return contactService.getContacts();
	}
	
	@GET
	@Path("/{contactId}")
	public Contact getContact(@PathParam("contactId")Long contactId) throws Exception {
		if (contactId == null) {
			throw new BadRequestException();
		}
		return contactService.getContact(contactId);
	}
	
	@POST
	public boolean addContact(Contact contact) throws Exception {
		logger.info(contact.toString());
		
		return contactService.addContact(contact);
	}
	
	@PUT
	@Path("/{contactId}")
	public Contact updateContact(@PathParam("contactId")Long contactId, Contact contact) {
		// check for null contact id
		if (contactId == null) {
			throw new BadRequestException();
		}
		// Check if id is valid
		
		contact.setContactId(contactId.longValue());
		//return contactService.updateContact(contact);
		return contact;
	}
		
	@DELETE
	@Path("/{contactId}")
	public boolean removeContact(@PathParam("contactId")Long contactId) throws Exception {
		if(contactId == null){
            throw new NotFoundException();
        }
		return contactService.removeContact(contactId);
	}
}
	