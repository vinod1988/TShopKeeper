package com.yh.android.weibo.fkw.api.impl;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

import com.yh.android.framework.social.MissingAuthorizationException;
import com.yh.android.framework.social.support.URIBuilder;

public abstract class AbstractOpenWeiBoOperations {
	
	private final boolean isAuthorized;
	
	protected final OpenWeiBoTemplate opentaobao;
	
	public AbstractOpenWeiBoOperations(OpenWeiBoTemplate openTaobao, boolean isAuthorized) {
		this.opentaobao = openTaobao;
		this.isAuthorized = isAuthorized;
	}
		

	protected void requireUserAuthorization() {
		if(!isAuthorized) {
			throw new MissingAuthorizationException();
		}
	}
	
	protected URI buildUri(String path) {
		return buildUri(path, Collections.<String, String>emptyMap());
	}
	
	protected URI buildUri(String path, Map<String, String> params) {
		URIBuilder uriBuilder = opentaobao.withAccessToken(API_URL_BASE + path);
		for (String paramName : params.keySet()) {
			uriBuilder.queryParam(paramName, String.valueOf(params.get(paramName)));
		}
		URI uri = uriBuilder.build();
		return uri;
	}
	
	private static final String API_URL_BASE = "http://gw.api.taobao.com/router/rest";
	
}
