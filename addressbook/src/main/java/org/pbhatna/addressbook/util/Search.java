package org.pbhatna.addressbook.util;

	public enum Search {
		
		FIRST_NAME("FirstName"),
		LAST_NAME("LastName"),
		EMAIL_ADDRESS("PrimaryEmail"),
		ADDRESS("PrimaryAddress"),
		PHONE_NUMBER("PrimaryPhone"),
		CONTACT_ID("PersonID");
		
		private String value;

		private Search(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};
