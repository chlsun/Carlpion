package com.kh.carlpion.auth.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kh.carlpion.auth.model.dao.AuthMapper;
import com.kh.carlpion.auth.model.dto.EmailDTO;
import com.kh.carlpion.auth.model.dto.LoginDTO;
import com.kh.carlpion.auth.model.vo.CarlpionUserDetails;
import com.kh.carlpion.auth.model.vo.EmailVerifyVO;
import com.kh.carlpion.exception.exceptions.CustomAuthenticationException;
import com.kh.carlpion.exception.exceptions.CustomMessagingException;
import com.kh.carlpion.exception.exceptions.DuplicateValueException;
import com.kh.carlpion.exception.exceptions.UnexpectSqlException;
import com.kh.carlpion.token.model.service.TokenService;
import com.kh.carlpion.user.model.dao.UserMapper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;
	private final UserMapper userMapper;
	private final AuthMapper authMapper;
//	private final JavaMailSender mailSender;
	
	// 요청 시, 액세스 토큰으로 인증했을 때, 유저 정보를 반환하는 메서드
	@Override
	public CarlpionUserDetails getUserDetails() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		CarlpionUserDetails user = (CarlpionUserDetails)authentication.getPrincipal();
		
		return user;
	}
	
	// 로그인하고 토큰과 유저 정보를 반환하는 메서드
	@Override
	public Map<String, String> login(LoginDTO loginInfo) {
		
		String username = loginInfo.getUsername();
		String password = loginInfo.getPassword();
		boolean isKeep = loginInfo.isKeep();
		
		Authentication authentication = null;
		
		try {
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
		} catch (AuthenticationException e) {
			throw new CustomAuthenticationException("아이디나 비밀번호를 잘못 입력 하셨습니다.");
		}
		
		CarlpionUserDetails user = (CarlpionUserDetails)authentication.getPrincipal();
		
		Map<String, String> loginResponse = new HashMap<String, String>();
		
		loginResponse.put("username", user.getUsername());
		loginResponse.put("nickname", user.getNickname());
		loginResponse.put("realname", user.getRealname());
		loginResponse.put("email", user.getEmail());
		loginResponse.put("accessToken", tokenService.generateAccessToken(user.getUsername()));
		
		if(isKeep) {
			loginResponse.put("refreshToken", tokenService.generateRefreshToken(user.getUserNo(), user.getUsername()));
		}

		return loginResponse;
	}

	// 회원가입 시, 인증 이메일을 전송하는 메서드
//	@Override
//	public void sendVerifyEmailWhileSignUp(EmailDTO emailInfo) {
//		
//		String email = emailInfo.getEmail();
//        String type = emailInfo.getType();
//        
//        // 이메일 인증 중복 확인
//        EmailVerifyVO emailVerifyInfoCheck = authMapper.selectEmailVerifyInfoByCompositePK(EmailVerifyVO.builder().email(email).type(type).build());
//        
//        if(emailVerifyInfoCheck != null) {
//        	throw new DuplicateValueException("이미 인증 메일이 전송 되었습니다. 메일함을 확인해 주세요.");
//        }
//		
//		// 이메일 중복 확인
//		if(userMapper.selectUserByEmail(email) != null) {
//			throw new DuplicateValueException("이미 사용 중인 이메일 입니다.");
//		}
//		
//		// 메일 제목 생성
//        StringBuilder mailTitleSB = new StringBuilder();
//        
//        mailTitleSB.append("Carlpion ");
//        mailTitleSB.append(type);
//        mailTitleSB.append(" 인증코드");
//        
//        String mailTitle = mailTitleSB.toString();
//		
//		// 무작위 인증 코드 생성
//		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//		
//        StringBuilder verifyCodeSB = new StringBuilder(8);
//
//        for (int i = 0; i < 8; i++) {
//        	
//            int randomIndex = (int)(Math.random() * characters.length());
//            
//            verifyCodeSB.append(characters.charAt(randomIndex));
//        }
//        
//        String verifyCode = verifyCodeSB.toString();
//        
//        // 메일 내용인 HTML 문서 생성
//        StringBuilder mailContentSB = new StringBuilder();
//        
//        mailContentSB.append("<!DOCTYPE html><html lang=\"ko\"><head><meta charset=\"UTF-8\"/><meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0\"/><title>Carlpion 인증코드</title><style>@import url(\"https://fastly.jsdelivr.net/gh/projectnoonnu/2408-5@1.0/HakgyoansimAllimjangTTF-R.woff2 \");@font-face{font-family:\"HakgyoansimAllimjangTTF-R\";src:url(\"https://fastly.jsdelivr.net/gh/projectnoonnu/2408-5@1.0/HakgyoansimAllimjangTTF-R.woff2 \") format(\"woff2\");font-weight:400;font-style:normal}</style></head><body><div style=\"width:40rem;background-color:white;border:4px solid #35bdf2;border-radius:1.5rem;display:flex;flex-direction:column;align-items:center;font-family:HakgyoansimAllimjangTTF-R;\"><div style=\"width:100%;padding:1rem 0;border-bottom:3px solid #35bdf2;font-family:HakgyoansimAllimjangTTF-R;font-size:1.875rem;color:#35bdf2;letter-spacing:0.1rem;display:flex;justify-content:center;\">Carlpion 인증코드</div><div style=\"width:100%;padding:2.5rem 0;border-bottom:3px solid #35bdf2;font-family:HakgyoansimAllimjangTTF-R;font-size:1.5rem;letter-spacing:0.1rem;display:flex;justify-content:center;\">");
//        mailContentSB.append(verifyCode);
//        mailContentSB.append("</div><div style=\"width:100%;padding:1rem 0;font-family:HakgyoansimAllimjangTTF-R;font-size:1rem;letter-spacing:0.1rem;display:flex;justify-content:center;\">위 코드를 입력하고 ");
//        mailContentSB.append(type);
//        mailContentSB.append(" 버튼을 누르세요.</div></div></body></html>");
//        
//        String mailContent = mailContentSB.toString();
//        
//        // 메일 작성
//        MimeMessage message = mailSender.createMimeMessage();
//		
//		MimeMessageHelper helper = null;
//		
//		try {
//			helper = new MimeMessageHelper(message, false, "UTF-8");
//			
//			helper.setSubject(mailTitle);
//			helper.setText(mailContent, true);
//			helper.setTo(email);
//			
//		} catch (MessagingException e) {
//			throw new CustomMessagingException("인증코드 메일 전송에 실패 했습니다. 나중에 다시 시도해 주세요.");
//		}
//		
//		// 이메일 주소와 인증 코드 DB에 저장
//		EmailVerifyVO emailVerifyInfo = EmailVerifyVO.builder().email(email).code(verifyCode).type(type).build();
//		
//		if(authMapper.insertEmailVerifyInfo(emailVerifyInfo) != 1) {
//			throw new UnexpectSqlException("인증 과정에 문제가 발생 했습니다. 나중에 다시 시도해 주세요.");
//		}
//
//		// 메일 전송
//		mailSender.send(message);
//	}
}
