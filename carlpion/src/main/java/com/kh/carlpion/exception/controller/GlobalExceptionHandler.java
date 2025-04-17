package com.kh.carlpion.exception.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kh.carlpion.exception.EmptyInputException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	public ResponseEntity<?> exceptionHandler(Exception e, HttpStatus status){
		
		return ResponseEntity.status(status).body(e.getMessage());
	}
	
	@ExceptionHandler(EmptyInputException.class)
	public ResponseEntity<?> emptyInputError(EmptyInputException e){
		
		return exceptionHandler(e, HttpStatus.BAD_REQUEST);
	}
}
