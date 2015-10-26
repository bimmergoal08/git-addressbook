package org.pbhatna.addressbook.exception;

/**
 * BadRequestException defined thrown by contact resource.
 */
public class BadRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public BadRequestException(String message) {
		super(message);
	}
}
