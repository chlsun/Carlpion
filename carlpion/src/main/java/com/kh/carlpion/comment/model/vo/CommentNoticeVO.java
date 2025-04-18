package com.kh.carlpion.comment.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
@Getter
public class CommentNoticeVO {
	private Long commentNo;
	private Long noticeNo;
	private Long userNo;
	private String content;
	private Date createDate;
	private String isActive;
}
