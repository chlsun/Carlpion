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
public class PointDTO {

	// 포인트 이력
	private Long userNo;
	private String userName;
	private String realName;
	private Date createDate;
	private Long pointChange;
	private String reason;
	private Long point;
	private String userLevel;
	private Long reviewNo;
	private String content;
	private Long commentNo;
	private Long historyNo;
	private Long reportNo;
	
	private Long fileNo;
	private String fileUrl;
	
	private Long limit;
	private Long offset;
	
	
	
	
	
	
	
}
