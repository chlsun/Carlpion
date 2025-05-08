package com.kh.carlpion.point.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
@Getter
@AllArgsConstructor
public class PointHistoryVO {
	private Long userNo;
	private Long pointChange;
	private String reason;
}
