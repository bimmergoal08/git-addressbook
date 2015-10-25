package org.pbhatna.addressbook.authentication;

import java.io.IOException;
import java.util.StringTokenizer;

import org.glassfish.jersey.internal.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationService {
	
	public static final String USER_NAME = "addressbook";
	public static final String PASSWORD = "Green123";
	
	private static final transient Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	
	public boolean authenticate(String credentials) {

		if (credentials == null) {
			return false;
		}
		
		final String encodedUserPassword = credentials.replaceFirst("Basic"+ " ", "");
		
		String userNameAndPassword = null;
		
		try {
			byte[] decodedBytes = Base64.decode(encodedUserPassword.getBytes());
			userNameAndPassword = new String(decodedBytes, "UTF-8");
			
			logger.error("usernameAndPassword" + userNameAndPassword);
		} catch (IOException e) {
			logger.error("IOException :"+ e.getMessage());
		}
		
		final StringTokenizer tokenizer = new StringTokenizer(userNameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();

		boolean authenticationStatus = USER_NAME.equals(username) && PASSWORD.equals(password);
		logger.error("authenticationStatus : " + authenticationStatus);
		return authenticationStatus;
	}
}
