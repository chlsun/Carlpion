package com.kh.carlpion.point.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PointHistoryDTO {
	private Long historyNo;
	private Long userNo;
	private Long reviewNo;
	private Long pointChange;
	private String reason;
	private Date createDate;
}
