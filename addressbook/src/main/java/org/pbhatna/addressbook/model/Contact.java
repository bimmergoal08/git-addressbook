package org.pbhatna.addressbook.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Contact {
	
	private long contactId;
	private String firstName;
	private String lastName;
	private String primaryPhoneNumber;
	private String primaryAddress;
	private String primaryEmailAddress;
	
	public Contact() {
		super();
	}
	
	public Contact(
			long contactId,
			String firstName,
			String lastName,
			String primaryPhoneNumber,
			String primaryAddress,
			String primaryEmailAddress
	) {
		super();
		this.contactId = contactId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.primaryPhoneNumber = primaryPhoneNumber;
		this.primaryAddress = primaryAddress;
		this.primaryEmailAddress = primaryEmailAddress;
	}

	public long getContactId() {
		return contactId;
	}
	
	public void setContactId(long contactId) {
		this.contactId = contactId;
	}
	
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
	public String getPrimaryPhoneNumber() {
		return primaryPhoneNumber;
	}
	public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
		this.primaryPhoneNumber = primaryPhoneNumber;
	}
	public String getPrimaryAddress() {
		return primaryAddress;
	}
	public void setPrimaryAddress(String primaryAddress) {
		this.primaryAddress = primaryAddress;
	}
	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}
	public void setPrimaryEmailAddress(String primaryEmailAddress) {
		this.primaryEmailAddress = primaryEmailAddress;
	}

}
