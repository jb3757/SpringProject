package com.lim.controller;

//import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lim.service.UserService;
//import com.google.gson.Gson;
import com.lim.vo.ResultVO;
import com.lim.vo.UserVO;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/auth/")

public class UserController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService u_service;

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Object loginUser(@RequestBody UserVO userVO) {

		String userId = userVO.getUSER_EMAIL();
		String userPw = userVO.getUSER_PW();

		logger.info(userId);
		logger.info(userPw);
		
		try {
			Object loingResult = u_service.userLogin(userVO);
			return loingResult;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			return null;
		}
		
	}

	// 이메일 get으로 받아와서 중복이 있는지 없는지 확인
	@ResponseBody
	@RequestMapping(value = "/checkemail", method = RequestMethod.GET)
	public ResultVO CheckLogin(String email) {

		logger.info(email);
		ResultVO result = new ResultVO(false, null);

		try {
			int resultCheckEmail = u_service.checkEmail(email);
			result.setSuccess(true);
			result.setResult(resultCheckEmail);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/join", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String JoinUser(@RequestBody UserVO uv) {
		String resultText = "회원가입 성공";

		logger.info(uv.toString());

		try {

			u_service.joinUser(uv);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
		}

		return resultText;
	}

}
