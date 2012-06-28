package com.hy.shopkeeper.fkw.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

import com.hy.shopkeeper.fkw.api.OpenTaoBao;
import com.hy.shopkeeper.fkw.api.impl.OpenTaoBaoTemplate;


public class OpenTaoBaoServiceProvider extends AbstractOAuth2ServiceProvider<OpenTaoBao> {
	
	private final String clientId;
	
	public OpenTaoBaoServiceProvider(String clientId, String clientSecret) {
		super(new OpenTaoBaoOAuth2Template(clientId, clientSecret));
		this.clientId = clientId;
	}
	
	public OpenTaoBao getApi(String accessToken) {
		return new OpenTaoBaoTemplate(clientId, accessToken);
	}

}
