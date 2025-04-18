package com.kh.carlpion.auth.model.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.kh.carlpion.auth.model.CarlpionUserDetails;
import com.kh.carlpion.auth.model.dto.LoginDTO;
import com.kh.carlpion.exception.exceptions.CustomAuthenticationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final AuthenticationManager authenticationManager;
	
	@Override
	public void login(LoginDTO loginInfo) {
		
		String username = loginInfo.getUsername();
		String password = loginInfo.getPassword();
		boolean isKeep = loginInfo.isKeep();
		
		Authentication authentication = null;
		
		try {
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
		} catch (AuthenticationException e) {
			throw new CustomAuthenticationException("아이디나 비밀번호를 잘못 입력 하셨습니다.");
		}
		
		CarlpionUserDetails user = (CarlpionUserDetails)authentication.getPrincipal();
	}

}
