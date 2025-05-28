package com.kh.carlpion.notice.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
@Getter
@AllArgsConstructor
public class NoticeVO {
	private Long noticeNo;
	private Long userNo;
	private Long modifierNo;
	private String title;
	private String content;
	private Date modifyDate;
	private String nickName;
}
