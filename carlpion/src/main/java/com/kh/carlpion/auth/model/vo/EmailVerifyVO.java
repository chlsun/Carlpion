package com.kh.carlpion.auth.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@Getter
@ToString
public class EmailVerifyVO {

	private String email;
	private String code;
	private String type;
}
