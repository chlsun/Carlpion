package com.kh.carlpion.comment.model.dto;

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
public class CommentDynamicDTO {
	private String commentType;
	private Long commentNo;
	private Long userNo;
	private String content;
	
	private Long noticeNo;
	private Long reportNo;
	private Long reviewNo;
}
