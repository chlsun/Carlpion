package com.kh.carlpion.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.carlpion.auth.model.dto.LoginDTO;
import com.kh.carlpion.auth.model.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginInfo){
		
		authService.login(loginInfo);
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
//	@PostMapping("/auto-login")
//	@PostMapping("/find-id")
//	@PostMapping("/find-pw")
}
