package com.lim.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lim.service.BookService;
import com.lim.vo.BookVO;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/main/")

public class MainpageController {
	
	private static Logger logger = LoggerFactory.getLogger(MainpageController.class);
	
	@Autowired
	BookService b_service;
	
	@ResponseBody
	@RequestMapping(value = "/getNewestBook", method = RequestMethod.POST)
	public ResponseEntity<List<BookVO>> bestBook() {
	    try {
	        List<BookVO> result = b_service.bestBook();
	        return ResponseEntity.ok(result);
	    } catch (Exception e) {
	        logger.error("에러 발생", e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
	
	@ResponseBody
	@RequestMapping(value = "/getBestBook", method = RequestMethod.POST)
	public List<BookVO> recentBook() {
		
		return b_service.recentBook();
	}
	
	@ResponseBody
	@RequestMapping(value = "/searchBest", method = RequestMethod.GET)
	public List<BookVO> searchBook(@RequestParam String searchKeyword) {
		logger.info(searchKeyword);
		return b_service.searchBook(searchKeyword);
	}

}
