package com.kh.carlpion.mypage.model.service;


import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.mypage.model.dto.MypageDTO;
import com.kh.carlpion.rental.model.dto.ReservationHistoryDTO;

public interface MypageService {
	
	
	int updateNickName(Long userNo, String nickname);
	
	void updatePassword(MypageDTO mypage);
	
	MypageDTO getUserInfo(Long userNo);
	
	MypageDTO updateProfile(MultipartFile file, Long userNo);
	
	int updateName(MypageDTO mypage);
	
	void deleteUser(MypageDTO mypage);
	
	//----------------------------------------------
	List<MypageDTO> replyCheck(Long userNo,int limit, int offset);
	
	List<MypageDTO> inquiryCheck(Long userNo,int limit, int offset);
	
	List<MypageDTO> reviewCheck(Long userNo,int limit, int offset);
	
	List<MypageDTO> pointCheck(Long userNo,int limit, int offset);
	
	int pointCheckCount(Long userNo);
	
	int reviewCheckCount(Long userNo);
	int replyCheckCount(Long userNo);
	int inquiryCheckCount(Long userNo);
	
	//----------------------------------------------
	
	List<MypageDTO> reservations(Long userNo);
	

	MypageDTO selectNickName(Long userNo);
	
	Map<String, Object> getReservationList();




	


	
	
	
}
