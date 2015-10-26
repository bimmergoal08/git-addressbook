package org.pbhatna.addressbook.util;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.pbhatna.addressbook.dao.ContactService;
import org.pbhatna.addressbook.exception.BadRequestException;
import org.pbhatna.addressbook.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContactResourceHelper {
	
	private static final transient Logger logger = LoggerFactory.getLogger(ContactResourceHelper.class);
	
	public static String getColumnName(String fieldType) {
		String column = "";
		
		switch(fieldType) {
			case "firstname": 
				column = Column.FIRST_NAME.getValue(); 
				break;
			case "lastname": 
				column = Column.LAST_NAME.getValue();
				break;
			case "email": 
				column = Column.EMAIL_ADDRESS.getValue(); 
				break;
			case "address": 
				column = Column.ADDRESS.getValue();
				break;
			case "phone": 
				column = Column.PHONE_NUMBER.getValue(); 
				break;
		}
		return column;
	}
	
	public static boolean searchCriteriaValid(UriInfo uriInfo) {
		boolean valid = false;
		
		MultivaluedMap<String, String> paramMap = uriInfo.getQueryParameters();
		
		for (Search search: Search.values()) {
			if (paramMap.containsKey(search.getValue())) {
				logger.info("valid" + search);
				valid = true;
			}
		}
		return valid;
	}
	
	public static boolean searchEnabled(UriInfo uriInfo) {
		return !uriInfo.getQueryParameters().isEmpty();
	}
	
	public static void validateSearch(UriInfo uriInfo) {
		if (!searchCriteriaValid(uriInfo)) {
			throw new BadRequestException("Incorrect searching criteria:");
		}
	
		if (!validateInput(uriInfo)) {
			throw new BadRequestException("Incorrect input values for the searching criteria : ");
		}
	}
	
	public static List<Contact> getSearch (UriInfo uriInfo) {
		List<Contact> contacts = null;
		
		MultivaluedMap<String, String> paramMap = uriInfo.getQueryParameters();
		
		for (Search search : Search.values()) {
			for (String key : paramMap.keySet()) {
				if (search.getValue().contains(key)) {
					ContactService contactService = new ContactService();
					contacts =  contactService.searchContacts(ContactResourceHelper.getColumnName(key), paramMap.getFirst(key));
				}
			}	
		}
		return contacts;
	}
	
	public static boolean validateInput(UriInfo uriInfo) {
		boolean valid = true;
		
		MultivaluedMap<String, String> paramMap = uriInfo.getQueryParameters();
		
		if(paramMap.getFirst(Search.FIRST_NAME.getValue()) != null) {
			if (!validString(paramMap.getFirst(Search.FIRST_NAME.getValue()))) {
				valid = false; 
				logger.info("firstname :" + valid);
			}
		}
		
		if(paramMap.getFirst(Search.LAST_NAME.getValue()) != null) {
			if (!validString(paramMap.getFirst(Search.LAST_NAME.getValue()))) {
				valid = false;
				logger.info("lastname :" + valid);
			}
		}
		
		if(paramMap.getFirst(Search.PHONE_NUMBER.getValue()) != null) {
			if (!validNumber(paramMap.getFirst(Search.PHONE_NUMBER.getValue()))) {
				valid = false; 
				logger.info("phonenumber :" + valid);
			}
		}
		
		if(paramMap.getFirst(Search.EMAIL_ADDRESS.getValue()) != null) {
			if (!validEmail(paramMap.getFirst(Search.EMAIL_ADDRESS.getValue()))) {
				valid = false;
				logger.info("email :" + valid);
			}
		}
		return valid;	
	}
	
	public static boolean validNumber(String number) {
		return number.matches("\\d+");
	}
	
	public static boolean validString(String name) {
		return name.matches("[a-zA-Z]+");
	}
	
	public static boolean validEmail(String email) {
		return email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
	}
}
