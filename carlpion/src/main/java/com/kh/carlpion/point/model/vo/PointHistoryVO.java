package com.kh.carlpion.point.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
@Getter
@AllArgsConstructor
public class PointHistoryVO {
	private Long historyNo;
	private Long userNo;
	private Long reviewNo;
	private Long pointChange;
	private String reason;
	private Date createDate;
}
