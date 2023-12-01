package com.lim.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SurveyVO {
    
    int SUR_ID;

    @JsonProperty("SUR_USER_EMAIL")
    String SUR_USER_EMAIL;

    @JsonProperty("SUR_LIKE_1")
    String SUR_LIKE_1;

    @JsonProperty("SUR_LIKE_2")
    String SUR_LIKE_2;

    @JsonProperty("SUR_LIKE_3")
    String SUR_LIKE_3;

    @JsonProperty("SUR_LIKE_4")
    String SUR_LIKE_4;

    @JsonProperty("SUR_LIKE_5")
    String SUR_LIKE_5;
}
