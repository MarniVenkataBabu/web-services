package com.cglia.springboot.webservices.exceptionHandling;

public class GlobalNotFoundException extends RuntimeException{

	public GlobalNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public GlobalNotFoundException(String message) {
		super(message);
	}

	public GlobalNotFoundException(Throwable cause) {
		super(cause);
	}
	

}
