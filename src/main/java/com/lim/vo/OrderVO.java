package com.lim.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OrderVO {
	@JsonProperty("ORDER_ID")
	int ORDER_ID;
	@JsonProperty("ORDER_USER_EMAIL")
	String ORDER_USER_EMAIL;
	@JsonProperty("ORDER_PAY")
	int ORDER_PAY;
	@JsonProperty("ORDER_ADDPOINT")
	int ORDER_ADDPOINT;
	@JsonProperty("ORDER_COST")
	int ORDER_COST;
}
