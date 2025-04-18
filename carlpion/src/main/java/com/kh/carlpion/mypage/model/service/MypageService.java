package com.kh.carlpion.mypage.model.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.mypage.model.dto.MypageDTO;

public interface MypageService {
	
	
	MypageDTO updateNickName(MypageDTO mypage);
	
	void updatePassword(MypageDTO mypage);
	
	MypageDTO updateEmail(MypageDTO mypage);
	
	MypageDTO updateProfile(MultipartFile file, Long userNo);
	
	MypageDTO updateName(MypageDTO mypage);
}
