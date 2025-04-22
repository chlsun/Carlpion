package com.kh.carlpion.review.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
@Getter
@AllArgsConstructor
public class ReviewVO {
	private Long reviewNo;
	private Long userNo;
	private String title;
	private String content;
	private String fileUrl;
	private Date createDate;
	private Long count;
	private String isActive;
}
