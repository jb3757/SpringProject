package com.lim.controller;

import java.util.Date;
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

import com.lim.service.BookService;
import com.lim.service.OrderService;
import com.lim.vo.BookVO;
import com.lim.vo.OrderVO;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/detail/")

public class BookDetailController {

	@Autowired
	BookService bs;

	@Autowired
	OrderService os;

	private static Logger logger = LoggerFactory.getLogger(BookDetailController.class);

	@ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<BookVO> getBookDetail(@RequestParam int bookNum) {
		logger.info("책 번호 : " + bookNum);
		return bs.getBookInfo(bookNum);
	}

	@ResponseBody
	@RequestMapping(value = "/setRec", method = RequestMethod.POST)
	public void setRecentview(@RequestBody Map<String, String> req) {

		String email = req.get("email");
		Date today = new Date();
		int bookNum = Integer.parseInt(req.get("bookid"));

		int checkResult = bs.checkRecBook(email, bookNum);

		logger.info("본적이 있는가? : " + checkResult);

		if (checkResult == 0) {
			bs.insertRecBook(email, bookNum, today);
		} else {
			bs.updateRecBook(today, email, bookNum);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/gotoPay", method = RequestMethod.POST)
	public int gotoPay(@RequestBody Map<String, String> req) {

		int bookNum = Integer.parseInt(req.get("bookNum"));
		int bookId = Integer.parseInt(req.get("bookId"));
		String email = req.get("email");
		int pay = Integer.parseInt(req.get("total_pay"));
		int addPoint = Integer.parseInt(req.get("total_point"));
		int cost = Integer.parseInt(req.get("fee"));

		logger.info("받은 데이터 : " + email + pay + addPoint + cost);

		int orderInt = os.insertOrder(email, pay, addPoint, cost);
		
		os.insertOrderItem(orderInt, bookId, bookNum, pay, addPoint);
		
		return orderInt;
	}

}
