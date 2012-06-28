package com.hy.shopkeeper.fkw.api.impl;

import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.support.URIBuilder;

import com.hy.shopkeeper.fkw.api.OpenTaoBao;
import com.hy.shopkeeper.fkw.api.ShopOperations;
import com.hy.shopkeeper.fkw.api.UserOperations;

public class OpenTaoBaoTemplate extends AbstractOAuth2ApiBinding implements OpenTaoBao {
    
	private final String accessToken;
	private final String clientId;
	private final UserOperations userOperations;
	private final ShopOperations shopOperations;
	
	public OpenTaoBaoTemplate(String clientId) {
		this(clientId, null, false);
	}
	
	/**
	 * Create a new instance of OpenTaoBaoTemplate.
	 * @param clientId the application's client ID
	 * @param accessToken an access token acquired through OAuth authentication with OpenTaoBao
	 */
	public OpenTaoBaoTemplate(String clientId, String accessToken) {
		this(clientId, accessToken, true);
	}
	
	private OpenTaoBaoTemplate(String clientId, String accessToken, boolean isAuthorizedForUser) {
	    super(accessToken);
		this.clientId = clientId;
		this.accessToken = accessToken;	
		userOperations = new UserTemplate(this, isAuthorizedForUser);
		shopOperations= new ShopTemplate(this, isAuthorizedForUser);
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
	
	public UserOperations userOperations() {
		return userOperations;
	}

	@Override
	public ShopOperations shopOperations() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
