package com.kh.carlpion.exception.exceptions;

public class DuplicateValueException extends RuntimeException {
	
	private static final long serialVersionUID = -2719299743706568107L;

	public DuplicateValueException(String msg) {
		
		super(msg);
	}
}
