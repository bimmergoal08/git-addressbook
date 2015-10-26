package org.pbhatna.addressbook.resource;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import org.pbhatna.addressbook.dao.ContactService;
import org.pbhatna.addressbook.exception.BadRequestException;
import org.pbhatna.addressbook.exception.DataNotFoundException;
import org.pbhatna.addressbook.model.Contact;
import org.pbhatna.addressbook.model.SuccessMessage;
import org.pbhatna.addressbook.resource.beans.ContactSortBean;
import org.pbhatna.addressbook.util.ContactResourceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Contact resource class, contains all the methods to perform CRUD operation
 * based on the user request. It produces responses in the JSON and XML format depending
 * on what user requested for. Currently valid request and response type is registered as
 * XML and JSON. Could be easily modified to accomodate other format's as well(ex text)
 */

@Path("/contacts")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ContactResource {
	
	private static final transient Logger logger = LoggerFactory.getLogger(ContactResource.class);
	
	@Context
    private UriInfo uriInfo;
	
    ContactService contactService = new ContactService();
	
    /**
     * Get all the contact's if search criteria is not provided. If
     * search criteria is enabled, then it validated the search and search only 
     * contact's that matches the search criteria.
     */
	@GET
	public List<Contact> getContacts() {
		List<Contact> contacts = null;
		
		if (ContactResourceHelper.searchEnabled(uriInfo)) {
			
			ContactResourceHelper.validateSearch(uriInfo);
			contacts = ContactResourceHelper.getSearch(uriInfo);
			
			if (contacts.size() == 0) {
				throw new DataNotFoundException("Contact not found:");
			}
		} else {
			// return all the contacts available in the addressbook if not criteria is specified
			contacts =  contactService.getContacts();
		}
		logger.debug("Get contacts success :");
		return contacts;
	}
	
	/**
     * Get contact with the specific contact id.
     */
	@GET
	@Path("/{contactId}")
	public Response getContact(@PathParam("contactId") long contactId) {
		Contact contact = contactService.getContact(contactId);
		if (contact == null) {
			throw new DataNotFoundException("Contact with contact id "+ contactId +" not found");
		}
		logger.debug("Get contact success :");
		return Response.status(Response.Status.FOUND).entity(contact).build();
	}
	
	/**
     * Sort contact's on the specific column provided by user and order specefied
     * by the user.
     */
	@GET
	@Path("/sort")
	public List<Contact> sortContacts(@BeanParam ContactSortBean contactSortBean) {
		if (!contactSortBean.isValid()) {
			throw new BadRequestException("Invalid sort criteria :");
		}
		List<Contact> contacts = null;
		contacts = contactService.sortContacts(
				ContactResourceHelper.getColumnName(contactSortBean.getSort()),contactSortBean.getOrder());
		if (contacts.size() == 0) {
			throw new DataNotFoundException("Please provide valid input for sorting: ");
		}
		
		logger.debug("Sort contact success :");
		return contacts;
	}
	
	/**
     * Add contact information after validating input information and send appropriate
     * response message and response code.
     */
	@POST
	public Response addContact(Contact contact) {
		if (contact == null) {
			throw new BadRequestException("Invalid contact! Can't be added :");
		}
		boolean addSuccess = true;
		addSuccess = contactService.addContact(contact);	
		if (!addSuccess) {
			throw new BadRequestException("Request failed! Please Make sure request is not duplicate :");
		}
		
		SuccessMessage successMessage = new SuccessMessage(
				Status.CREATED.getStatusCode(), "Contact added successfully :");
		
		logger.debug("Add contact success :");
		return Response.status(Status.CREATED).entity(successMessage).build();
	}
	
	/**
     * Update contact information after validating input information and send appropriate
     * response message and response code.
     */
	@PUT
	@Path("/{contactId}")
	public Response updateContact(@PathParam("contactId")Long contactId, Contact contact) {
		if (contactId == null) {
			throw new BadRequestException("Contact Id can not be null:");
		}
		boolean updateSuccess = true;
		contact.setContactId(contactId.longValue());
		updateSuccess = contactService.updateContact(contact);
		if (!updateSuccess) {
			throw new DataNotFoundException(
					"Contact not found and can not be updated for the contact id: "+ contactId);	
		}
		
		SuccessMessage successMessage = new SuccessMessage(Status.OK.getStatusCode(), "Contact updated successfully :");	
		
		logger.debug("Update contact success :");
		return Response.status(Status.OK).entity(successMessage).build();
	}
	
	/**
     * Remove contact with the specefied contact id if it exists or send back the
     * appropriate response.
     */
	@DELETE
	@Path("/{contactId}")
	public Response removeContact(@PathParam("contactId")Long contactId) {
		if (contactId == null) {
			throw new DataNotFoundException("Contact with contact id "+ contactId + "not found.");
		}
		boolean removeSuccess = true;
		removeSuccess = contactService.removeContact(contactId);
		
		if (!removeSuccess) {
			throw new DataNotFoundException(
					"Contact not found and can not be removed for the contact id: "+ contactId);
		}
		
		logger.debug("Remove contact success :");
		return Response.status(Status.NO_CONTENT.getStatusCode()).build();
	}
}
	