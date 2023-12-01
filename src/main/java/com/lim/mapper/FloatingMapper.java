package com.lim.mapper;

import java.util.List;


import com.lim.vo.LikedBookVO;
import com.lim.vo.RecentBookVO;

public interface FloatingMapper {
	
	public List<RecentBookVO> recentbook(String email);

	public List<LikedBookVO> likedBook(String email);
}
