package org.pbhatna.addressbook.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.pbhatna.addressbook.database.DatabaseClass;
import org.pbhatna.addressbook.model.Contact;

public class ContactService {
	
	private Map<Long,Contact> contacts = DatabaseClass.getContacts();
	
	public ContactService() {
		contacts.put(1L, new Contact(1, "Daniel","Schmidt","3095335328", "danielS@gmail.com", "2130 University Ave"));
		contacts.put(2L, new Contact(2, "Alex","Schmidt","3095443456", "alexS@gmail.com", "5430 Highland Ave"));
		contacts.put(3L, new Contact(3, "Henry","Schardt","3096785435", "henryS@gmail.com", "5435 Highland Park"));
		contacts.put(4L, new Contact(4, "Marcus","Popp","3093456543", "marcusS@gmail.com", "5433 Oakland Ave"));
		contacts.put(5L, new Contact(5, "Marcus","Popp","3093456543", "marcusS@gmail.com", "5433 Oakland Ave"));
	}
	
	public List<Contact> getAllContacts() {
		return new ArrayList<Contact>(contacts.values());
	}
	
	public Contact getContact(long contactId) {
		return contacts.get(contactId);
	}
	
	public Contact addContact(Contact contact) {
		contact.setContactId(contacts.size() + 1);
		contacts.put(contact.getContactId(), contact);
		return contact;
	}
	
	public Contact updateContact(Contact contact) {
		if (contact.getContactId() <= 0) {
			return null;
		}
		contacts.put(contact.getContactId(), contact);
		return contact;	
	}
	
	public Contact removeContact(long contactId) {
		return contacts.remove(contactId);
	}
}
