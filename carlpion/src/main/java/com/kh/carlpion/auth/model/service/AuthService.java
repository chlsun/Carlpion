package com.kh.carlpion.auth.model.service;

import java.util.Map;

import com.kh.carlpion.auth.model.dto.EmailDTO;
import com.kh.carlpion.auth.model.dto.LoginDTO;
import com.kh.carlpion.auth.model.vo.CarlpionUserDetails;

public interface AuthService {

	CarlpionUserDetails getUserDetails();
	
	Map<String, String> login(LoginDTO loginInfo);
	
	void sendVerifyEmailWhileSignUp(EmailDTO emailInfo);
}
