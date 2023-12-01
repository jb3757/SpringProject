package com.lim.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BookVO {
	
    @JsonProperty("BOOK_ID")
    int BOOK_ID;

    @JsonProperty("BOOK_TITLE")
    String BOOK_TITLE;

    @JsonProperty("BOOK_AUTHOR")
    String BOOK_AUTHOR;

    @JsonProperty("BOOK_PUBDATE")
    String BOOK_PUBDATE;

    @JsonProperty("BOOK_DESCRIPTION")
    String BOOK_DESCRIPTION;

    @JsonProperty("BOOK_PRICE")
    int BOOK_PRICE;

    @JsonProperty("BOOK_POINT")
    int BOOK_POINT;

    @JsonProperty("BOOK_COVER")
    String BOOK_COVER;

    @JsonProperty("BOOK_CATEGORYNAME")
    String BOOK_CATEGORYNAME;

    @JsonProperty("BOOK_PUBLISHER")
    String BOOK_PUBLISHER;

    @JsonProperty("BOOK_SALESPOINT")
    int BOOK_SALESPOINT;
    @JsonProperty("reviewpoint")
    long reviewpoint;
}
