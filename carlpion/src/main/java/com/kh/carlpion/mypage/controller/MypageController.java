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
	public ResponseEntity<?> updateProfile(@RequestParam("file") MultipartFile file,
			@AuthenticationPrincipal CarlpionUserDetails user){
		Long userNo = user.getUserNo();
		
		MypageDTO dto = mypageService.updateProfile(file, userNo);
		return ResponseEntity.ok(dto);
		
	}
		
	@DeleteMapping("/users")
	public ResponseEntity<String> deleteUser(@AuthenticationPrincipal CarlpionUserDetails user,@RequestBody MypageDTO mypage){
		Long userNo = user.getUserNo();
		mypage.setUserNo(userNo);
		mypageService.deleteUser(mypage);
		
		return ResponseEntity.ok("탈퇴에 성공하셨습니다.");
	} 
	
	
	//------------------------------------------------------------
	
	@GetMapping("/mypage/comments")
	public ResponseEntity<Map<String, Object>> replyCheck(@AuthenticationPrincipal CarlpionUserDetails user,
														@RequestParam("limit")int limit,
														@RequestParam("offset") int offset){
		Long userNo = user.getUserNo();
		
		List<MypageDTO> replyList = mypageService.replyCheck(userNo,limit,offset);
		int totalCount = mypageService.replyCheckCount(userNo);
		Map<String, Object> result = new HashMap<>();
		result.put("replyList", replyList);
		result.put("totalCount", totalCount);
			return ResponseEntity.ok(result);
	}
	

	@GetMapping("/mypage/reports")
	public ResponseEntity<Map<String, Object>> inquiryCheck(@AuthenticationPrincipal CarlpionUserDetails user,
														@RequestParam("limit")int limit,
														@RequestParam("offset") int offset){
		Long userNo = user.getUserNo();
	List<MypageDTO> inquiryList	= mypageService.inquiryCheck(userNo,limit,offset);
	int totalCount = mypageService.replyCheckCount(userNo);
	Map<String, Object> result = new HashMap<>();
	result.put("inquiryList", inquiryList);
	result.put("totalCount", totalCount);
		return ResponseEntity.ok(result);
	
	}

	@GetMapping("/mypage/reviews")
	public ResponseEntity<Map<String, Object>> reviewCheck(@AuthenticationPrincipal CarlpionUserDetails user,
													@RequestParam("limit")int limit,
													@RequestParam("offset") int offset){
		Long userNo = user.getUserNo();
		List<MypageDTO> reviewList = mypageService.reviewCheck(userNo,limit,offset);
		int totalCount = mypageService.reviewCheckCount(userNo);
		Map<String, Object> result = new HashMap<>();
		result.put("reviewList", reviewList);
		result.put("totalCount", totalCount);
		
		return ResponseEntity.ok(result);
		
	} 
	
	@GetMapping("/mypage/points")
	public ResponseEntity<Map<String, Object>> pointCheck(@AuthenticationPrincipal CarlpionUserDetails user ,
														@RequestParam("limit")int limit,
														@RequestParam("offset") int offset){
		Long userNo= user.getUserNo();
		List<MypageDTO> pointList = mypageService.pointCheck(userNo, limit, offset);
		int totalCount = mypageService.pointCheckCount(userNo);
		
		Map<String, Object> result = new HashMap<>();
		result.put("pointList", pointList);
		result.put("totalCount", totalCount);
		
		return ResponseEntity.ok(result);
		
	}

	@GetMapping("/mypage/use")
	public ResponseEntity<?> reservations(@AuthenticationPrincipal CarlpionUserDetails user){
		Long userNo = user.getUserNo();
		System.out.println("값오나");
		List<ReservationHistoryDTO> result = mypageService.reservations(userNo);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/mypage/reservation")
	public ResponseEntity<?> getReservationList(){
		Map<String, Object> reservationList = mypageService.getReservationList();
		
		return ResponseEntity.status(HttpStatus.OK).body(reservationList);
	}
	

	
	
}
