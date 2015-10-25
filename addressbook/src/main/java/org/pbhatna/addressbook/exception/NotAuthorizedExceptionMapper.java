package org.pbhatna.addressbook.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.pbhatna.addressbook.model.ErrorMessage;

@Provider
public class NotAuthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {

private static final String DOCUMENTATION_URL = "http://pbhatna.org";
	
	@Override
	public Response toResponse(NotAuthorizedException exception) {
		
		ErrorMessage errorMessage = new ErrorMessage(Status.UNAUTHORIZED.getStatusCode(), 
				exception.getMessage(),
				DOCUMENTATION_URL);
		
		return Response.status(Status.UNAUTHORIZED)
				.entity(errorMessage)
				.build();
	}

}
