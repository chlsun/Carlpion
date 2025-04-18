package com.kh.carlpion.mypage.model.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.mypage.model.dao.MypageMapper;
import com.kh.carlpion.mypage.model.dto.MypageDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MypageServiceImpl implements MypageService {

	private final MypageMapper mapper;
	
	
	@Override
	public ResponseEntity<String> updateNickName(MypageDTO mypage) {
		int result =  mapper.updateNickName(mypage);
		
		
		 
		 return ResponseEntity.status(HttpStatus.OK).body("닉네임 변경 성공");
	}
	
	@Override
	public void updatePassword(MypageDTO mypage) {
		mapper.updatePassword(mypage);
	}

	@Override
	public ResponseEntity<String> updateEmail(MypageDTO mypage) {
		 mapper.updateEmail(mypage);
		return ResponseEntity.status(HttpStatus.OK).body("이메일 변경 성공");
	}

	@Override
	public ResponseEntity<String> updateProfile(MultipartFile file, Long userNo) {
		 mapper.updateProfile(file,userNo);
	
		
		return ResponseEntity.status(HttpStatus.OK).body("프로필 수정 성공"); 
	}

	@Override
	public ResponseEntity<String> updateName(MypageDTO mypage) {
		mapper.updateName(mypage);
		
		return ResponseEntity.status(HttpStatus.OK).body("이름 수정 성공");
	}

	


}
