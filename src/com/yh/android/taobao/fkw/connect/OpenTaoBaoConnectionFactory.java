package com.yh.android.taobao.fkw.connect;

import com.yh.android.framework.social.connect.support.OAuth2ConnectionFactory;
import com.yh.android.taobao.fkw.api.OpenTaoBao;


public class OpenTaoBaoConnectionFactory extends OAuth2ConnectionFactory<OpenTaoBao> {

	public OpenTaoBaoConnectionFactory(String clientId, String clientSecret) {
		super("opentaobao", new OpenTaoBaoServiceProvider(clientId, clientSecret), new OpenTaoBaoAdapter());
	}
	
}
