package com.yh.android.taobao.fkw;

import com.yh.android.framework.org.json.JSONException;
import com.yh.android.framework.org.json.JSONObject;
import com.yh.android.framework.social.oauth2.AccessGrant;



/**
 * A data class representing Basic user information element
 */
public class TaoBaoAccessToken implements java.io.Serializable {

	private String taobaoUserId;
	private String taobaoUserNick;
	private Integer expiresIn;
	private String accessToken;
	private String refreshToken;
	private Long expireTime;
	private String scope;
	
	private AccessGrant accessGrant;
	  
	/**
	 * 
	 */
	private static final long serialVersionUID = 3473349966713163765L;


	/*package*/public TaoBaoAccessToken(JSONObject json) throws JSONException {
		super();
		init(json);
	}

	private void init(JSONObject json) throws JSONException {
		if(json!=null){
			try {
				if(json.has("taobao_user_id")){
					taobaoUserId = json.getString("taobao_user_id");
				}
				if(json.has("taobao_user_nick")){
					taobaoUserNick = json.getString("taobao_user_nick");
				}
				if(json.has("scope")){
					scope = json.getString("scope");
				}
				refreshToken = json.getString("refresh_token");
				accessToken = json.getString("access_token");
				expiresIn= json.getInt("expires_in");
				
				expireTime=(expiresIn != null ? System.currentTimeMillis() + expiresIn * 1000 : null);
				accessGrant=(new AccessGrant(accessToken,scope,refreshToken,expiresIn));
			} catch (JSONException jsone) {
				throw jsone;
			}
		}
	}
	
	public String getTaobaoUserId() {
		return taobaoUserId;
	}

	public String getTaobaoUserNick() {
		return taobaoUserNick;
	}
	
	public Integer getExpiresIn() {
		return expiresIn;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}
	
	public Long getExpireTime() {
		return expireTime;
	}
	
	public boolean isExpire(){
		if(expiresIn != null){
			return expireTime<System.currentTimeMillis()?true:false;
		}else{
			return true;
		}
	}

	public AccessGrant getAccessGrant() {
		return accessGrant;
	}
}
