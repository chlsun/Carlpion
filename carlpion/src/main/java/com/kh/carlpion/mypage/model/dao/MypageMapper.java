package com.kh.carlpion.mypage.model.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.mypage.model.dto.MypageDTO;

@Mapper
public interface MypageMapper {

	
	MypageDTO updateNickName(MypageDTO mypage);
	
	int checkNickName(MypageDTO mypage);
	
	void updatePassword(MypageDTO mypage);
	
	MypageDTO updateEmail(MypageDTO mypage);
	
	int checkEmail(MypageDTO mypage);
	
	MypageDTO updateProfile (MultipartFile file, long userNo);
	
	MypageDTO updateName(MypageDTO myapge);
	
	void deleteUser(MypageDTO mypage);
//--------------------------------------------------------
	List<MypageDTO> replyCheck(Long userNo);

	List<MypageDTO> inquiryCheck(Long userNo);

	List<MypageDTO> reviewCheck(Long userNo);






}
