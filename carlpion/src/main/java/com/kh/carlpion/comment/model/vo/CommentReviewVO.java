package com.kh.carlpion.comment.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
@Getter
public class CommentReviewVO {
	private Long reviewNo;
	private Long userNo;
	private String content;
}
