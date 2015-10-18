package org.pbhatna.addressbook.util;

	public enum Search {
		FIRST_NAME("firstName"),
		LAST_NAME("lastName"),
		EMAIL_ADDRESS("email"),
		ADDRESS("address"),
		PHONE_NUMBER("phone"),
		CONTACT_ID("id");
		
		private String value;

		private Search(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};


