package com.lim.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lim.controller.BooklistController;
import com.lim.mapper.BookMapper;
import com.lim.vo.BookVO;

@Service
public class BookService {

	private static Logger logger = LoggerFactory.getLogger(BooklistController.class);

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	public List<BookVO> bestBook() {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			BookMapper mapper = session.getMapper(BookMapper.class);

			return mapper.bestBook();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public List<BookVO> recentBook() {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			BookMapper mapper = session.getMapper(BookMapper.class);

			return mapper.recentBook();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public List<BookVO> searchBook(String book) {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			BookMapper mapper = session.getMapper(BookMapper.class);
			String bookname = ("%" + book + "%");

			return mapper.searchBook(bookname);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public int checkOrder(String email) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			BookMapper mapper = session.getMapper(BookMapper.class);

			return mapper.checkOrder(email);
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	}

	public List<BookVO> getRecList(List<String> categoryNames) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			BookMapper mapper = session.getMapper(BookMapper.class);

			return mapper.getRecList(categoryNames);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("e : " + e);
			return null;
		}
	}

	public List<BookVO> getBookInfo(int bookNum) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			BookMapper mapper = session.getMapper(BookMapper.class);

			return mapper.getBookInfo(bookNum);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("e : " + e);
			return null;
		}
	}

	public int checkRecBook(String userEmail, int bookNum) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			BookMapper mapper = session.getMapper(BookMapper.class);
			logger.info("?? : " + userEmail + bookNum);
			return mapper.checkRecBook(userEmail, bookNum);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("e : " + e);
			return -1;
		}
	}

	public void insertRecBook(String email, int bookNum, Date today) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			BookMapper mapper = session.getMapper(BookMapper.class);
			
			mapper.insertRecBook(email, bookNum, today);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("e : " + e);

		}
	}
	
	public void updateRecBook(Date today, String email, int bookNum) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			BookMapper mapper = session.getMapper(BookMapper.class);
			
			mapper.updateRecBook(today, email, bookNum);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("e : " + e);

		}
	}

}
