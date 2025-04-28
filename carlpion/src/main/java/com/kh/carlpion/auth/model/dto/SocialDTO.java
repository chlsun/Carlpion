package com.kh.carlpion.auth.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SocialDTO {

	@NotBlank
	private String socialId;
	
	@NotBlank
	private String platform;
	
	private String nickname;
	
	private String realname;
	
	@Size(min = 2, message = "이메일 길이는 2자 보다는 길어야 합니다.")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "잘못된 이메일 형식입니다.")
	@NotBlank(message = "이메일은 비어있거나 공백이 포함될 수 없습니다.")
	private String email;
	
	private String role;
	
	private String createDate;
	
	private String isActive;
}
