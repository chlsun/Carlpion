package com.kh.carlpion.user.model.dto;

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
public class UserDTO {
	
	private Long userNo;
	
	@Size(min = 8, max = 20, message = "아이디 길이는 8~20자 사이여야 합니다.")
	@Pattern(regexp = "^[a-z][a-z0-9]{6,18}[0-9]$", message = "잘못된 아이디 형식입니다.")
	@NotBlank(message = "아이디는 비어있거나 공백이 포함될 수 없습니다.")
	private String username;
	
	@Size(min = 8, max = 30, message = "비밀번호 길이는 8~30자 사이여야 합니다.")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).\\S{8,30}$", message = "잘못된 비밀번호 형식입니다.")
	@NotBlank(message = "비밀번호는 비어있거나 공백이 포함될 수 없습니다.")
	private String password;
	
	@Size(min = 2, max = 10, message = "닉네임 길이는 2~10자 사이여야 합니다.")
	@Pattern(regexp = "^[\\uAC00-\\uD7A3a-zA-Z0-9]{2,10}$", message = "잘못된 닉네임 형식입니다.")
	@NotBlank(message = "닉네임은 비어있거나 공백이 포함될 수 없습니다.")
	private String nickname;
	
	@Size(min = 2, max = 30, message = "이름 길이는 한글 2~5자, 영어 2~30자 사이여야 합니다.")
	@Pattern(regexp = "^([a-zA-Z]{2,30}|[\\uAC00-\\uD7A3]{2,5})$", message = "잘못된 이름 형식입니다.")
	@NotBlank(message = "이름은 비어있거나 공백이 포함될 수 없습니다.")
	private String realname;
	
	@Size(min = 2, message = "이메일 길이는 2자 보다는 길어야 합니다.")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "잘못된 이메일 형식입니다.")
	@NotBlank(message = "이메일은 비어있거나 공백이 포함될 수 없습니다.")
	private String email;
	
	private String role;
}
