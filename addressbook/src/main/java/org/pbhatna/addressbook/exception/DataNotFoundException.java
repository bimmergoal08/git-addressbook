package org.pbhatna.addressbook.exception;

/**
 * DataNotFoundException defined thrown by contact resource.
 */
public class DataNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public DataNotFoundException(String message) {
		super(message);
	}
}
