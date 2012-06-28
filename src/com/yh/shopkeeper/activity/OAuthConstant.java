package com.yh.shopkeeper.activity;

import com.hy.shopkeeper.fkw.TaoBaoAccessToken;


public class OAuthConstant {
	
	private static OAuthConstant instance=null;
	private String token;
	private TaoBaoAccessToken taobaoAccessToken;
	
	private OAuthConstant(){};
	public static synchronized OAuthConstant getInstance(){
		if(instance==null)
			instance= new OAuthConstant();
		return instance;
	}

	public String getToken() {
		return token;
	}
	
	public void setTaobaoAccessToken(TaoBaoAccessToken taobaoAccessToken) {
		this.taobaoAccessToken = taobaoAccessToken;
		this.token=taobaoAccessToken.getAccessToken();
	}
	
	public TaoBaoAccessToken getTaobaoAccessToken() {
		return taobaoAccessToken;
	}
	
}
