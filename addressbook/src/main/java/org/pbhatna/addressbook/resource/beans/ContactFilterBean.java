package org.pbhatna.addressbook.resource.beans;

import javax.ws.rs.QueryParam;
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
	
	public boolean firstNameValid() {
		boolean valid = false;
		if (firstName != null && !firstName.isEmpty()) {
			valid = true;
			logger.debug("firstNameValid" + valid);
		}
		return valid;
	}
	
	public boolean lastNameValid() {
		boolean valid = false;
		if (lastName != null && !lastName.isEmpty()) {
			valid = true;
			logger.debug("lastNameValid" + valid);
		}
		return valid;
	}
	
	public boolean emailValid() {
		boolean valid = false;
		if (email != null && !email.isEmpty()) {
			valid = true;
			logger.debug("emailValid" + valid);
		}
		return valid;
	}
	
	public boolean addressValid() {
		boolean valid = false;
		if (address != null && !address.isEmpty()) {
			valid = true;
			logger.debug("addressValid" + valid);
		}
		return valid;
	}
	
	public boolean phoneValid() {
		boolean valid = false;
		if (phone != null && !phone.isEmpty()) {
			valid = true;
			logger.debug("phoneValid" + valid);
		}
		return valid;
	}
	
	public boolean searchCriteriaEnabled() {
		boolean enabled = false;
		if (firstNameValid() || lastNameValid() || emailValid()
				|| addressValid() || phoneValid()) {
			System.out.print("searchCriteriaEnabled"+ true);
			enabled = true;
		}
		return enabled;
	}
	@Override
	public String toString() {
		return "ContactFilterBean [firstName=" + firstName + ", lastName="
				+ lastName + ", id=" + id + ", phone=" + phone + ", email="
				+ email + ", address=" + address + "]";
	}
	
}
