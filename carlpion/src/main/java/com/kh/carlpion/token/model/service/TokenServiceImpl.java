package com.kh.carlpion.token.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.carlpion.exception.exceptions.UnexpectSqlException;
import com.kh.carlpion.token.model.dao.TokenMapper;
import com.kh.carlpion.token.model.vo.RefreshTokenVO;
import com.kh.carlpion.token.util.JwtUtil;
import com.kh.carlpion.user.model.dao.UserMapper;
import com.kh.carlpion.user.model.dto.UserDTO;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

	private final JwtUtil jwtUtil;
	private final TokenMapper tokenMapper;
	private final UserMapper userMapper;
	
	@Override
	public String generateAccessToken(String username) {

		String accessToken = jwtUtil.getAccessToken(username);
		
		return accessToken;
	}

	@Override
	public String generateRefreshToken(Long userNo, String username) {
		
		String refreshToken = jwtUtil.getRefreshToken(username);
		
		RefreshTokenVO refreshTokenVO = RefreshTokenVO.builder()
													  .userNo(userNo)
													  .token(refreshToken)
													  .expirationDate(System.currentTimeMillis() + 36000000L * 24 * 7)
													  .build();
		
		if(tokenMapper.insertToken(refreshTokenVO) != 1) {
			throw new UnexpectSqlException("예기치 않은 데이터베이스 측 오류로 로그인이 정상적으로 이루어지지 않았습니다.");
		}
		
		return refreshToken;
	}

	@Override
	public Map<String, String> loginByRefreshToken(String refreshToken) {
		
		RefreshTokenVO token = RefreshTokenVO.builder().token(refreshToken).build();
		
		RefreshTokenVO responseToken = tokenMapper.selectToken(token);
		
		Claims claims = jwtUtil.parseJwt(refreshToken);
		
		String username = claims.getSubject();
		
		Long userNo = responseToken.getUserNo();
		
		// 기존 토큰 삭제
		if(tokenMapper.deleteToken(responseToken) != 1) {
			throw new UnexpectSqlException("예기치 않은 데이터베이스 측 오류로 로그인이 정상적으로 이루어지지 않았습니다.");
		}
		
		String newRefreshToken = jwtUtil.getRefreshToken(username);
		
		RefreshTokenVO newRefreshTokenVO = RefreshTokenVO.builder()
														 .userNo(userNo)
														 .token(newRefreshToken)
														 .expirationDate(System.currentTimeMillis() + 36000000L * 24 * 7)
														 .build();
		
		// 새로운 토큰 DB에 삽입
		if(tokenMapper.insertToken(newRefreshTokenVO) != 1) {
			throw new UnexpectSqlException("예기치 않은 데이터베이스 측 오류로 로그인이 정상적으로 이루어지지 않았습니다.");
		}
		
		UserDTO user = userMapper.selectUserByUsername(username);
		
		Map<String, String> loginResponse = new HashMap<String, String>();
		
		loginResponse.put("username", user.getUsername());
		loginResponse.put("nickname", user.getNickname());
		loginResponse.put("realname", user.getRealname());
		loginResponse.put("email", user.getEmail());
		loginResponse.put("accessToken", generateAccessToken(username)) ;
		loginResponse.put("refreshToken", newRefreshToken);
		
		return loginResponse;
	}

	@Override
	public void deleteToken(String refreshToken) {
		
		tokenMapper.deleteToken(RefreshTokenVO.builder().token(refreshToken).build());
	}

}
