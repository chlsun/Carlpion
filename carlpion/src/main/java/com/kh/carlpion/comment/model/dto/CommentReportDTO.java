package com.kh.carlpion.comment.model.dto;

import java.sql.Date;

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
public class CommentReportDTO {
	private Long commentNo;
	private Long reportNo;
	private Long userNo;
	private String content;
	private String nickName;
	private Date createDate;
	private String isActive;
}
