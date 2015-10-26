package org.pbhatna.addressbook.util;

	public enum Column {
		
		FIRST_NAME("FirstName"),
		LAST_NAME("LastName"),
		EMAIL_ADDRESS("PrimaryEmail"),
		ADDRESS("PrimaryAddress"),
		PHONE_NUMBER("PrimaryPhone"),
		CONTACT_ID("PersonID"),
		CITY("City"),
		STATE("State"),
		ZIP("Zip"),
		COUNTRY("Country");
		
		private String value;

		private Column(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};
