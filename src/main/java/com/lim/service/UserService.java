package com.lim.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lim.mapper.UserMapper;

import com.lim.vo.UserVO;

@Service
public class UserService {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	public int checkEmail(String email) {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper mapper = session.getMapper(UserMapper.class);

			/* mapper.checkEmail(email); */
			return mapper.checkEmail("%" + email + "%");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return -1;
		}
	}

	public void joinUser(UserVO uv) {
		// TODO Auto-generated method stub

		uv.setUSER_REGDATE(new Date());

		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper mapper = session.getMapper(UserMapper.class);

			mapper.joinUser(uv);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public List<UserVO> userLogin(UserVO uv) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper mapper = session.getMapper(UserMapper.class);

			return mapper.userLogin(uv);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}

	}
	
	public List<UserVO> userJoin(String email) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper mapper = session.getMapper(UserMapper.class);

			return mapper.userJoin(email);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}

	}
	
	public int userPoint(String email) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper mapper = session.getMapper(UserMapper.class);

			return mapper.userPoint(email);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return -1;
		}

	}
}
