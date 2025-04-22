package com.kh.carlpion.token.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

@Value
@Getter
@Builder
@ToString
public class RefreshTokenVO {

	private Long userNo;
	private String token;
	private Long expirationDate;
}
