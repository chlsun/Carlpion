package com.kh.carlpion.auth.model.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kh.carlpion.auth.model.vo.CarlpionUserDetails;
import com.kh.carlpion.exception.exceptions.CustomAuthenticationException;
import com.kh.carlpion.user.model.dao.UserMapper;
import com.kh.carlpion.user.model.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserDTO user = userMapper.selectUserByUsername(username);
		
		if(user == null) {
			throw new CustomAuthenticationException("아이디나 비밀번호를 잘못 입력 하셨습니다.");
		}
		
		return CarlpionUserDetails.builder().userNo(user.getUserNo())
											.username(user.getUsername())
											.password(user.getPassword())
											.nickname(user.getNickname())
											.realname(user.getRealname())
											.email(user.getEmail())
											.authorities(Collections.singletonList(new SimpleGrantedAuthority(user.getRole())))
											.build();
	}

}
