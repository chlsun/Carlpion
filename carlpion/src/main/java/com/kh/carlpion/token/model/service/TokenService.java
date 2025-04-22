package com.kh.carlpion.token.model.service;

import java.util.Map;

public interface TokenService {
	
	String generateAccessToken(String username);
	
	String generateRefreshToken(Long userNo, String username);
	
	Map<String, String> loginByRefreshToken(String refreshToken);
	
	void deleteToken(String refreshToken);
}
