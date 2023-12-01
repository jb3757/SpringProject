package com.lim.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lim.mapper.CartMapper;



@Service
public class CartService {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	public int countCart(String email) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			CartMapper mapper = session.getMapper(CartMapper.class);

			return mapper.countCart(email);
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
			return 0;
		}
	}

}
