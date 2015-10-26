package org.pbhatna.addressbook.util;

	public enum Search {
		FIRST_NAME("firstname"),
		LAST_NAME("lastname"),
		EMAIL_ADDRESS("email"),
		ADDRESS("address"),
		PHONE_NUMBER("phone");
		
		private String value;

		private Search(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

