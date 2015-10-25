package org.pbhatna.addressbook.resource;

import java.util.Collection;
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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.pbhatna.addressbook.dao.ContactService;
import org.pbhatna.addressbook.exception.BadRequestException;
import org.pbhatna.addressbook.exception.DataNotFoundException;
import org.pbhatna.addressbook.model.Contact;
import org.pbhatna.addressbook.model.SuccessMessage;
import org.pbhatna.addressbook.resource.beans.ContactSortBean;
import org.pbhatna.addressbook.util.InputParameter;
import org.pbhatna.addressbook.util.ResourceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/contacts")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ContactResource {
	
	private static final transient Logger logger = LoggerFactory.getLogger(ContactResource.class);
	
	@Context
    private UriInfo uriInfo;
	
    ContactService contactService = new ContactService();
	
	@GET
	public List<Contact> getContacts() {
		List<Contact> contacts = null;
	
		MultivaluedMap<String, String> queryParameterMap = uriInfo.getQueryParameters();
		
		if (!queryParameterMap.keySet().isEmpty()) { 
			if (!ResourceHelper.searchValid(queryParameterMap)) {
				throw new BadRequestException("In correct searching criteria:");
			}
			
			if (!ResourceHelper.inputValid(queryParameterMap)) {
				throw new BadRequestException("Input value must be string: ");
			}
			
			for (InputParameter input : InputParameter.values()) {
				for (String key : queryParameterMap.keySet()) {
					if (input.getValue().contains(queryParameterMap.getFirst(key))) {
						contacts =  contactService.searchContacts(
								ResourceHelper.getColumnName(key),
								queryParameterMap.getFirst(key));
					}
				}	
	 		}
			
			if (contacts.size() == 0) {
				throw new DataNotFoundException("Contact not found:");
			}
		} else {
			// return all the contacts available in the addressbook if not criteria is specified
			contacts =  contactService.getContacts();
		}
		return contacts;
	}
	
	@GET
	@Path("/{contactId}")
	public Response getContact(@PathParam("contactId") long contactId) {
		Contact contact = contactService.getContact(contactId);
		if (contact == null) {
			throw new DataNotFoundException("Contact with contact id "+ contactId +" not found");
		}
		return Response.status(Response.Status.FOUND).entity(contact).build();
	}
	
	@GET
	@Path("/sort")
	public Collection<Contact> sortContacts(@BeanParam ContactSortBean contactSortBean) {
		if (!contactSortBean.isValid()) {
			throw new BadRequestException("Invalid sort criteria for :" + contactSortBean.getSort() + 
					" and orderby :" + contactSortBean.getOrder());
		}
		return contactService.sortContacts(contactSortBean.getSort(), contactSortBean.getOrder());
	}
	
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
		return Response.status(Status.CREATED).entity(successMessage).build();
	}
	
	@PUT
	@Path("/{contactId}")
	public Response updateContact(@PathParam("contactId")Long contactId, Contact contact) {
		// check for null contact id
		if (contactId == null) {
			throw new BadRequestException("Contact can not be updated for the contact id: "+ contactId);
		}		
		contact.setContactId(contactId.longValue());
		contactService.updateContact(contact);
		SuccessMessage successMessage = new SuccessMessage(
				Status.OK.getStatusCode(), "Contact updated successfully :");	
		return Response.status(Status.OK).entity(successMessage).build();
	}
		
	@DELETE
	@Path("/{contactId}")
	public Response removeContact(@PathParam("contactId")Long contactId) {
		if (contactId == null) {
			throw new DataNotFoundException("Contact with contact id "+ contactId + "not found.");
		}
		contactService.removeContact(contactId);
		SuccessMessage successMessage = new SuccessMessage(
				Status.NO_CONTENT.getStatusCode(), "Contact deleted successfully :");	
		return Response.status(Status.NO_CONTENT).entity(successMessage).build();
	}
}
	