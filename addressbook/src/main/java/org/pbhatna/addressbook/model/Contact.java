package org.pbhatna.addressbook.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class store all the attribute values populated as a result of Mysql query.
 * Also with the jaxb annotation it's get automatically translated to Json while
 * providing an output to the user.  
 */

@XmlRootElement
public class Contact {
	
	private Long contactId;
	private String firstName;
	private String lastName;
	private String primaryPhoneNumber;
	private String primaryAddress;
	private String primaryEmailAddress;
	private String city;
	private String state;
	private String zip;
	private String country;
	
	
	/**
	 * No args constructor needed for Jaxb marshalling and unmarshalling.
	 */
	public Contact() {
		super();
	}
	
	/**
	 * Parameterized constructor
	 * 
	 * @param contactId
	 * @param firstName
	 * @param lastName
	 * @param primaryPhoneNumber
	 * @param primaryAddress
	 * @param primaryEmailAddress
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public Contact(
			Long contactId, 
			String firstName,
			String lastName,
			String primaryPhoneNumber,
			String primaryAddress,
			String primaryEmailAddress,
			String city,
			String state,
			String zip,
			String country
	) {
		super();
		this.contactId = contactId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.primaryPhoneNumber = primaryPhoneNumber;
		this.primaryAddress = primaryAddress;
		this.primaryEmailAddress = primaryEmailAddress;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
	}
	
	public Long getContactId() {
		return contactId;
	}
	
	public void setContactId(Long contactId) {
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}
	
	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * Overriden toStrings method
	 */
	@Override
	public String toString() {
		return "Contact [contactId=" + contactId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", primaryPhoneNumber="
				+ primaryPhoneNumber + ", primaryAddress=" + primaryAddress
				+ ", primaryEmailAddress=" + primaryEmailAddress + ", city="
				+ city + ", state=" + state + ", zip=" + zip + ", country="
				+ country + "]";
	}

}
