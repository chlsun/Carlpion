package com.kh.carlpion.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kh.carlpion.exception.exceptions.DuplicateValueException;
import com.kh.carlpion.exception.exceptions.FileSaveException;
import com.kh.carlpion.exception.exceptions.NotFindException;
import com.kh.carlpion.exception.exceptions.UnexpectSqlException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	public ResponseEntity<?> exceptionHandler(Exception e, HttpStatus status){
		
		return ResponseEntity.status(status).body(e.getMessage());
	}

	@ExceptionHandler(EmptyInputException.class)
	public ResponseEntity<?> emptyInputError(EmptyInputException e){
		
		return exceptionHandler(e, HttpStatus.BAD_REQUEST);
	}

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
	
	
	@ExceptionHandler(NotFindException.class)
	public ResponseEntity<?> handleNotFind(NotFindException e) {
		return exceptionHandler(e, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(FileSaveException.class)
	public ResponseEntity<?> handleFileSave(FileSaveException e) {
		return exceptionHandler(e, HttpStatus.BAD_REQUEST);
	}
	
	
}
