package com.hy.shopkeeper.fkw.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import com.hy.shopkeeper.fkw.api.OpenTaoBao;

public class OpenTaoBaoConnectionFactory extends OAuth2ConnectionFactory<OpenTaoBao> {

	public OpenTaoBaoConnectionFactory(String clientId, String clientSecret) {
		super("opentaobao", new OpenTaoBaoServiceProvider(clientId, clientSecret), new OpenTaoBaoAdapter());
	}
	
}
