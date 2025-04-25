package com.kh.carlpion.exception.exceptions;

public class EmailVerifyFailException extends RuntimeException {

	private static final long serialVersionUID = 9136770107810936011L;

	public EmailVerifyFailException(String msg) {
		
		super(msg);
	}
}
