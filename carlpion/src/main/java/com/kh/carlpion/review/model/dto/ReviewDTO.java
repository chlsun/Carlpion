package com.kh.carlpion.review.model.dto;

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
public class ReviewDTO {
	private Long reviewNo;
	private Long userNo;
	private String title;
	private String content;
	private String fileUrl;
	private Date createDate;
	private Long count;
	private String isActive;	
}
