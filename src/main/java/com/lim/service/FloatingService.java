package com.lim.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lim.mapper.FloatingMapper;
import com.lim.vo.LikedBookVO;
import com.lim.vo.RecentBookVO;



@Service
public class FloatingService {
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	public List<RecentBookVO> countCart(String email) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			FloatingMapper mapper = session.getMapper(FloatingMapper.class);

			return mapper.recentbook(email);
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
			return null;
		}
	}
	
	public List<LikedBookVO> likedBook(String email) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			FloatingMapper mapper = session.getMapper(FloatingMapper.class);

			return mapper.likedBook(email);
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
			return null;
		}
	}

}
