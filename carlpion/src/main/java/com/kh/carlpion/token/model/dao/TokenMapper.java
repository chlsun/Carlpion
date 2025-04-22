package com.kh.carlpion.token.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.carlpion.token.model.vo.RefreshTokenVO;

@Mapper
public interface TokenMapper {

	RefreshTokenVO selectToken(RefreshTokenVO refreshToken);
	
	int insertToken(RefreshTokenVO refreshToken);
	
	int deleteToken(RefreshTokenVO refreshToken);
}
