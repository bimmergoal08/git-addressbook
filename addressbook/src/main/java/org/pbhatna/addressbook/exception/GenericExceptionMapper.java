package org.pbhatna.addressbook.exception;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.pbhatna.addressbook.model.ErrorMessage;

@Provider
@Singleton
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable e) {
		ErrorMessage errorMessage = new ErrorMessage(500, e.getMessage(), "http://pbhatna.org");
		
		return Response.status(Status.INTERNAL_SERVER_ERROR).
				entity(errorMessage).build();
	}
}
