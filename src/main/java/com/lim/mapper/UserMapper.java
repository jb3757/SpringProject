package com.lim.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lim.vo.UserVO;

@Mapper
public interface UserMapper {
	
	/* 이메일 중복 검사 */
	public int checkEmail(String email);
	
	public void joinUser(UserVO uv);
	
	public List<UserVO> userLogin(UserVO uv);
	
	public List<UserVO> userJoin(String email);
	
	public int userPoint(String email);
}
