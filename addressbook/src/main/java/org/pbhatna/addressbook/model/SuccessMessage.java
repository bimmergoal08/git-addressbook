package org.pbhatna.addressbook.model;


/**
 * This class provides a standard success template to map all the
 * output responses with the sucess message.
 */

public class SuccessMessage {
	
	private int successCode;
	private String successMessage;

	public SuccessMessage() {
		super();
	}
	
	/**
	 * @param successCode
	 * @param successMessage
	 */
	public SuccessMessage(int successCode, String successMessage) {
		super();
		this.successCode = successCode;
		this.successMessage = successMessage;
	}
	
	/**
	 * @return the successCode
	 */
	public int getSuccessCode() {
		return successCode;
	}
	/**
	 * @param successCode the successCode to set
	 */
	public void setSuccessCode(int successCode) {
		this.successCode = successCode;
	}
	/**
	 * @return the successMessage
	 */
	public String getSuccessMessage() {
		return successMessage;
	}
	/**
	 * @param successMessage the successMessage to set
	 */
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
}
