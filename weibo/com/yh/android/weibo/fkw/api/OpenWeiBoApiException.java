package com.yh.android.weibo.fkw.api;

import com.yh.android.framework.social.SocialException;

@SuppressWarnings("serial")
public class OpenWeiBoApiException extends SocialException {

	private int code;
	private String errorType;
	
	public OpenWeiBoApiException(int code, String errorType, String message) {
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
