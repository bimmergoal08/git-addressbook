package org.pbhatna.addressbook.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.pbhatna.addressbook.model.ErrorMessage;

/**
 * DataNotFoundExceptionMapper defined for DataNotFoundException. So if this exception is thrown,
 * get caught by the exception mapper and with "@Provider" annotation Jax-rs know's
 * this exception is registered and can provide customized response to the user with
 * the error message and status code we want, instead of throwing tomcat servlet exception.
 */

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {
	
	private static final String DOCUMENTATION_URL = "http://pbhatna.org";
	
	/**
	 * Method map's the thown exception with mapper registered and provide
	 * customized response to the user.
	 */
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
