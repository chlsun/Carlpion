package com.kh.carlpion.auth.model.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.carlpion.auth.model.vo.EmailVerifyVO;
import com.kh.carlpion.user.model.dto.UserDTO;
import com.kh.carlpion.user.model.vo.UserVO;

@Mapper
public interface AuthMapper {

	@Select("SELECT EMAIL, TYPE, CODE FROM CP_EMAIL_VERIFY WHERE EMAIL = #{email} AND TYPE = #{type}")
	EmailVerifyVO selectEmailVerifyInfoByCompositePK(EmailVerifyVO emailVerifyInfo);
	
	@Select("SELECT EMAIL, TYPE, CODE FROM CP_EMAIL_VERIFY WHERE EMAIL = #{email} AND TYPE = #{type} AND CODE = #{code}")
	EmailVerifyVO selectEmailVerifyInfoByCodeAndCompositePK(EmailVerifyVO emailVerifyInfo);
	
	@Select("SELECT USERNAME FROM CP_USER WHERE EMAIL = #{email} AND REALNAME = #{realname}")
	String selectUsernameByEmailAndRealname(UserVO userInfo);
	
	@Select("SELECT USER_NO userNo, USERNAME, PASSWORD, NICKNAME, REALNAME, EMAIL, ROLE FROM CP_USER WHERE EMAIL = #{email} AND REALNAME = #{realname} AND USERNAME = #{username}")
	UserDTO selectUserByEmailAndRealnameAndUsername(UserVO userInfo);
	
	@Insert("INSERT INTO CP_EMAIL_VERIFY(EMAIL, TYPE, CODE) VALUES (#{email}, #{type}, #{code})")
	int insertEmailVerifyInfo(EmailVerifyVO emailVerifyInfo);
	
	@Update("UPDATE CP_USER SET PASSWORD = #{password} WHERE USERNAME = #{username} AND NICKNAME = #{nickname} AND REALNAME = #{realname} AND EMAIL = #{email}")
	int updatePasswordToTemporaryPassword(UserVO userInfo);
	
	@Delete("DELETE FROM CP_EMAIL_VERIFY WHERE EMAIL = #{email} AND TYPE = #{type}")
	int deleteEmailVerifyInfo(EmailVerifyVO emailVerifyInfo);
}
