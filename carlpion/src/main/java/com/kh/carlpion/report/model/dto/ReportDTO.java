package com.kh.carlpion.report.model.dto;

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
public class ReportDTO {
	private Long reportNo;
	private Long userNo;
	private String title;
	private String content;
	private String nickName;
	private Date createDate;
	private Long count;
	private String fileUrl;
	private Long point;
	private String userLevel;
	private boolean hasPermission;
}
