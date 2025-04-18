package com.kh.carlpion.user.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

@Value
@Getter
@Builder
@ToString
public class UserVO {

	private String username;
	private String password;
	private String nickname;
	private String realname;
	private String email;
	
}
