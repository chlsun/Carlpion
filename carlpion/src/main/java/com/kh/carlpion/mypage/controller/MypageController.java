package com.kh.carlpion.mypage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	public ResponseEntity<MypageDTO> updateNickName(@RequestBody MypageDTO mypage) {
		MypageDTO result = mypageService.updateNickName(mypage);
		
		return ResponseEntity.ok(result);
		
	}
	
	@PutMapping("/users/update-pw")
	public ResponseEntity<?> updatePassword(@RequestBody MypageDTO mypage) {
	
		mypageService.updatePassword(mypage);
		return ResponseEntity.status(HttpStatus.OK).body("비밀번호 변경");
	}
	
	@PutMapping("/users/update-email")
	public ResponseEntity<MypageDTO> updateEmail(@RequestBody MypageDTO mypage) {
		MypageDTO result = mypageService.updateEmail(mypage);
		return ResponseEntity.ok(result);
		
	}
	
	@PutMapping("/users/update-profile")
	public ResponseEntity<MypageDTO> updateProfile(@RequestParam("file") MultipartFile file,
											        @RequestParam("userNo") Long userNo){
		MypageDTO result = mypageService.updateProfile(file, userNo);
		return ResponseEntity.ok(result);
		
	}
	@PutMapping("/users/update-relname")
	public ResponseEntity<MypageDTO> updateName(@RequestBody MypageDTO mypage){
		
		MypageDTO result = mypageService.updateName(mypage);
		return ResponseEntity.ok(result);
			
	}
		
	@DeleteMapping("/users")
	public ResponseEntity<String> deleteUser(@RequestBody MypageDTO mypage){
		
		mypageService.deleteUser(mypage);
		
		return ResponseEntity.ok("탈퇴에 성공하셨습니다.");
	} 
	
	
	
	
}
