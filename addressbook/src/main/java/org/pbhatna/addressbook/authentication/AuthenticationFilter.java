package org.pbhatna.addressbook.authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible for authenticating user with basic HTTP authentication,
 * and intercepts the user request using servlet filter and provide an appropriate
 * response based on the credentials.
 */
public class AuthenticationFilter implements javax.servlet.Filter {
	
	// SLF4J wrapper logger is used for internal logging, All the logger's are routed to SLF4J
	// from underlying Jax-rs which uses Java Util logging. In case API implementation is changed
	// in future that uses different logging, SLF4J will smoothly accomodate that change.
	
	private static final transient Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
	
	public static final String AUTHENTICATION_HEADER = "Authorization";
	
	/**
	 * This method intercepts the incomming user request to access the addressbook API
	 * and check if user is authorized to access and has valid credentials. If not this
	 * send the response code(401) in header, saying user is unauthorized. 
	 */
	@Override
	public void doFilter(
			ServletRequest request,
			ServletResponse response,
			FilterChain filter
	) throws IOException, ServletException {
		
		logger.info("doFilter");
		if (request instanceof HttpServletRequest) {
			
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			String authCredentials = httpServletRequest.getHeader(AUTHENTICATION_HEADER);
			
			// Instantiate an authentication service and call authenticate method with
			// the credentials.
			AuthenticationService authenticationService = new AuthenticationService();
			boolean authenticationStatus = authenticationService.authenticate(authCredentials);

			if (authenticationStatus) {
				// if success, let the user access resources.
				filter.doFilter(request, response);
			} else {
				// if authentication failed, send response code accordingly.
				if (response instanceof HttpServletResponse) {
					HttpServletResponse httpServletResponse = (HttpServletResponse) response;
					httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				}
			}
		}
	}
	
	/*
	 * Not required for current implementation
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {}
	
	/*
	 * Not required for current implementation
	 */
	@Override
	public void destroy() {
	}

}

