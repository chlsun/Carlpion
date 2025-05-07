package com.kh.carlpion.mypage.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.auth.model.vo.CarlpionUserDetails;
import com.kh.carlpion.mypage.model.dto.MypageDTO;
import com.kh.carlpion.mypage.model.dto.PointDTO;
import com.kh.carlpion.mypage.model.service.MypageService;
import com.kh.carlpion.rental.model.dto.ReservationHistoryDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class MypageController {

	private final MypageService mypageService;
	
	
	@PutMapping("/users/update-nickname")
	public ResponseEntity<?> updateNickName(@AuthenticationPrincipal CarlpionUserDetails user, @RequestBody MypageDTO mypage) {
		Long userNo= user.getUserNo();
		String nickname = mypage.getNickName();
		  int result = mypageService.updateNickName(userNo,nickname);
		  
		  
		return ResponseEntity.ok(result);
		
	}
	
	@GetMapping("/mypage/selectNickname")
	public ResponseEntity<?> selectNickName(@AuthenticationPrincipal CarlpionUserDetails user){
			Long userNo = user.getUserNo();
			
			MypageDTO result = mypageService.selectNickName(userNo);
		return ResponseEntity.ok(result);
		
	}
	
	
	@PutMapping("/users/update-pw")
	public ResponseEntity<?> updatePassword(@AuthenticationPrincipal CarlpionUserDetails user,@RequestBody MypageDTO mypage) {
		Long userNo = user.getUserNo();
		mypage.setUserNo(userNo);
		String modifyPw = mypage.getModifyPw();
				
		mypageService.updatePassword(mypage);
		return ResponseEntity.status(HttpStatus.OK).body("비밀번호 변경");
	}
	
	
	
	@PutMapping("/users/update-realname")
	public ResponseEntity<?> updateName(@AuthenticationPrincipal CarlpionUserDetails user,@RequestBody MypageDTO mypage){
		Long userNo = user.getUserNo();
		mypage.setUserNo(userNo);
		int result = mypageService.updateName(mypage);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping("/users/getUserInfo")
	public  ResponseEntity<?> getUserInfo(@AuthenticationPrincipal CarlpionUserDetails user){
		Long userNo = user.getUserNo();
		
		MypageDTO  result = mypageService.getUserInfo(userNo);
		
		return ResponseEntity.ok(result);
		
	}
	
	
	
	@PutMapping("/users/update-profile")
	public ResponseEntity<MypageDTO> updateProfile(@RequestParam("file") MultipartFile file,
											        @RequestParam("userNo") Long userNo){
		MypageDTO result = mypageService.updateProfile(file, userNo);
		return ResponseEntity.ok(result);
		
	}
		
	@DeleteMapping("/users")
	public ResponseEntity<String> deleteUser(@RequestBody MypageDTO mypage){
		
		mypageService.deleteUser(mypage);
		
		return ResponseEntity.ok("탈퇴에 성공하셨습니다.");
	} 
	
	
	//------------------------------------------------------------
	
	@GetMapping("/mypage/comments")
	public ResponseEntity<List<MypageDTO>> replyCheck(@AuthenticationPrincipal CarlpionUserDetails user){
		Long userNo = user.getUserNo();
		
		List<MypageDTO> result = mypageService.replyCheck(userNo);
		System.out.println("댓글 컨트롤러 나오나");
		return ResponseEntity.ok(result);
	}
	

	@GetMapping("/mypage/reports")
	public ResponseEntity<List<MypageDTO>> inquiryCheck(@AuthenticationPrincipal CarlpionUserDetails user){
		Long userNo = user.getUserNo();
		System.out.println("문의 컨트롤러 나오나");
	List<MypageDTO> result	= mypageService.inquiryCheck(userNo);
		
		return ResponseEntity.ok(result);
	
	}

	@GetMapping("/mypage/reviews")
	public ResponseEntity<List<MypageDTO>> reviewCheck(@AuthenticationPrincipal CarlpionUserDetails user){
		Long userNo = user.getUserNo();
		List<MypageDTO> result = mypageService.reviewCheck(userNo);
		System.out.println(result);
		return ResponseEntity.ok(result);
		
	} 
	
	@GetMapping("/mypage/points")
	public ResponseEntity<List<MypageDTO>> pointCheck(@AuthenticationPrincipal CarlpionUserDetails user ,
														@RequestParam(defaultValue = "3")int limit,
														@RequestParam(defaultValue = "0") int offset){
		Long userNo= user.getUserNo();
		List<MypageDTO> result = mypageService.pointCheck(userNo, limit, offset);
		
		return ResponseEntity.ok(result);
		
	}

	@GetMapping("/mypage/reservations")
	public ResponseEntity<List<MypageDTO>> reservations(@AuthenticationPrincipal CarlpionUserDetails user){
		Long userNo = user.getUserNo();
		System.out.println("값오나");
		List<MypageDTO> result = mypageService.reservations(userNo);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/mypage/reservation")
	public ResponseEntity<?> getReservationList(){
		Map<String, Object> reservationList = mypageService.getReservationList();
		
		return ResponseEntity.status(HttpStatus.OK).body(reservationList);
	}
	
	@GetMapping("/mypage/usedCars")
	public ResponseEntity<List<MypageDTO>> usedCars(@AuthenticationPrincipal CarlpionUserDetails user){
		Long userNo = user.getUserNo();
		
			List<MypageDTO> result = mypageService.usedCars(userNo);
		return ResponseEntity.ok(result);
	}
 	
	
	
}
