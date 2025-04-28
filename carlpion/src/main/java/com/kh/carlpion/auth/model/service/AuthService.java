package com.kh.carlpion.auth.model.service;

import java.util.Map;

import com.kh.carlpion.auth.model.dto.EmailDTO;
import com.kh.carlpion.auth.model.dto.FindIdDTO;
import com.kh.carlpion.auth.model.dto.FindPwDTO;
import com.kh.carlpion.auth.model.dto.LoginDTO;
import com.kh.carlpion.auth.model.dto.SocialDTO;
import com.kh.carlpion.auth.model.vo.CarlpionUserDetails;

public interface AuthService {

	CarlpionUserDetails getUserDetails();
	
	Map<String, String> login(LoginDTO loginInfo);
	
	String selectUsernameByCode(FindIdDTO findIdInfo);
	
	void sendTemporaryPasswordEmail(FindPwDTO findPwInfo);
	
	void sendVerifyEmailByType(EmailDTO emailInfo);
	
	SocialDTO checkSocialUser(SocialDTO socialLoginInfo);
	
	void signUpBySocial(SocialDTO socialSignUpInfo);
}
