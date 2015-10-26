package org.pbhatna.addressbook.util;

	public enum Column {
		
		FIRST_NAME("FirstName"),
		LAST_NAME("LastName"),
		EMAIL_ADDRESS("PrimaryEmail"),
		ADDRESS("PrimaryAddress"),
		PHONE_NUMBER("PrimaryPhone"),
		CONTACT_ID("PersonID");
		
		private String value;

		private Column(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};
