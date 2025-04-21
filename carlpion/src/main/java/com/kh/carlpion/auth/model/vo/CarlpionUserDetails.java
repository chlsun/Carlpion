package com.kh.carlpion.auth.model.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@Getter
@ToString
public class CarlpionUserDetails implements UserDetails {

	private static final long serialVersionUID = -8487810517261049492L;
	
	private Long userNo;
	private String username;
	private String password;
	private String nickname;
	private String realname;
	private String email;
	private Collection<? extends GrantedAuthority> authorities;
}
