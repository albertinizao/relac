package com.opipo.relac.exception;

public class NotFoundElement extends RuntimeException {
	
	private static String MESSAGE = " not found. Maybe it doesn't exist.";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NotFoundElement(String elementNotFound){
		super(new StringBuilder(elementNotFound).append(MESSAGE).toString());
	}

}
