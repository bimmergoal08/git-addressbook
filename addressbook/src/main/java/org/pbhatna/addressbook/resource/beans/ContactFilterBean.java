package org.pbhatna.addressbook.resource.beans;
import javax.ws.rs.QueryParam;

import org.pbhatna.addressbook.util.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContactFilterBean {
	
	private static final transient Logger logger = LoggerFactory.getLogger(ContactFilterBean.class);
	
	private @QueryParam("firstName") String firstName;
	private @QueryParam("lastName") String lastName;
	private @QueryParam("id") Long id;
	private @QueryParam("phone") String phone;
	private @QueryParam("email") String email;
	private @QueryParam("address") String address;
	private @QueryParam("start") String start;
	private @QueryParam("size") String size;
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public boolean isSearchField(String fieldType) {
		boolean valid = false;
		
		if (fieldType != null && !fieldType.isEmpty()) {
			valid = true;
			logger.debug(fieldType + ":" + valid);
		}
		return valid;
	} 
	
	public String getSearchValue(String fieldType) {
		String searchText = "";
		
		switch(fieldType) {
			case "PersonID": 
				searchText = getFirstName(); 
				break;
			case "FirstName": 
				searchText = getFirstName(); 
				break;
			case "LastName": 
				searchText = getLastName(); 
				break;
			case "PrimaryEmail": 
				searchText = getEmail(); 
				break;
			case "PrimaryAddress": 
				searchText = getAddress(); 
				break;
			case "PrimaryPhone": 
				searchText = getPhone(); 
				break;
			}
		return searchText;
	}
	
	
	public boolean isSearchEnabled() {
		boolean enabled = false;
		if (isSearchField(firstName) || isSearchField(lastName) || isSearchField(email)
				|| isSearchField(phone) || isSearchField(address)) {
			logger.info("searchEnabled :"+ true);
			enabled = true;
		}
		return enabled;
	}
	
	public String getSearchCriteria() {
		
		String searchCriteria = "";
		if (isSearchField(firstName)) {
			searchCriteria = Search.FIRST_NAME.getValue();
		} else if (isSearchField(lastName)) {
			searchCriteria = Search.LAST_NAME.getValue();
		} else if (isSearchField(email)) {
			searchCriteria = Search.EMAIL_ADDRESS.getValue();
		} else if (isSearchField(phone)) {
			searchCriteria = Search.PHONE_NUMBER.getValue();
		} else if (isSearchField(address)) {
			searchCriteria = Search.ADDRESS.getValue();
		}
		
		logger.info(searchCriteria);
		return searchCriteria;
	}
	
	@Override
	public String toString() {
		return "ContactFilterBean [firstName=" + firstName + ", lastName="
				+ lastName + ", id=" + id + ", phone=" + phone + ", email="
				+ email + ", address=" + address + "]";
	}
	
}
