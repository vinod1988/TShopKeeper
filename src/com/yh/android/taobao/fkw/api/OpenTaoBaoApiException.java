package com.yh.android.taobao.fkw.api;

import com.yh.android.framework.social.SocialException;

@SuppressWarnings("serial")
public class OpenTaoBaoApiException extends SocialException {

	private int code;
	private String errorType;
	
	public OpenTaoBaoApiException(int code, String errorType, String message) {
		super(message);
		this.code = code;
		this.errorType = errorType;
	}

	public int getCode() {
		return code;
	}

	public String getErrorType() {
		return errorType;
	}
	
}
