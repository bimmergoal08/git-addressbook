package org.pbhatna.addressbook.util;
	
	/**
	 * Enum for valid fieldtype which user pass in as a query parameter.
	 */
	public enum Search {
		FIRST_NAME("firstname"),
		LAST_NAME("lastname"),
		EMAIL_ADDRESS("email"),
		ADDRESS("address"),
		PHONE_NUMBER("phone"),
		ID("id"),
		CITY("city"),
		STATE("state"),
		ZIP("zip"),
		COUNTRY("country");
		
		private String value;

		private Search(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

