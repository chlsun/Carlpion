package com.kh.carlpion.exception;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kh.carlpion.exception.exceptions.AlreadyExistsException;
import com.kh.carlpion.exception.exceptions.BadRequestException;
import com.kh.carlpion.exception.exceptions.CarModelNotFoundException;
import com.kh.carlpion.exception.exceptions.CarNotFoundException;
import com.kh.carlpion.exception.exceptions.CreateDirectoriesException;
import com.kh.carlpion.exception.exceptions.CustomAuthenticationException;
import com.kh.carlpion.exception.exceptions.CustomMessagingException;
import com.kh.carlpion.exception.exceptions.DuplicateValueException;
import com.kh.carlpion.exception.exceptions.EmailDuplicateException;
import com.kh.carlpion.exception.exceptions.EmailVerifyFailException;
import com.kh.carlpion.exception.exceptions.EmptyInputException;
import com.kh.carlpion.exception.exceptions.FileDeleteException;
import com.kh.carlpion.exception.exceptions.FileSaveException;

import com.kh.carlpion.exception.exceptions.IllegalArgumentPwException;
import com.kh.carlpion.exception.exceptions.ImgFileNotFoundException;
import com.kh.carlpion.exception.exceptions.ModelNotFoundException;
import com.kh.carlpion.exception.exceptions.NickNameDuplicateException;
import com.kh.carlpion.exception.exceptions.ImgFileNotFoundException;
import com.kh.carlpion.exception.exceptions.ModelNotFoundException;
import com.kh.carlpion.exception.exceptions.NickNameDuplicateException;
import com.kh.carlpion.exception.exceptions.NotFindException;
import com.kh.carlpion.exception.exceptions.PointException;
import com.kh.carlpion.exception.exceptions.RentCarNotFoundException;
import com.kh.carlpion.exception.exceptions.UnauthorizedException;
import com.kh.carlpion.exception.exceptions.UnexpectSqlException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	public ResponseEntity<?> exceptionHandler(Exception e, HttpStatus status){
		
		return ResponseEntity.status(status).body(e.getMessage());
	}

	// 조회값으로 빈문자열이 요청왔을 경우 발생
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
	
	// 이미 존재하는 식별값 요청이 들어왔을 경우 발생
	@ExceptionHandler(AlreadyExistsException.class)
	public ResponseEntity<?> alreadyExistsError(AlreadyExistsException e){
		
		return exceptionHandler(e, HttpStatus.CONFLICT);
	}
	@ExceptionHandler(NickNameDuplicateException.class)
	public ResponseEntity<?> handleNickNameDuplicate(NickNameDuplicateException e){
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}

	@ExceptionHandler(EmailDuplicateException.class)
	public ResponseEntity<?> handleEmailDuplicate(EmailDuplicateException e){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}
	@ExceptionHandler(IllegalArgumentPwException.class)
	public ResponseEntity<?> IllegalArgumentPw(IllegalArgumentPwException e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
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
	
	// 잘못된 값으로 인해 SQL문이 제대로 작동하지 않았을 때 발생
	@ExceptionHandler(InvalidParameterException.class)
	public ResponseEntity<?> handleInvalidParameter(InvalidParameterException e) {
		
		Map<String, String> error = new HashMap<String, String>();
		
		error.put("cause", e.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	// 클라이언트 측에서 요청과 함께 보내는 데이터가 유효성 검사를 통과하지 못했을 때 발생
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleArgumentNotValid(MethodArgumentNotValidException e) {
		
		Map<String, String> errors = new HashMap<>();
		
		e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
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
	
	@ExceptionHandler(PointException.class)
	public ResponseEntity<?> handlePoint(PointException e) {
		return exceptionHandler(e, HttpStatus.BAD_REQUEST);
	}
	

	// 차량모델을 찾을 수 없을때 발생
	@ExceptionHandler(ModelNotFoundException.class)
	public ResponseEntity<?> modelNotFoundError(ModelNotFoundException e){
		return exceptionHandler(e, HttpStatus.NOT_FOUND);
	}
	
	// 렌트차량을 찾을 수 없을때 발생
	@ExceptionHandler(CarNotFoundException.class)
	public ResponseEntity<?> carNotFoundError(CarNotFoundException e){
		return exceptionHandler(e, HttpStatus.NOT_FOUND);
	}
	
	
	// 이미지 파일이 null로 넘어왔을 경우 발생
	@ExceptionHandler(ImgFileNotFoundException.class)
	public ResponseEntity<?> imgFileNotFoundError(ImgFileNotFoundException e){
		return exceptionHandler(e, HttpStatus.NOT_FOUND);
	}
	
	// 차량 모델 조회 값이 없을 경우 발생
	@ExceptionHandler(CarModelNotFoundException.class)
	public ResponseEntity<?> carModelNotFoundError(CarModelNotFoundException e){
		return exceptionHandler(e, HttpStatus.NOT_FOUND);
	}
	
	// 렌트 차량 조회 값이 없을 경우 발생
	@ExceptionHandler(RentCarNotFoundException.class)
	public ResponseEntity<?> rentCarNotFoundError(RentCarNotFoundException e){
		return exceptionHandler(e, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<?> handleUnauthorized(UnauthorizedException e) {
		return exceptionHandler(e, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> badRequestError(BadRequestException e) {
		return exceptionHandler(e, HttpStatus.BAD_REQUEST);
	}


}
