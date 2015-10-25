package org.pbhatna.addressbook.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.pbhatna.addressbook.model.ErrorMessage;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {
	
	private static final String DOCUMENTATION_URL = "http://pbhatna.org";
	
	@Override
	public Response toResponse(BadRequestException exception) {
		
		ErrorMessage errorMessage = new ErrorMessage(Status.BAD_REQUEST.getStatusCode(), 
				exception.getMessage(),
				DOCUMENTATION_URL);
		
		return Response.status(Status.BAD_REQUEST)
				.entity(errorMessage)
				.build();
	}
}
