package com.lim.mapper;

import java.util.List;

import com.lim.vo.SurveyVO;



public interface SurveyMapper {
	
	public List<SurveyVO> checkSurvey(String userEmail);
	
	public void insertSurvey(SurveyVO sv);

}
