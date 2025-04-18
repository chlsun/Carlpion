package com.kh.carlpion.exception.exceptions;

public class UnexpectSqlException extends RuntimeException {

	private static final long serialVersionUID = 216509690552852558L;

	public UnexpectSqlException(String msg) {
		
		super(msg);
	}
}
