package com.tennisKata.exceptions;

public class MyBusinessException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyBusinessException(Exception e) {
		super(e);
	}

	public MyBusinessException(String message, Exception e) {
		super(message, e);
	}

	public MyBusinessException(String message) {
		super(message);
	}

}
