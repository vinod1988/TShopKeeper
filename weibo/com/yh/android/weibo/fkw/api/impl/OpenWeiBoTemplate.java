package com.yh.android.weibo.fkw.api.impl;

import com.yh.android.framework.social.oauth2.AbstractOAuth2ApiBinding;
import com.yh.android.framework.social.support.URIBuilder;
import com.yh.android.weibo.fkw.api.OpenWeiBo;
import com.yh.android.weibo.fkw.api.WeiBoOperations;


public class OpenWeiBoTemplate extends AbstractOAuth2ApiBinding implements OpenWeiBo {
    
	private final String accessToken;
	private final String clientId;
	private final WeiBoOperations weiBoOperations;
	
	public OpenWeiBoTemplate(String clientId) {
		this(clientId, null, false);
	}
	
	/**
	 * Create a new instance of OpenTaoBaoTemplate.
	 * @param clientId the application's client ID
	 * @param accessToken an access token acquired through OAuth authentication with OpenTaoBao
	 */
	public OpenWeiBoTemplate(String clientId, String accessToken) {
		this(clientId, accessToken, true);
	}
	
	private OpenWeiBoTemplate(String clientId, String accessToken, boolean isAuthorizedForUser) {
	    super(accessToken);
		this.clientId = clientId;
		this.accessToken = accessToken;	
		weiBoOperations = new WeiBoTemplate(this, isAuthorizedForUser);
	}
			
	public URIBuilder withAccessToken(String uri) {
		return (accessToken == null) 
			? URIBuilder.fromUri(uri).queryParam("client_id", clientId)
			: URIBuilder.fromUri(uri).queryParam("access_token", accessToken);
	}
	
	public String withAccessTokenString(){
		return (accessToken == null) 
		? clientId
		: accessToken;
	}


	@Override
	public WeiBoOperations weiboOperations() {
		// TODO Auto-generated method stub
		return weiBoOperations;
	}
	
}
