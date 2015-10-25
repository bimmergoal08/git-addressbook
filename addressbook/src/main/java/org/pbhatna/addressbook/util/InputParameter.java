package org.pbhatna.addressbook.util;

	public enum InputParameter {
		FIRST_NAME("firstname"),
		LAST_NAME("lastname"),
		EMAIL_ADDRESS("email"),
		ADDRESS("address"),
		PHONE_NUMBER("phone");
		
		private String value;

		private InputParameter(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

