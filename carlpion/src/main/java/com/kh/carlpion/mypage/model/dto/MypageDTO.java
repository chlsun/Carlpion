package com.kh.carlpion.mypage.model.dto;

import java.sql.Date;
import java.util.List;

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
	private Long historyNo;
	private Long pointChange;
	private String reason;
	private Long point;
	private String userLevel;
	private Long reviewNo;
	private String content;
	private Long commentNo;
	
	private Long reportNo;
	
	private Long fileNo;
	private String fileUrl;
	
	
	
	
	
	private String fileUrls;
	
	
	
	private String boardType;
	private Long boardNo;
	private String title;
	private Long count;
	private Date createDate;
	private String comment;
	
	
	
	
	
	
	
	
	
	
	
}
