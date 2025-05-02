package com.kh.carlpion.mypage.model.service;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.mypage.model.dto.MypageDTO;

public interface MypageService {
	
	
	int updateNickName(Long userNo, String nickname);
	
	void updatePassword(MypageDTO mypage);
	
	MypageDTO getUserInfo(Long userNo);
	
	MypageDTO updateProfile(MultipartFile file, Long userNo);
	
	int updateName(MypageDTO mypage);
	
	void deleteUser(MypageDTO mypage);
	
	//----------------------------------------------
	List<MypageDTO> replyCheck(Long userNo);
	
	List<MypageDTO> inquiryCheck(Long userNo);
	
	List<MypageDTO> reviewCheck(Long userNo);
	
	List<MypageDTO> pointCheck(Long userNo,int limit, int offset);
	
	//----------------------------------------------
	
	List<MypageDTO> reservations(Long userNo);
	
	List<MypageDTO> usedCars(Long userNo);

	MypageDTO selectNickName(Long userNo);

	


	
	
	
}
