package com.kh.carlpion.comment.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
@Getter
public class CommentNoticeVO {
	private Long noticeNo;
	private Long userNo;
	private String content;
}
