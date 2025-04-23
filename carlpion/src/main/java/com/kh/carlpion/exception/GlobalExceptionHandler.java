package com.kh.carlpion.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kh.carlpion.exception.exceptions.CreateDirectoriesException;
import com.kh.carlpion.exception.exceptions.CustomAuthenticationException;
import com.kh.carlpion.exception.exceptions.CustomMessagingException;
import com.kh.carlpion.exception.exceptions.DuplicateValueException;
import com.kh.carlpion.exception.exceptions.FileDeleteException;
import com.kh.carlpion.exception.exceptions.EmailDuplicateException;
import com.kh.carlpion.exception.exceptions.EmailVerifyFailException;
import com.kh.carlpion.exception.exceptions.NickNameDuplicateException;
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

	// 회원가입 시, 중복된 값을 입력했을 경우 발생
	@ExceptionHandler(DuplicateValueException.class)
	public ResponseEntity<Map<String, String>> handleDuplicateValue(DuplicateValueException e) {
		
		Map<String, String> error = new HashMap<String, String>();
		
		error.put("cause", e.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	// 모종의 이유로 SQL문이 올바르게 작동하지 않았을 경우 발생
	@ExceptionHandler(UnexpectSqlException.class)
	public ResponseEntity<Map<String, String>> handleUnexpectSql(UnexpectSqlException e) {
		
		Map<String, String> error = new HashMap<String, String>();
		
		error.put("cause", e.getMessage());
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
	
	@ExceptionHandler(NickNameDuplicateException.class)
	public ResponseEntity<?> handleNickNameDuplicate(NickNameDuplicateException e){
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}

	@ExceptionHandler(EmailDuplicateException.class)
	public ResponseEntity<?> handleEmailDuplicate(EmailDuplicateException e){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}
	

	// AuthenticationManager가 사용자 인증을 실패했을 경우 발생
	@ExceptionHandler(CustomAuthenticationException.class)
	public ResponseEntity<Map<String, String>> handleCustomAuthentication(CustomAuthenticationException e) {
		
		Map<String, String> error = new HashMap<String, String>();
		
		error.put("cause", e.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	// 모종의 이유로 이메일 전송 관련 예외가 발생하면 발생
	@ExceptionHandler(CustomMessagingException.class)
	public ResponseEntity<Map<String, String>> handleCustomMessaging(CustomMessagingException e) {
		
		Map<String, String> error = new HashMap<String, String>();
		
		error.put("cause", e.getMessage());
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
	
	// 이메일 인증 정보 불일치로 인증을 실패했을 경우 발생
	@ExceptionHandler(EmailVerifyFailException.class)
	public ResponseEntity<Map<String, String>> handleEmailVerifyFail(EmailVerifyFailException e) {
		
		Map<String, String> error = new HashMap<String, String>();
		
		error.put("cause", e.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(NotFindException.class)
	public ResponseEntity<?> handleNotFind(NotFindException e) {
		return exceptionHandler(e, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(FileSaveException.class)
	public ResponseEntity<?> handleFileSave(FileSaveException e) {
		return exceptionHandler(e, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(FileDeleteException.class)
	public ResponseEntity<?> handleFileDelete(FileDeleteException e) {
		return exceptionHandler(e, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CreateDirectoriesException.class)
	public ResponseEntity<?> handleCreateDirectories(CreateDirectoriesException e) {
		return exceptionHandler(e, HttpStatus.BAD_REQUEST);
	}

}
