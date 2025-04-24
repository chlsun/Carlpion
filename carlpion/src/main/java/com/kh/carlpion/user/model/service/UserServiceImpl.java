package com.kh.carlpion.user.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.carlpion.exception.exceptions.DuplicateValueException;
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
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public void signUp(UserDTO user) {
		
		String username = user.getUsername();
		String password = user.getPassword();
		String nickname = user.getNickname();
		String realname = user.getRealname();
		String email = user.getEmail();
		
		if(userMapper.selectUserByUsername(username) != null) {
			throw new DuplicateValueException("이미 사용 중인 아이디 입니다.");
		}
		
		if(userMapper.selectUserByNickname(nickname) != null) {
			throw new DuplicateValueException("이미 사용 중인 닉네임 입니다.");
		}
		
		if(userMapper.selectUserByEmail(email) != null) {
			throw new DuplicateValueException("이미 사용 중인 이메일 입니다.");
		}

		String encodedPassword = passwordEncoder.encode(password);
		
		UserVO userInfo = UserVO.builder()
								.username(username)
								.password(encodedPassword)
								.nickname(nickname)
								.realname(realname)
								.email(email)
								.build();
		
		if(userMapper.signUp(userInfo) != 1) {
			throw new UnexpectSqlException("예기치 않은 데이터베이스 측 오류로 회원가입이 정상적으로 이루어지지 않았습니다.");
		}	
	}
}
