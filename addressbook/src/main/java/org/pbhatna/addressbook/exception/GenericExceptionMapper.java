package org.pbhatna.addressbook.exception;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.pbhatna.addressbook.model.ErrorMessage;

/**
 * GenericExceptionMapper is defined for any unexpected error, that may occur. but
 * In any case user should not see ugly exception thrown by tomcat. So, to handle that
 * gracefully this mapper is registered.
 */

@Provider
@Singleton
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
	
	/**
	 * Method prevent user see the tomcat error page. And handle error
	 * gracefully.
	 */
	
	@Override
	public Response toResponse(Throwable e) {
		ErrorMessage errorMessage = new ErrorMessage(500, e.getMessage(), "http://pbhatna.org");
		
		return Response.status(Status.INTERNAL_SERVER_ERROR).
				entity(errorMessage).build();
	}
}
