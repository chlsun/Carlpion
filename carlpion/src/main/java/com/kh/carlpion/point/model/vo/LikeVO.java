package com.kh.carlpion.point.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
@Getter
@AllArgsConstructor
public class LikeVO {
	private Long reviewNo;
	private Long userNo;
}
