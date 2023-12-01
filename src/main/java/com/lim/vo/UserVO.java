package com.lim.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserVO {

	@JsonProperty("USER_EMAIL")
	String USER_EMAIL;
	@JsonProperty("USER_PW")
	String USER_PW;
	@JsonProperty("USER_SEX")
	String USER_SEX;
	@JsonProperty("USER_AGEGROUP")
	String USER_AGEGROUP;
	@JsonProperty("USER_NAME")
	String USER_NAME;
	@JsonProperty("USER_REGDATE")
	Date USER_REGDATE;
	@JsonProperty("USER_PHONE")
	String USER_PHONE;
	@JsonProperty("USER_ADD1")
	String USER_ADD1;
	@JsonProperty("USER_ADD2")
	String USER_ADD2;
	@JsonProperty("USER_TOTAL_PAY")
	int USER_TOTAL_PAY;;
	@JsonProperty("USER_POINT")
	int USER_POINT;
	@JsonProperty("USER_PROVIDER")
	String USER_PROVIDER;
	@JsonProperty("USER_ZIPCODE")
	String USER_ZIPCODE;
	


}
