package com.lim.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lim.controller.BooklistController;
import com.lim.mapper.OrderMapper;
import com.lim.vo.OrderVO;
import com.lim.vo.PayBookInfo;

@Service
public class OrderService {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	private static Logger logger = LoggerFactory.getLogger(BooklistController.class);

	public int insertOrder(String email, int pay, int point, int fee) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			OrderMapper mapper = session.getMapper(OrderMapper.class);

			OrderVO ov = new OrderVO();
			ov.setORDER_USER_EMAIL(email);
			ov.setORDER_PAY(pay);
			ov.setORDER_ADDPOINT(point);
			ov.setORDER_COST(fee);

			// 데이터베이스에 삽입 후에 자동으로 생성된 ID를 ov에 설정
			mapper.insertOrder(ov);

			System.out.println(ov.getORDER_ID());

			return ov.getORDER_ID();

		} catch (Exception e) {
			// TODO: handle exception
			logger.info("e : " + e);
			return -1;
		}
	}

	public void insertOrderItem(int orderId, int bookId, int bookNum, int price, int point) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			OrderMapper mapper = session.getMapper(OrderMapper.class);
			mapper.insertOrderItem(orderId, bookId, bookNum, price, point);

		} catch (Exception e) {
			// TODO: handle exception
			logger.info("e : " + e);

		}
	}
	
	public int bookCount(int orderId) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			OrderMapper mapper = session.getMapper(OrderMapper.class);
			return mapper.bookCount(orderId);

		} catch (Exception e) {
			// TODO: handle exception
			logger.info("e : " + e);
			return -1;
		}
	}
	
	public int originalPrice(int orderId) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			OrderMapper mapper = session.getMapper(OrderMapper.class);
			return mapper.originalPrice(orderId);

		} catch (Exception e) {
			// TODO: handle exception
			logger.info("e : " + e);
			return -1;
		}
	}
	
	public List<PayBookInfo> payBookInfo(int orderId) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			OrderMapper mapper = session.getMapper(OrderMapper.class);
			return mapper.payBookInfo(orderId);

		} catch (Exception e) {
			// TODO: handle exception
			logger.info("e : " + e);
			return null;
		}
	}
}
