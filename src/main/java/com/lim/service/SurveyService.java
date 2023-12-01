package com.lim.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lim.mapper.SurveyMapper;
import com.lim.vo.SurveyVO;

@Service
public class SurveyService {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	public List<SurveyVO> checkSurvey(String userEmail) {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			SurveyMapper mapper = session.getMapper(SurveyMapper.class);

			return mapper.checkSurvey(userEmail);
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
			return null;
		}
	}

	public void insertSurvey(SurveyVO sv) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			SurveyMapper mapper = session.getMapper(SurveyMapper.class);

			mapper.insertSurvey(sv);
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
		}
	}

}
