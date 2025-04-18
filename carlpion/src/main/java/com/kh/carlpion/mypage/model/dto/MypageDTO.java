package com.kh.carlpion.mypage.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class MypageDTO {

	// 회원정보,파일
	private Long userNo; 
	private String userName;
	private String passWord;
	private String realName;
	private String email;
	private String nickName;
	private String imgUrl;
	
	// 포인트 이력
	private Long pointChange;
	private String reason;
	private Date createDate;
	private Long point;
	private String userLevel;
	
	// 리뷰 게시판 조회
	private Long reviewNo;
	private String title;
	private String content;
	private Long commentNo;
	
	// 신고/ 문의 게시판 조회
	private Long reportNo;
	
	
	//파일
	private Long fileNo;
	private String fileUrl;
	
	//자동차 예약현황,사용내역 조회
	private Long reservationNo;
	private String carId;
	private Date rentalDate;
	private Date returnDate;
	private Long totalPrice;
	private String status;
	private Date paymentCompletedAt;
	private Long modelNo;
	private String parkingAddr;
	private String parkingTitle;
	private String chargeType;
	private String carImgUrl;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
