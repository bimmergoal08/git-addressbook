package org.pbhatna.addressbook.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.pbhatna.addressbook.model.ErrorMessage;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {
	
	@Override
	public Response toResponse(DataNotFoundException exception) {
		
		ErrorMessage errorMessage = new ErrorMessage(404, exception.getMessage(), "http://pbhatna.org");
			return Response.status(Status.ACCEPTED)
					.entity(errorMessage)
					.build();
	}
}
