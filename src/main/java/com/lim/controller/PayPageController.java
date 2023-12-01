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
import com.lim.service.OrderService;
import com.lim.service.UserService;
import com.lim.vo.LikedBookVO;
import com.lim.vo.PayBookInfo;
import com.lim.vo.RecentBookVO;
import com.lim.vo.UserVO;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/pay/")

public class PayPageController {

	@Autowired
	UserService us;
	
	@Autowired
	OrderService os;

	private static Logger logger = LoggerFactory.getLogger(PayPageController.class);

	@ResponseBody
	@RequestMapping(value = "/bookCount", method = RequestMethod.GET)
	public int getBookCount(@RequestParam int payID) {
//		logger.info("받아온 유저 이메일" + payID);
		return os.bookCount(payID);
	}

	@ResponseBody
	@RequestMapping(value = "/payUserInfo", method = RequestMethod.POST)
	public List<UserVO> getLikedBook(@RequestBody Map<String, String> requestBody) {
		String email = requestBody.get("userEmail");

		return us.userJoin(email);
	}
	
	@ResponseBody
	@RequestMapping(value = "/userPoint", method = RequestMethod.GET)
	public int getUserPoint(@RequestParam String userEmail) {
//		logger.info("받아온 유저 이메일" + payID);
		return us.userPoint(userEmail);
	}
	
	@ResponseBody
	@RequestMapping(value = "/originalPrice", method = RequestMethod.GET)
	public int getUserPoint(@RequestParam int payID) {
//		logger.info("받아온 유저 이메일" + payID);
		return os.originalPrice(payID);
	}
	
	@ResponseBody
	@RequestMapping(value = "/payBookInfo", method = RequestMethod.GET)
	public List<PayBookInfo> getPayBookInfo(@RequestParam int payID) {
//		logger.info("받아온 유저 이메일" + payID);
		return os.payBookInfo(payID);
	}


}
