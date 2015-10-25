package org.pbhatna.addressbook.util;

public enum Sort {
		FIRST_NAME("firstname"),
		LAST_NAME("lastname"),
		EMAIL_ADDRESS("email"),
		ADDRESS("address"),
		PHONE_NUMBER("phone"),
		CONTACT_ID("id");
		
		private String value;

		private Sort(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
}
