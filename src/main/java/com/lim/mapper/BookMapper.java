package com.lim.mapper;

import java.util.Date;
import java.util.List;

import com.lim.vo.BookVO;

public interface BookMapper {
	
	public List<BookVO> bestBook();
	
	public List<BookVO> recentBook();
	
	public List<BookVO> searchBook(String book);
	
	public int checkOrder(String email);
	
	public List<BookVO> getRecList(List<String> categoryNames);
	
	public List<BookVO> getBookInfo(int bookNum);
	
	public int checkRecBook(String userEmail, int bookNum);
	
	public void insertRecBook(String email, int bookNum, Date today);
	
	public void updateRecBook(Date today, String email, int bookNum);

}
