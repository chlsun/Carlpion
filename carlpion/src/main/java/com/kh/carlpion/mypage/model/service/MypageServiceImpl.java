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
	public 	MypageDTO updateNickName(MypageDTO mypage) {
		MypageDTO result =  mapper.updateNickName(mypage);
		
		 return result;
	}
	
	@Override
	public void updatePassword(MypageDTO mypage) {
		mapper.updatePassword(mypage);
	}

	@Override
	public 	MypageDTO updateEmail(MypageDTO mypage) {
		MypageDTO result = mapper.updateEmail(mypage);
		return result;
	}

	@Override
	public 	MypageDTO updateProfile(MultipartFile file, Long userNo) {
		MypageDTO result = mapper.updateProfile(file,userNo);
	
		
		return result;
	}

	@Override
	public 	MypageDTO updateName(MypageDTO mypage) {
		MypageDTO result = mapper.updateName(mypage);
		
		return result;
	}

	


}
