package com.lim.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lim.service.BookService;
import com.lim.service.SurveyService;
import com.lim.vo.BookVO;
import com.lim.vo.SurveyVO;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/booklist/")
public class BooklistController {

	private static Logger logger = LoggerFactory.getLogger(BooklistController.class);

	@Autowired
	BookService bs;

	@Autowired
	SurveyService ss;

	@ResponseBody
	@RequestMapping(value = "/getRecList", method = RequestMethod.POST)
	public List<BookVO> recBook(@RequestBody Map<String, String> requestBody) {
		String userEmail = requestBody.get("email");
		// 이메일 받아옴

		/* logger.info("추천 유저 이메일 :" + userEmail); */
		/* int checkOrderResult = bs.checkOrder(userEmail); */
		/* logger.info("첫번째 쿼리 실행 :" + checkOrderResult); */

		List<SurveyVO> sv = ss.checkSurvey(userEmail);
		/* logger.info("설문조사 결과 받아오기 :" + sv); */

		List<String> categoryNames = sv.stream()
				.flatMap(survey -> Stream.of(survey.getSUR_LIKE_1(), survey.getSUR_LIKE_2(), survey.getSUR_LIKE_3(),
						survey.getSUR_LIKE_4(), survey.getSUR_LIKE_5()))
				.filter(Objects::nonNull).map(category -> "%" + category + "%").collect(Collectors.toList());

		/* logger.info("List<String> categoryNames :" + categoryNames); */

		List<BookVO> result = bs.getRecList(categoryNames);
		/* logger.info("List<BookVO> result :" + result); */

		return result;

	}

}
