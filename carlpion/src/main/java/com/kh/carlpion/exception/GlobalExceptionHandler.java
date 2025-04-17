package com.kh.carlpion.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kh.carlpion.exception.exceptions.DuplicateValueException;
import com.kh.carlpion.exception.exceptions.UnexpectSqlException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DuplicateValueException.class)
	public ResponseEntity<Map<String, String>> handleDuplicateValue(DuplicateValueException e) {
		
		Map<String, String> error = new HashMap<String, String>();
		
		error.put("cause", e.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(UnexpectSqlException.class)
	public ResponseEntity<Map<String, String>> handleUnexpectSql(UnexpectSqlException e) {
		
		Map<String, String> error = new HashMap<String, String>();
		
		error.put("cause", e.getMessage());
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}
