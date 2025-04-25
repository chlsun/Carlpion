package com.kh.carlpion.point.model.dto;

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
public class PointDTO {
	private Long userNo;
	private Long point;
	private String userLevel;
}
