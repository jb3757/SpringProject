package com.lim.vo;

import lombok.Data;

@Data
public class ResultVO {

	Object result;
	boolean success;

	public ResultVO(boolean success, Object result) {
		setResult(result);
		setSuccess(success);
	}
}
