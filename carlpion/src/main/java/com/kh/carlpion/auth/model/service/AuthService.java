package com.kh.carlpion.auth.model.service;

import com.kh.carlpion.auth.model.dto.LoginDTO;

public interface AuthService {

	void login(LoginDTO loginInfo);
}
