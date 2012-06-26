package com.yh.shopkeeper.activity;

import org.springframework.social.oauth2.AccessGrant;

import com.hy.shopkeeper.fkw.TaoBao;
import com.hy.shopkeeper.fkw.TaoBaoAccessToken;


public class OAuthConstant {
	
	private static TaoBao taobao=null;
	private static OAuthConstant instance=null;
	private AccessGrant accessToken;
	private String token;

	private OAuthConstant(){};
	public static synchronized OAuthConstant getInstance(){
		if(instance==null)
			instance= new OAuthConstant();
		return instance;
	}
	public TaoBao getTaoBao(){
		if(taobao==null)
			taobao= new TaoBao();
		return taobao;
	}
	
	public AccessGrant getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(AccessGrant accessToken) {
		this.accessToken = accessToken;
		this.token=accessToken.getAccessToken();
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
