package com.kh.carlpion.user.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.carlpion.auth.model.dao.AuthMapper;
import com.kh.carlpion.auth.model.vo.EmailVerifyVO;
import com.kh.carlpion.exception.exceptions.DuplicateValueException;
import com.kh.carlpion.exception.exceptions.EmailVerifyFailException;
import com.kh.carlpion.exception.exceptions.UnexpectSqlException;
import com.kh.carlpion.user.model.dao.UserMapper;
import com.kh.carlpion.user.model.dto.UserDTO;
import com.kh.carlpion.user.model.vo.UserVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserMapper userMapper;
	private final AuthMapper authMapper;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public void signUp(UserDTO user) {
		
		String username = user.getUsername();
		String password = user.getPassword();
		String nickname = user.getNickname();
		String realname = user.getRealname();
		String email = user.getEmail();
		String code = user.getCode();
		String type = "회원가입";
		
		// 아이디, 닉네임, 이메일 중복 확인
		if(userMapper.selectUserByUsername(username) != null) {
			throw new DuplicateValueException("이미 사용 중인 아이디 입니다.");
		}
		
		if(userMapper.selectUserByNickname(nickname) != null) {
			throw new DuplicateValueException("이미 사용 중인 닉네임 입니다.");
		}
		
		if(userMapper.selectUserByEmail(email) != null) {
			throw new DuplicateValueException("이미 사용 중인 이메일 입니다.");
		}
		
		// 이메일 인증 확인하고 성공 시, 해당 인증 정보 삭제
		EmailVerifyVO emailVerifyInfo = EmailVerifyVO.builder().email(email).type(type).code(code).build();
		
		if(authMapper.selectEmailVerifyInfoByCodeAndCompositePK(emailVerifyInfo) == null) {
			throw new EmailVerifyFailException("이메일 인증에 실패 했습니다. 코드를 다시 확인해 주세요.");
		}
		
		if(authMapper.deleteEmailVerifyInfo(emailVerifyInfo) != 1) {
			throw new UnexpectSqlException("예기치 않은 오류로 회원가입이 정상적으로 이루어지지 않았습니다.");
		}

		// 비밀번호 암호화하고 DB에 저장
		String encodedPassword = passwordEncoder.encode(password);
		
		UserVO userInfo = UserVO.builder()
								.username(username)
								.password(encodedPassword)
								.nickname(nickname)
								.realname(realname)
								.email(email)
								.build();
		
		if(userMapper.signUp(userInfo) != 1) {
			throw new UnexpectSqlException("예기치 않은 오류로 회원가입이 정상적으로 이루어지지 않았습니다.");
		}
	}
}
