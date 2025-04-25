package com.kh.carlpion.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.carlpion.auth.model.dto.EmailDTO;
import com.kh.carlpion.auth.model.dto.FindIdDTO;
import com.kh.carlpion.auth.model.dto.FindPwDTO;
import com.kh.carlpion.auth.model.dto.LoginDTO;
import com.kh.carlpion.auth.model.service.AuthService;
import com.kh.carlpion.token.model.service.TokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final TokenService tokenService;
	
	// 로그인
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginInfo){
		
		Map<String, String> loginResponse = authService.login(loginInfo);
		
		return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
	}
	
	// 자동 로그인
	@PostMapping("/auto-login")
	public ResponseEntity<?> autoLogin(@RequestBody Map<String, String> refreshToken) {
		
		String refreshTokenValue = refreshToken.get("refreshToken");

		Map<String, String> loginResponse = tokenService.loginByRefreshToken(refreshTokenValue);
		
		return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
	}
	
	// 로그아웃
	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestBody Map<String, String> refreshToken) {
		
		String refreshTokenValue = refreshToken.get("refreshToken");
		
		tokenService.deleteToken(refreshTokenValue);
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	// 아이디 찾기
	@PostMapping("/find-id")
	public ResponseEntity<?> findId(@RequestBody @Valid FindIdDTO findIdInfo) {
		
		String username = authService.selectUsernameByCode(findIdInfo);
		
		Map<String, String> findIdResponse = new HashMap<String, String>();
		
		findIdResponse.put("username", username);
		
		return ResponseEntity.status(HttpStatus.OK).body(findIdResponse);
	}
	
	// 비밀번호 찾기
	@PostMapping("/find-pw")
	public ResponseEntity<?> findPw(@RequestBody @Valid FindPwDTO findPwInfo) {
		
		authService.sendTemporaryPasswordEmail(findPwInfo);
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	// 회원가입, 아이디 찾기, 비밀번호 찾기 시, 인증 이메일 전송
	@PostMapping("/send-email")
	public ResponseEntity<?> sendVerifyEmailByType(@RequestBody @Valid EmailDTO emailInfo) {
		
		authService.sendVerifyEmailByType(emailInfo);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
