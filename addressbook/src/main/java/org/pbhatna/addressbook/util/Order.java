package org.pbhatna.addressbook.util;

public enum Order {
	
	ASCENDING("ASC"),
	DESCENDING("DESC");
	
	private String value;

	private Order(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
};