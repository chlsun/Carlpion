package com.kh.carlpion.user.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.carlpion.user.model.dto.UserDTO;
import com.kh.carlpion.user.model.vo.UserVO;

@Mapper
public interface UserMapper {
	
	UserDTO selectUserByUsername(String username);
	
	UserDTO selectUserByNickname(String nickname);
	
	UserDTO selectUserByEmail(String email);
	
	int signUp(UserVO userInfo);
}
