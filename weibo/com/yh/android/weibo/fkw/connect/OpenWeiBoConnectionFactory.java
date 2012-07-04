package com.yh.android.weibo.fkw.connect;

import com.yh.android.framework.social.connect.support.OAuth2ConnectionFactory;
import com.yh.android.taobao.fkw.api.OpenTaoBao;
import com.yh.android.weibo.fkw.api.OpenWeiBo;


public class OpenWeiBoConnectionFactory extends OAuth2ConnectionFactory<OpenWeiBo> {

	public OpenWeiBoConnectionFactory(String clientId, String clientSecret) {
		super("openweibo", new OpenWeiBoServiceProvider(clientId, clientSecret), new OpenWeiBoAdapter());
	}
	
}
