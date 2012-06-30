package com.yh.taobao.fkw.api.impl;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.support.URIBuilder;

public abstract class AbstractOpenTaoBaoOperations {
	
	private final boolean isAuthorized;
	
	protected final OpenTaoBaoTemplate opentaobao;
	
	public AbstractOpenTaoBaoOperations(OpenTaoBaoTemplate openTaobao, boolean isAuthorized) {
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
