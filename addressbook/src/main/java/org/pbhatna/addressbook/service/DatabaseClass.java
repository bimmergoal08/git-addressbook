package org.pbhatna.addressbook.service;

import java.util.HashMap;
import java.util.Map;

import org.pbhatna.addressbook.model.Contact;

public class DatabaseClass {
	
	private static Map<Long, Contact> contacts = new HashMap<Long, Contact>();
	
	public static Map<Long, Contact> getContacts() {
		return contacts;
	}

}
