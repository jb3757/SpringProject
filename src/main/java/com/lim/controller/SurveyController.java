package com.lim.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lim.service.SurveyService;
import com.lim.vo.SurveyVO;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/sur/")

public class SurveyController {
	
	private static Logger logger = LoggerFactory.getLogger(SurveyController.class);
	
	@Autowired
	SurveyService s_service;
	
	@ResponseBody
	@RequestMapping(value = "/check", method = RequestMethod.GET, produces = "application/text; charset=utf8") 
	public String checkSurvey(String userEmail) {
	    logger.info("서베이 컨트롤러 " + userEmail);
	    
	    try {
	        List<SurveyVO> surResult = s_service.checkSurvey(userEmail);
	        String resultText = "";
	        
	        resultText = (surResult.isEmpty()) ? "설문조사안함" : "설문조사함";
	        

	        return resultText;
	    } catch (Exception e) {
	        // TODO: handle exception
	        logger.info("에러발생" + e);
	        return null;
	    }
	}
	
	@ResponseBody
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public void insertSurvey(@RequestBody SurveyVO sv) {
		logger.info("설문조사 실시" + sv);
		
		try {
			s_service.insertSurvey(sv);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("에러발생" + e);
		}
		
	}

}
