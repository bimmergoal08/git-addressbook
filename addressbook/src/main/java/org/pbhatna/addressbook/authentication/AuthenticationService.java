package org.pbhatna.addressbook.authentication;

import java.io.IOException;
import java.util.StringTokenizer;

import org.glassfish.jersey.internal.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Authentication Service class that validates the user credential's and send's back the
 * appropriate boolean response based on the request
 */
public class AuthenticationService {
	
	private static final transient Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	
	// Valid credential's to access the API 
	public static final String USER_NAME = "addressbook";
	public static final String PASSWORD = "Green123";
	public static final String ENCODING = "UTF-8";
	
	/**
	 * This method takes credential's as parameter, extracts the username and password 
	 * from the authentication string and validate it against the existing credentials 
	 * and return boolean accordingly.
	 */
	public boolean authenticate(String credentials) {

		if (credentials == null) {
			return false;
		}
		
		final String encodedCredentials = credentials.replaceFirst("Basic"+ " ", "");
		String userNameAndPassword = null;
		
		try {
			byte[] decodedBytes = Base64.decode(encodedCredentials.getBytes());
			userNameAndPassword = new String(decodedBytes, ENCODING);
		} catch (IOException e) {
			logger.error("IOException :"+ e.getMessage());
		}
		// Tokenizer split's the string on colon":" and extract's each token,
		// which is username and password.
		final StringTokenizer tokenizer = new StringTokenizer(userNameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();

		// Get authentication status
		boolean authenticationStatus = USER_NAME.equals(username) && PASSWORD.equals(password);
		
		logger.info("authenticationStatus : " + authenticationStatus);
		return authenticationStatus;
	}
}
