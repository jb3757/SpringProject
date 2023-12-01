package com.lim.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LikedBookVO {
	
	@JsonProperty("LIKE_ID")
	int LIKE_ID;
	@JsonProperty("LIKE_CREATED_AT")
	Date LIKE_CREATED_AT;
	@JsonProperty("LIKE_BOOK_ID")
	int LIKE_BOOK_ID;
	@JsonProperty("LIKE_USER_EMAIL")
	String LIKE_USER_EMAIL;

}
