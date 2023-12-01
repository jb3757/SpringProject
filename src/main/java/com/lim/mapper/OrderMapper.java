package com.lim.mapper;

import java.util.List;

import com.lim.vo.OrderVO;
import com.lim.vo.PayBookInfo;

public interface OrderMapper {

	public int insertOrder(OrderVO ov);
	
	public void insertOrderItem(int orderId, int bookId, int bookNum, int price, int point);
	
	public int bookCount(int orderId);
	
	public int originalPrice(int orderId);
	
	public List<PayBookInfo> payBookInfo(int orderId);
}
