package org.pbhatna.addressbook.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.pbhatna.addressbook.model.ErrorMessage;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {
	
	private static final String DOCUMENTATION_URL = "http://pbhatna.org";
	
	@Override
	public Response toResponse(DataNotFoundException exception) {
		
		ErrorMessage errorMessage = new ErrorMessage(Status.NOT_FOUND.getStatusCode(), 
				exception.getMessage(),
				DOCUMENTATION_URL);
		
		return Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
	}
}
