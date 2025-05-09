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
	
	void deleteUser(long userNo);
//--------------------------------------------------------
	List<MypageDTO> replyCheck(Map<String, Object> page);

	List<MypageDTO> inquiryCheck(Map<String, Object> page);

	List<MypageDTO> reviewCheck(Map<String, Object> page);
	
	List<MypageDTO> pointCheck(Map<String, Object> page);
	int pointCheckCount(Long userNo);
	int reviewCheckCount(Long userNo);
	int replyCheckCount(Long userNo);
	int inquiryCheckCount(Long userNo);


//--------------------------------------------------------

	List<MypageDTO> reservations(Long userNo);



	






	


}
