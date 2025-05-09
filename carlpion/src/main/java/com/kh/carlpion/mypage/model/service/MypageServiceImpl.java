package com.kh.carlpion.mypage.model.service;



import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.session.RowBounds;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.auth.model.service.AuthService;
import com.kh.carlpion.exception.exceptions.CarNotFoundException;
import com.kh.carlpion.exception.exceptions.IllegalArgumentPwException;
import com.kh.carlpion.exception.exceptions.NickNameDuplicateException;
import com.kh.carlpion.mypage.model.dao.MypageMapper;
import com.kh.carlpion.mypage.model.dto.MypageDTO;
import com.kh.carlpion.rental.model.dao.RentalMapper;
import com.kh.carlpion.rental.model.dto.ReservationHistoryDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MypageServiceImpl implements MypageService {

	private final MypageMapper mapper;
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	private final AuthService authService;
	private final RentalMapper rentalMapper;
	
	@Override
	public int updateNickName(Long userNo, String nickname) {
		
			Map<String, Object> checkNickName = new HashMap<>();
			
			checkNickName.put("userNo", userNo);
			checkNickName.put("nickName", nickname);
			int check =	mapper.checkNickName(checkNickName);
			
			if(check > 0) {
				throw new NickNameDuplicateException("이미 존재하는 닉네임 입니다.");
			}
			
			int result =  mapper.updateNickName(checkNickName);
		
		 return result;
	}
	
	@Override
	public MypageDTO selectNickName(Long userNo) {
		MypageDTO result = mapper.selectNickName(userNo);
		
		return result;
	}

	
	@Override
	public void updatePassword(MypageDTO mypage) {
		
				String passwordCheck = mapper.passowordCheck(mypage.getUserNo());
				if(!passwordEncoder.matches(mypage.getPassword(),passwordCheck))
					throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
					
				String newEncodedPw  = passwordEncoder.encode(mypage.getModifyPw());
				mypage.setModifyPw(newEncodedPw);
				 mapper.updatePassword(mypage);
				
	}		



	@Override
	public MypageDTO getUserInfo(Long userNo) {
		MypageDTO result = mapper.getUserInfo(userNo);
		
		
		return result;
	}
	
	
	
	@Override
	public 	int updateName(MypageDTO mypage) {
		
		int result = mapper.updateName(mypage);
					
		return result;
	}
	
	
	@Override
	public MypageDTO updateProfile(MultipartFile file, Long userNo) {
		String path = new File("").getAbsolutePath() + "/uploads/profile/";
		File uploadPath = new File(path);
		if(!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		String originalFilename = file.getOriginalFilename();
		String fileName = UUID.randomUUID() + "_" + originalFilename;
		
		File dset = new File(uploadPath,fileName);
		
		
		try {
	        file.transferTo(dset); 
	    } catch (IOException e) {
	        throw new RuntimeException("파일 저장 실패", e);
	    }
		String imgUrl = "http://localhost:80/uploads/profile/" + fileName;
		mapper.updateProfile(userNo,imgUrl);
		
		MypageDTO dto = new MypageDTO();
		dto.setFileUrl(imgUrl);
		
		return dto;
	}


	
	@Override
	public void deleteUser(MypageDTO mypage) {
		String passwordCheck = mapper.passowordCheck(mypage.getUserNo());
		if(!passwordEncoder.matches(mypage.getPassword(), passwordCheck)) {
			
			throw new IllegalArgumentPwException("현재 비밀번호가 일치하지 않습니다.");
		}
		mapper.deleteUser(mypage.getUserNo());
	}

	//--------------------------------------------------------
	@Override
	public List<MypageDTO> replyCheck(Long userNo,int limit, int offset){
		int startRow = offset;
		int endRow = offset + limit;
		
		Map<String,Object> page = new HashMap<>();
		page.put("userNo", userNo);
		page.put("startRow", startRow);
		page.put("endRow", endRow);
		
		List<MypageDTO> result = mapper.replyCheck(page);
		return result;
		
	}
	@Override
	public int replyCheckCount(Long userNo) {
		return mapper.replyCheckCount(userNo);
	}
	
	@Override
	public List<MypageDTO> inquiryCheck(Long userNo,int limit, int offset) {
		int startRow = offset;
		int endRow = offset + limit;
		
		Map<String,Object> page = new HashMap<>();
		page.put("userNo", userNo);
		page.put("startRow", startRow);
		page.put("endRow", endRow);
		
		List<MypageDTO> result = mapper.inquiryCheck(page);
		return result;
	}
	@Override
	public int inquiryCheckCount(Long userNo) {
		return mapper.inquiryCheckCount(userNo);
	}
	
	@Override
	public List<MypageDTO> reviewCheck(Long userNo,int limit, int offset) {
		
		int startRow = offset;
		int endRow = offset + limit;
		
		Map<String,Object> page = new HashMap<>();
		page.put("userNo", userNo);
		page.put("startRow", startRow);
		page.put("endRow", endRow);
		
		List<MypageDTO> result =mapper.reviewCheck(page);
		
		return result;
	}

	@Override
	public int reviewCheckCount(Long userNo) {
		return mapper.reviewCheckCount(userNo);
	}
	
	@Override
	public List<MypageDTO> pointCheck(Long userNo,int limit, int offset) {
		
		int startRow = offset;
		int endRow = offset + limit;
		
		Map<String,Object> page = new HashMap<>();
		page.put("userNo", userNo);
		page.put("startRow", startRow);
		page.put("endRow", endRow);
		List<MypageDTO> result =mapper.pointCheck(page);
		return result;
	}
	@Override
	public int pointCheckCount(Long userNo) {
	
		return mapper.pointCheckCount(userNo);
	}
	
	
	//--------------------------------------------------------
	@Override
	public List<ReservationHistoryDTO> reservations(Long userNo) {
		
		RowBounds rowBounds = new RowBounds(0, 3);
		
		List<ReservationHistoryDTO> result = rentalMapper.getRentHistory(rowBounds, userNo);
	
		
		return result;
	}
	
	
	




	@Override
	public Map<String, Object> getReservationList() {
		
		long userNo = authService.getUserDetails().getUserNo();
		
		int reservationCount = rentalMapper.getReservationCount(userNo);
		
		if(reservationCount < 1) {
			throw new CarNotFoundException("조회결과 없음");
		}
		
		RowBounds rowBounds = new RowBounds(0, 3);
		
		List<ReservationHistoryDTO> result = rentalMapper.getReservationList(rowBounds, userNo);
		
		
		Map<String, Object> reservationList = new HashMap();
		
		reservationList.put("reservation", result);
		
		reservationList.put("reservationCount", reservationCount);
		
		return reservationList;
	}

	
	


	

	

}
