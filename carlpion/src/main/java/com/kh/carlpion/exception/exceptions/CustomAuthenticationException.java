package com.kh.carlpion.exception.exceptions;

public class CustomAuthenticationException extends RuntimeException {

	private static final long serialVersionUID = 3462346412591874957L;

	public CustomAuthenticationException(String msg) {
		
		super(msg);
	}
}
