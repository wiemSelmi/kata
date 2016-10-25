package com.tennisKata.exceptions;

public class MyTechnicalException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyTechnicalException(Exception e) {
		super(e);
	}

	public MyTechnicalException(String message, Exception e) {
		super(message, e);
	}

	public MyTechnicalException(String message) {
		super(message);
	}
}
