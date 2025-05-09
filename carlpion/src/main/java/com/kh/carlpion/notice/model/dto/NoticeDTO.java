package com.kh.carlpion.notice.model.dto;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NoticeDTO {
	private Long noticeNo;
	private Long userNo;
	private Long modifierNo;
	private String title;
	private String content;
	private String nickName;
	private Date createDate;
	private Date modifyDate;
	private Long count;
	private List<String> fileUrls;
	private Long point;
	private String userLevel;
}
