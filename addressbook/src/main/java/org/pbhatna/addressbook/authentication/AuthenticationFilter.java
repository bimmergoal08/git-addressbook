package org.pbhatna.addressbook.authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationFilter implements javax.servlet.Filter {
	
	private static final transient Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
	
	public static final String AUTHENTICATION_HEADER = "Authorization";
	
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

			AuthenticationService authenticationService = new AuthenticationService();
			boolean authenticationStatus = authenticationService.authenticate(authCredentials);

			if (authenticationStatus) {
					filter.doFilter(request, response);
			} else {
				if (response instanceof HttpServletResponse) {
					HttpServletResponse httpServletResponse = (HttpServletResponse) response;
					httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					httpServletResponse.setContentType(MediaType.APPLICATION_JSON);
					httpServletResponse.getWriter().print("Not Found:");
				}
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}
	
	@Override
	public void destroy() {
	}

}

