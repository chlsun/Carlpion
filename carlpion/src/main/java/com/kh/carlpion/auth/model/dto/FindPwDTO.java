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
public class FindPwDTO {

	@Size(min = 8, max = 20, message = "아이디 길이는 8~20자 사이여야 합니다.")
	@Pattern(regexp = "^[a-z][a-z0-9]{6,18}[0-9]$", message = "잘못된 아이디 형식입니다.")
	@NotBlank(message = "아이디는 비어있거나 공백이 포함될 수 없습니다.")
	private String username;
	
	@Size(min = 2, max = 30, message = "이름 길이는 한글 2~5자, 영어 2~30자 사이여야 합니다.")
	@Pattern(regexp = "^([a-zA-Z]{2,30}|[\\uAC00-\\uD7A3]{2,5})$", message = "잘못된 이름 형식입니다.")
	@NotBlank(message = "이름은 비어있거나 공백이 포함될 수 없습니다.")
	private String realname;
	
	@Size(min = 2, message = "이메일 길이는 2자 보다는 길어야 합니다.")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "잘못된 이메일 형식입니다.")
	@NotBlank(message = "이메일은 비어있거나 공백이 포함될 수 없습니다.")
	private String email;
	
	@Size(min = 8, max = 8, message = "인증코드 길이는 8자 입니다.")
	@Pattern(regexp = "^[A-Za-z0-9]{8}$", message = "잘못된 인증코드 형식입니다.")
	@NotBlank(message = "인증코드는 비어있거나 공백이 포함될 수 없습니다.")
	private String code;
}
