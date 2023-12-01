package com.lim.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RecentBookVO {
	@JsonProperty("REC_VIEWED_AT")
	Date REC_VIEWED_AT;
	@JsonProperty("BOOK_TITLE")
	String BOOK_TITLE;
	@JsonProperty("BOOK_COVER")
	String BOOK_COVER;
	@JsonProperty("BOOK_AUTHOR")
	String BOOK_AUTHOR;
	@JsonProperty("BOOK_PRICE")
	int BOOK_PRICE;
	@JsonProperty("BOOK_ID")
	int BOOK_ID;
}
