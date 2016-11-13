package com.opipo.relac.exception;

public class InvalidUser extends RuntimeException {
	
	private static String MESSAGE = " user is invalid to this request.";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidUser(String userIdentifier){
		super(new StringBuilder(userIdentifier).append(MESSAGE).toString());
	}

}
