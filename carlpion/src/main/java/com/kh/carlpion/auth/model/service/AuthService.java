package com.kh.carlpion.auth.model.service;

import java.util.Map;

import com.kh.carlpion.auth.model.dto.LoginDTO;

public interface AuthService {

	Map<String, String> login(LoginDTO loginInfo);
}
