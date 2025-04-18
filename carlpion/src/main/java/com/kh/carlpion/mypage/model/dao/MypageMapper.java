package com.kh.carlpion.mypage.model.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.mypage.model.dto.MypageDTO;

@Mapper
public interface MypageMapper {

	
	int updateNickName(MypageDTO mypage);
	
	void updatePassword(MypageDTO mypage);
	
	int updateEmail(MypageDTO mypage);
	
	int updateProfile (MultipartFile file, long userNo);
	
	int updateName(MypageDTO myapge);
}
