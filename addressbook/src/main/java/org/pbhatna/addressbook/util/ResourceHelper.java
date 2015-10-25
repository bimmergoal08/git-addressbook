package org.pbhatna.addressbook.util;

import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceHelper {
	
	private static final transient Logger logger = LoggerFactory.getLogger(ResourceHelper.class);
	
	public static String getColumnName(String fieldType) {
		String key = "";
		
		switch(fieldType) {
			case "firstname": 
				key = Search.FIRST_NAME.getValue(); 
				break;
			case "lastname": 
				key = Search.LAST_NAME.getValue();
				break;
			case "email": 
				key = Search.EMAIL_ADDRESS.getValue(); 
				break;
			case "address": 
				key = Search.ADDRESS.getValue();
				break;
			case "phone": 
				key = Search.PHONE_NUMBER.getValue(); 
				break;
		}
		return key;
	}
	
	public static boolean searchValid(MultivaluedMap<String, String> queryParameterMap) {
		boolean valid = false;
		
		for (InputParameter input: InputParameter.values()) {
			if (queryParameterMap.containsKey(input.getValue())) {
				logger.info("valid" + input);
				valid = true;
			}
		}
		return valid;
	}
	
	public static boolean inputValid(MultivaluedMap<String, String> queryParameterMap) {
		boolean valid = false;
		
		for (String key : queryParameterMap.keySet()) {			
			if (!containsNumbers(queryParameterMap.getFirst(key))) {
				valid = true;
			}
		}	
		return valid;
	}
	
	public static boolean containsNumbers(String str) {
		for(char ch : str.toCharArray()){
			if(Character.isDigit(ch)){
	            return true;
	        }
	    }
	    return false;
	}
}
