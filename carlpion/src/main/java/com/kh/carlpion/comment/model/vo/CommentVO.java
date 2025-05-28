package com.kh.carlpion.comment.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
@Getter
public class CommentVO {	
	private String commentType;	
	private Long commentNo;
	private Long userNo;
	private String content;	
	private Long boardNo;
}
