package com.kh.carlpion.mypage.model.service;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.mypage.model.dto.MypageDTO;

public interface MypageService {
	
	
	MypageDTO updateNickName(MypageDTO mypage);
	
	void updatePassword(MypageDTO mypage);
	
	MypageDTO updateEmail(MypageDTO mypage);
	
	MypageDTO updateProfile(MultipartFile file, Long userNo);
	
	MypageDTO updateName(MypageDTO mypage);
	
	void deleteUser(MypageDTO mypage);
	
	//----------------------------------------------
	List<MypageDTO> replyCheck(Long userNo);
	
	List<MypageDTO> inquiryCheck(Long userNo);
	
	List<MypageDTO> reviewCheck(Long userNo);
	
	List<MypageDTO> pointCheck(Long userNo);
	
	//----------------------------------------------
	
	List<MypageDTO> reservations(Long userNo);
	
	List<MypageDTO> usedCars(Long userNo);
	
	
	
}
