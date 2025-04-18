package com.kh.carlpion.mypage.model.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.mypage.model.dto.MypageDTO;

public interface MypageService {
	
	
	ResponseEntity<String> updateNickName(MypageDTO mypage);
	
	void updatePassword(MypageDTO mypage);
	
	ResponseEntity<String> updateEmail(MypageDTO mypage);
	
	ResponseEntity<String> updateProfile(MultipartFile file, Long userNo);
	
	ResponseEntity<String> updateName(MypageDTO mypage);
}
