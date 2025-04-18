package com.kh.carlpion.token.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.carlpion.token.model.vo.RefreshTokenVO;

@Mapper
public interface TokenMapper {

	int insertToken(RefreshTokenVO refreshToken);
}
