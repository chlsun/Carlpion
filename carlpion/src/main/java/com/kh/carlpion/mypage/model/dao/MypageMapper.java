package com.kh.carlpion.mypage.model.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.mypage.model.dto.MypageDTO;

@Mapper
public interface MypageMapper {

	
	int updateNickName(Map<String, Object> checkNickName);
	
	MypageDTO selectNickName(Long userNo);
	
	int checkNickName(Map<String, Object> checkNickName);
	
	void updatePassword(MypageDTO mypage);
	
	String passowordCheck(Long userNo);
	
	MypageDTO getUserInfo(Long userNo);
	
	int checkEmail(MypageDTO mypage);
	
	int updateName(MypageDTO updateName);
	
	void updateProfile(@Param("userNo") Long userNo, @Param("fileUrl") String imgUrl);
	
	void deleteUser(MypageDTO mypage);
//--------------------------------------------------------
	List<MypageDTO> replyCheck(Long userNo);

	List<MypageDTO> inquiryCheck(Long userNo);

	List<MypageDTO> reviewCheck(Long userNo);
	
	List<MypageDTO> pointCheck(Map<String, Object> page);
	int pointCheckCount(Long userNo);

//--------------------------------------------------------

	List<MypageDTO> reservations(Long userNo);
	List<MypageDTO> usedCars(Long userNo);



	






	


}
