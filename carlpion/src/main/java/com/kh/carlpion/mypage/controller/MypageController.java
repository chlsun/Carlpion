package com.kh.carlpion.mypage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.mypage.model.dto.MypageDTO;
import com.kh.carlpion.mypage.model.service.MypageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class MypageController {

	private final MypageService mypageService;
	
	
	@PutMapping("/users/update-nickname")
	public ResponseEntity<?> updateNickName(@RequestBody MypageDTO mypage) {
	
		return mypageService.updateNickName(mypage);
		
	}
	
	@PutMapping("/users/update-pw")
	public ResponseEntity<?> updatePassword(@RequestBody MypageDTO mypage) {
	
		mypageService.updatePassword(mypage);
		return ResponseEntity.status(HttpStatus.OK).body("비밀번호 변경");
	}
	
	@PutMapping("/users/update-email")
	public ResponseEntity<?> updateEmail(@RequestBody MypageDTO mypage) {
		
		return mypageService.updateEmail(mypage);
		
	}
	
	@PutMapping("/users/update-profile")
	public ResponseEntity<String> updateProfile(@RequestParam("file") MultipartFile file,
											        @RequestParam("userNo") Long userNo){
			
		return mypageService.updateProfile(file,userNo);
		
	}
	@PutMapping("/users/update-relname")
	public ResponseEntity<String> updateName(@RequestBody MypageDTO mypage){
		
			return mypageService.updateName(mypage);
			
	}
		
	
	
	
	
	
	
}
