package com.kh.carlpion.auth.model.service;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.carlpion.auth.model.dao.AuthMapper;
import com.kh.carlpion.auth.model.dto.EmailDTO;
import com.kh.carlpion.auth.model.dto.FindIdDTO;
import com.kh.carlpion.auth.model.dto.FindPwDTO;
import com.kh.carlpion.auth.model.dto.LoginDTO;
import com.kh.carlpion.auth.model.dto.SocialDTO;
import com.kh.carlpion.auth.model.vo.CarlpionUserDetails;
import com.kh.carlpion.auth.model.vo.EmailVerifyVO;
import com.kh.carlpion.exception.exceptions.CustomAuthenticationException;
import com.kh.carlpion.exception.exceptions.CustomMessagingException;
import com.kh.carlpion.exception.exceptions.DuplicateValueException;
import com.kh.carlpion.exception.exceptions.EmailVerifyFailException;
import com.kh.carlpion.exception.exceptions.UnexpectSqlException;
import com.kh.carlpion.token.model.service.TokenService;
import com.kh.carlpion.user.model.dao.UserMapper;
import com.kh.carlpion.user.model.dto.UserDTO;
import com.kh.carlpion.user.model.vo.UserVO;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;
	private final AuthMapper authMapper;
	private final UserMapper userMapper;
	private final JavaMailSender mailSender;
	private final PasswordEncoder passwordEncoder;
	
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
	
	// 인증코드로 이메일 인증하고 해당 이메일로 생성된 아이디를 반환하는 메서드
	@Override
	public String selectUsernameByCode(FindIdDTO findIdInfo) {
		
		String realname = findIdInfo.getRealname();
		String email = findIdInfo.getEmail();
		String code = findIdInfo.getCode();
		String type = "아이디 찾기";
		
		// 이메일 인증 확인하고 성공 시, 해당 인증 정보 삭제
		EmailVerifyVO emailVerifyInfo = EmailVerifyVO.builder().email(email).type(type).code(code).build();
		
		if(authMapper.selectEmailVerifyInfoByCodeAndCompositePK(emailVerifyInfo) == null) {
			throw new EmailVerifyFailException("이메일 인증에 실패 했습니다. 코드를 다시 확인해 주세요.");
		}
		
		if(authMapper.deleteEmailVerifyInfo(emailVerifyInfo) != 1) {
			throw new UnexpectSqlException("예기치 않은 오류로 아이디 찾기가 정상적으로 이루어지지 않았습니다.");
		}
		
		// 이름과 이메일로 아이디 찾고 반환
		UserVO userInfo = UserVO.builder().realname(realname).email(email).build();
		
		String username = authMapper.selectUsernameByEmailAndRealname(userInfo);
		
		if(username == null) {
			throw new InvalidParameterException("이름이 일치하지 않습니다.");
		}
		
		return username;
	}
	
	// 인증 코드로 인증하고 임시 비밀번호 생성하고 이메일로 전송하는 메서드
	@Override
	public void sendTemporaryPasswordEmail(FindPwDTO findPwInfo) {
		
		String username = findPwInfo.getUsername();
		String realname = findPwInfo.getRealname();
		String email = findPwInfo.getEmail();
		String code = findPwInfo.getCode();
		String type = "비밀번호 찾기";
		
		// 이메일 인증 확인하고 성공 시, 해당 인증 정보 삭제
		EmailVerifyVO emailVerifyInfo = EmailVerifyVO.builder().email(email).type(type).code(code).build();
		
		if(authMapper.selectEmailVerifyInfoByCodeAndCompositePK(emailVerifyInfo) == null) {
			throw new EmailVerifyFailException("이메일 인증에 실패 했습니다. 코드를 다시 확인해 주세요.");
		}
		
		if(authMapper.deleteEmailVerifyInfo(emailVerifyInfo) != 1) {
			throw new UnexpectSqlException("예기치 않은 오류로 비밀번호 찾기가 정상적으로 이루어지지 않았습니다.");
		}
		
		// 아이디와 이름과 이메일로 유저 인증
		UserVO tempUserInfo = UserVO.builder().username(username).realname(realname).email(email).build();
		
		UserDTO userInfo = authMapper.selectUserByEmailAndRealnameAndUsername(tempUserInfo);
		
		if(userInfo == null) {
			throw new InvalidParameterException("입력 정보가 일치하지 않습니다.");
		}
		
		// 메일 제목
        String mailTitle = "Carlpion 임시 비밀번호";
        
		// 임시 비밀번호 생성
        Random random = new Random();
        
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		String number = "0123456789";
		String symbol = "!@#$%^&*()_+\\-=";
		
		StringBuilder temporaryPasswordCharactersSB = new StringBuilder(12);

        for (int i = 0; i < 4; i++) {
        	
            int randomIndex = random.nextInt(alphabet.length());
            
            temporaryPasswordCharactersSB.append(alphabet.charAt(randomIndex));
        }
        
        for (int i = 0; i < 4; i++) {
        	
            int randomIndex = random.nextInt(number.length());
            
            temporaryPasswordCharactersSB.append(number.charAt(randomIndex));
        }
        
        for (int i = 0; i < 4; i++) {
        	
            int randomIndex = random.nextInt(symbol.length());
            
            temporaryPasswordCharactersSB.append(symbol.charAt(randomIndex));
        }
        
        String temporaryPasswordCharacters = temporaryPasswordCharactersSB.toString();
        
        char[] chars = temporaryPasswordCharacters.toCharArray();
        
        for (int i = chars.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        
        String temporaryPassword = new String(chars);
        
        // 메일 내용인 HTML 문서 생성
        StringBuilder mailContentSB = new StringBuilder();

        mailContentSB.append("<!DOCTYPE html><html lang=\"ko\"><head><meta charset=\"UTF-8\" /><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" /><title>Carlpion 인증코드</title></head>");
        mailContentSB.append("<body><table style=\"width: 40rem; background-color: white; border: 4px solid #35bdf2; border-radius: 1.5rem; font-family: 'Trebuchet MS', sans-serif\"><tr><td style=\"width: 100%; padding: 1rem 0; border-bottom: 3px solid #35bdf2; font-size: 1.875rem; color: #35bdf2; letter-spacing: 0.1rem; text-align: center; font-weight: bold\">Carlpion ");
        mailContentSB.append(" 임시 비밀번호</td></tr><tr><td style=\"width: 100%; padding: 2.5rem 0; border-bottom: 3px solid #35bdf2; font-size: 1.5rem; letter-spacing: 0.1rem; text-align: center\">");
        mailContentSB.append(temporaryPassword);
        mailContentSB.append("</td></tr><tr><td style=\"width: 100%; padding: 1rem 0; font-size: 1rem; letter-spacing: 0.1rem; text-align: center\">임시 비밀번호로 로그인하고 반드시 비밀번호를 변경하세요.</td></tr></table></body></html>");
        
        String mailContent = mailContentSB.toString();
        
        // 메일 작성
        MimeMessage message = mailSender.createMimeMessage();
		
		MimeMessageHelper helper = null;
		
		try {
			helper = new MimeMessageHelper(message, false, "UTF-8");
			
			helper.setSubject(mailTitle);
			helper.setText(mailContent, true);
			helper.setTo(email);
			
		} catch (Exception e) {
			throw new CustomMessagingException("임시 비밀번호 전송에 실패 했습니다. 나중에 다시 시도해 주세요.");
		}
		
		// 임시 비밀번호 암호화해서 DB에 저장
		String encodedPassword = passwordEncoder.encode(temporaryPassword);
		
		UserVO user = UserVO.builder().username(userInfo.getUsername())
									  .password(encodedPassword)
									  .nickname(userInfo.getNickname())
									  .realname(userInfo.getRealname())
									  .email(userInfo.getEmail())
									  .build();
		
		if(authMapper.updatePasswordToTemporaryPassword(user) != 1) {
			throw new UnexpectSqlException("비밀번호 찾기 중 문제가 발생했습니다. 나중에 다시 시도해 주세요.");
		}
		
		// 메일 전송
		mailSender.send(message);
	}

	// 인증 이메일 전송 메서드
	private void sendVerifyEmail(String email, String type) {
        
        // 이메일 인증 중복 확인
        EmailVerifyVO emailVerifyInfoCheck = authMapper.selectEmailVerifyInfoByCompositePK(EmailVerifyVO.builder().email(email).type(type).build());
        
        if(emailVerifyInfoCheck != null) {
        	throw new DuplicateValueException("인증 메일이 이미 전송 되었습니다.");
        }
		
		// 메일 제목 생성
        StringBuilder mailTitleSB = new StringBuilder();
        
        mailTitleSB.append("Carlpion ");
        mailTitleSB.append(type);
        mailTitleSB.append(" 인증코드");
        
        String mailTitle = mailTitleSB.toString();
		
		// 무작위 인증 코드 생성
        Random random = new Random();
        
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		
        StringBuilder verifyCodeSB = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {
        	
            int randomIndex = random.nextInt(characters.length());
            
            verifyCodeSB.append(characters.charAt(randomIndex));
        }
        
        String verifyCode = verifyCodeSB.toString();
        
        // 메일 내용인 HTML 문서 생성
        StringBuilder mailContentSB = new StringBuilder();

        mailContentSB.append("<!DOCTYPE html><html lang=\"ko\"><head><meta charset=\"UTF-8\" /><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" /><title>Carlpion 인증코드</title></head>");
        mailContentSB.append("<body><table style=\"width: 40rem; background-color: white; border: 4px solid #35bdf2; border-radius: 1.5rem; font-family: 'Trebuchet MS', sans-serif\"><tr><td style=\"width: 100%; padding: 1rem 0; border-bottom: 3px solid #35bdf2; font-size: 1.875rem; color: #35bdf2; letter-spacing: 0.1rem; text-align: center; font-weight: bold\">Carlpion ");
        mailContentSB.append(type);
        mailContentSB.append(" 인증코드</td></tr><tr><td style=\"width: 100%; padding: 2.5rem 0; border-bottom: 3px solid #35bdf2; font-size: 1.5rem; letter-spacing: 0.1rem; text-align: center\">");
        mailContentSB.append(verifyCode);
        mailContentSB.append("</td></tr><tr><td style=\"width: 100%; padding: 1rem 0; font-size: 1rem; letter-spacing: 0.1rem; text-align: center\">위 코드를 입력하고 ");
        mailContentSB.append(type);
        mailContentSB.append(" 버튼을 누르세요.</td></tr></table></body></html>");
        
        String mailContent = mailContentSB.toString();
        
        // 메일 작성
        MimeMessage message = mailSender.createMimeMessage();
		
		MimeMessageHelper helper = null;
		
		try {
			helper = new MimeMessageHelper(message, false, "UTF-8");
			
			helper.setSubject(mailTitle);
			helper.setText(mailContent, true);
			helper.setTo(email);
			
		} catch (Exception e) {
			throw new CustomMessagingException("인증코드 메일 전송에 실패 했습니다. 나중에 다시 시도해 주세요.");
		}
		
		// 이메일 주소와 인증 코드 DB에 저장
		EmailVerifyVO emailVerifyInfo = EmailVerifyVO.builder().email(email).code(verifyCode).type(type).build();
		
		if(authMapper.insertEmailVerifyInfo(emailVerifyInfo) != 1) {
			throw new UnexpectSqlException("인증 과정에 문제가 발생 했습니다. 나중에 다시 시도해 주세요.");
		}

		// 메일 전송
		mailSender.send(message);
	}
	
	// 회원가입, 아이디 찾기, 비밀번호 찾기 시, 인증 이메일을 전송하는 메서드
	@Override
	public void sendVerifyEmailByType(EmailDTO emailInfo) {
		
		String email = emailInfo.getEmail();
        String type = emailInfo.getType();
        
        switch (type) {
        
		case "회원가입":
			// 이메일 중복 확인
    		if(userMapper.selectUserByEmail(email) != null) {
    			throw new DuplicateValueException("이미 사용 중인 이메일 입니다.");
    		}
    		
            sendVerifyEmail(email, type);
            
			break;
			
		case "아이디 찾기":
    		// 가입된 이메일인지 확인
    		if(userMapper.selectUserByEmail(email) == null) {
    			throw new EmailVerifyFailException("가입되지 않은 이메일 입니다.");
    		}
    		
            sendVerifyEmail(email, type);
            
			break;
			
		case "비밀번호 찾기":
    		// 가입된 이메일인지 확인
    		if(userMapper.selectUserByEmail(email) == null) {
    			throw new EmailVerifyFailException("가입되지 않은 이메일 입니다.");
    		}
    		
    		sendVerifyEmail(email, type);
            
			break;
			
		default: 
			throw new EmailVerifyFailException("올바르지 않은 요청입니다.");
		}
	}

	// 소셜 로그인 메서드
	@Override
	public SocialDTO checkSocialUser(SocialDTO socialLoginInfo) {
		
		SocialDTO user = userMapper.selectSocialUserByCompositePK(socialLoginInfo);
		
		// DB와 일치하는 정보가 없으면 회원가입 아니면 로그인
		if(user == null) {
			
			return null;
		};
		
		return user;
	}

	// 소셜 회원가입 메서드
	@Override
	public void signUpBySocial(SocialDTO socialSignUpInfo) {
		
		
	}
}
