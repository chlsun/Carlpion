package com.kh.carlpion.mypage.model.service;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.exception.exceptions.EmailDuplicateException;
import com.kh.carlpion.exception.exceptions.NickNameDuplicateException;
import com.kh.carlpion.mypage.model.dao.MypageMapper;
import com.kh.carlpion.mypage.model.dto.MypageDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MypageServiceImpl implements MypageService {

	private final MypageMapper mapper;
	
	@Override
	public 	MypageDTO updateNickName(MypageDTO mypage) {
		
			int checkNickName =	mapper.checkNickName(mypage);
			
			if(checkNickName > 0) {
				throw new NickNameDuplicateException("이미 존재하는 닉네임 입니다.");
			}
			
		MypageDTO result =  mapper.updateNickName(mypage);
		
		 return result;
	}
	
	@Override
	public void updatePassword(MypageDTO mypage) {
		mapper.updatePassword(mypage);
	}

	@Override
	public 	MypageDTO updateEmail(MypageDTO mypage) {
		int checkEmail = mapper.checkEmail(mypage);
		
		if(checkEmail > 0 ) {
			throw new EmailDuplicateException("이미 존재하는 이메일 입니다.");
			
		}	
		
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

	
	@Override
	public void deleteUser(MypageDTO mypage) {
		mapper.deleteUser(mypage);
	}

	//--------------------------------------------------------
	public List<MypageDTO> replyCheck(String userName){
		
		List<MypageDTO> result = mapper.replyCheck(userName);
		
		return result;
		
	}
	
	@Override
	public List<MypageDTO> inquiryCheck(String userName) {
	
		List<MypageDTO> result = mapper.inquiryCheck(userName);
		
		return result;
	}
	
	@Override
	public List<MypageDTO> reviewCheck(String userName) {
		
		
		List<MypageDTO> result =mapper.pointCheck(userName);
		
		
		return result;
	}

	@Override
	public List<MypageDTO> pointCheck(String userName) {
		
		List<MypageDTO> result =mapper.pointCheck(userName);
		return result;
	}



}
