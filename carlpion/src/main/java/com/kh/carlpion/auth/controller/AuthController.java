package com.kh.carlpion.auth.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginInfo){
		
		Map<String, String> loginResponse = authService.login(loginInfo);
		
		return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
	}
	
	@PostMapping("/auto-login")
	public ResponseEntity<?> autoLogin(@RequestBody Map<String, String> refreshToken) {
		
		String refreshTokenValue = refreshToken.get("refreshToken");

		Map<String, String> loginResponse = tokenService.loginByRefreshToken(refreshTokenValue);
		
		return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestBody Map<String, String> refreshToken) {
		
		String refreshTokenValue = refreshToken.get("refreshToken");
		
		tokenService.deleteToken(refreshTokenValue);
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping("/mail")
	public ResponseEntity<?> sendAuthenticateEmail(@RequestBody Map<String, String> email) {
		
		String emailValue = email.get("email");
		
		authService.sendAuthenticateEmail(emailValue);
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
//	@PostMapping("/find-id")
//	@PostMapping("/find-pw")
}
