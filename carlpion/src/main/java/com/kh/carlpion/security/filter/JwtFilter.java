package com.kh.carlpion.security.filter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kh.carlpion.auth.model.vo.CarlpionUserDetails;
import com.kh.carlpion.token.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil util;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if(authorization == null || !authorization.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			
			return;
		}
		
		String token = authorization.substring(7);

		try {
			Claims claims = util.parseJwt(token);
			
			String issuer = claims.getIssuer();
			
			if(issuer.equals("https://accounts.google.com")) {
				filterChain.doFilter(request, response);
				
				return;
			}
			
			String username = claims.getSubject();
			
			CarlpionUserDetails user = (CarlpionUserDetails)userDetailsService.loadUserByUsername(username);
			
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			
			// 세부설정: 사용자의 IP주소, MAC주소, SessionID 등등
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		} catch (ExpiredJwtException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("text/plain;charset=UTF-8");
			response.getWriter().write("토큰이 만료 되었습니다.");
			response.getWriter().flush();
			
			return;
			
		} catch (JwtException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentType("text/plain;charset=UTF-8");
			response.getWriter().write("유효하지 않은 토큰입니다.");
			response.getWriter().flush();
			
			return;
		}
		
		filterChain.doFilter(request, response);
	}
	
}
