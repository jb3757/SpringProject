package com.lim.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lim.service.CartService;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/cart/")

public class CartController {
	
	@Autowired
	CartService c_service;

	private static Logger logger = LoggerFactory.getLogger(CartController.class);

	@ResponseBody
	@RequestMapping(value = "/countCart", method = RequestMethod.POST)
	public int countCart(@RequestBody Map<String, String> requestBody) {
		String email = requestBody.get("email");

//		logger.info(email);
		return c_service.countCart(email);
		
	}

}
