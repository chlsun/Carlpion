package com.kh.carlpion.auth.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kh.carlpion.auth.model.vo.CarlpionUserDetails;
import com.kh.carlpion.auth.model.dto.LoginDTO;
import com.kh.carlpion.exception.exceptions.CustomAuthenticationException;
import com.kh.carlpion.token.model.service.TokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;
	
	@Override
	public CarlpionUserDetails getUserDetails() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		CarlpionUserDetails user = (CarlpionUserDetails)authentication.getPrincipal();
		
		return user;
	}
	
	@Override
	public Map<String, String> login(LoginDTO loginInfo) {
		
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
		
		Map<String, String> loginResponse = new HashMap<String, String>();
		
		loginResponse.put("username", user.getUsername());
		loginResponse.put("nickname", user.getNickname());
		loginResponse.put("realname", user.getRealname());
		loginResponse.put("email", user.getEmail());
		loginResponse.put("accessToken", tokenService.generateAccessToken(user.getUsername()));
		
		if(isKeep) {
			loginResponse.put("refreshToken", tokenService.generateRefreshToken(user.getUserNo(), user.getUsername()));
		}

		return loginResponse;
	}

	@Override
	public void sendAuthenticateEmail(String email) {
		
		
	}
}
