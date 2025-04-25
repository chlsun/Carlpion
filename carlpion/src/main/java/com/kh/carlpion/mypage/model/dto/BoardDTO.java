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
public class BoardDTO {

	
	private String boardType;
	private Long boardNo;
	private String title;
	private Long count;
	private Date createDate;
	private String comment;
	private String fileUrl;
	private Long userNo;
	private Long reportNo;
	private Long reviewNo;
	private Long noticeNo;
	private Long postNo;
				
      
}
