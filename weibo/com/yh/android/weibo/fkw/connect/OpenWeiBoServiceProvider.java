package com.yh.android.weibo.fkw.connect;

import com.yh.android.framework.social.oauth2.AbstractOAuth2ServiceProvider;
import com.yh.android.weibo.fkw.api.OpenWeiBo;
import com.yh.android.weibo.fkw.api.impl.OpenWeiBoTemplate;

public class OpenWeiBoServiceProvider extends AbstractOAuth2ServiceProvider<OpenWeiBo> {
	
	private final String clientId;
	
	public OpenWeiBoServiceProvider(String clientId, String clientSecret) {
		super(new OpenWeiBoOAuth2Template(clientId, clientSecret));
		this.clientId = clientId;
	}
	
	public OpenWeiBo getApi(String accessToken) {
		return new OpenWeiBoTemplate(clientId, accessToken);
	}

}
