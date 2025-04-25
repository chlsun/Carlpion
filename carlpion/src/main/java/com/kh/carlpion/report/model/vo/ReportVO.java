package com.kh.carlpion.report.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
@Getter
@AllArgsConstructor
public class ReportVO {
	private Long reportNo;
	private Long userNo;
	private String title;
	private String content;
	private String nickName;
	private String fileUrl;
}
