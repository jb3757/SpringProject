package com.lim.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lim.service.FloatingService;
import com.lim.vo.LikedBookVO;
import com.lim.vo.RecentBookVO;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/floating/")

public class floatingController {
	
	@Autowired
	FloatingService f_service;
	
	private static Logger logger = LoggerFactory.getLogger(floatingController.class);
	
	@ResponseBody
	@RequestMapping(value = "/recentbook", method = RequestMethod.GET)
	public List<RecentBookVO> getRecentbook(@RequestParam String userEmail) {
		logger.info("받아온 유저 이메일" + userEmail);
		return f_service.countCart(userEmail);
	}
	
	@ResponseBody
	@RequestMapping(value = "/checkLikeList", method = RequestMethod.POST, produces = "application/json")
	public List<LikedBookVO> getLikedBook(@RequestBody Map<String, String> requestBody) {
		String email = requestBody.get("email");
		return f_service.likedBook(email);
	}

}
