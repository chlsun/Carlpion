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

	private Long userNo; 
	private String userName;
	private String passWord;
	private String realName;
	private String email;
	private String nickName;
	private String imgUrl;
	

	
	
	
}
