package com.meetpeoples.exception;

public class ErrorResponseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErrorResponseException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ErrorResponseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ErrorResponseException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ErrorResponseException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ErrorResponseException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
