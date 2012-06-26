package com.hy.shopkeeper.fkw;

import com.hy.shopkeeper.org.json.JSONException;
import com.hy.shopkeeper.org.json.JSONObject;


/**
 * A data class representing Basic user information element
 */
public class TaoBaoAccessToken extends TaoBaoResponse implements java.io.Serializable {

	private String taobaoUserId;
	private String taobaoUserNick;
	private Integer expiresIn;
	private String accessToken;
	private String refreshToken;
	  
	/**
	 * 
	 */
	private static final long serialVersionUID = 3473349966713163765L;


	/*package*/TaoBaoAccessToken(JSONObject json) throws TaoBaoException {
		super();
		init(json);
	}

	private void init(JSONObject json) throws TaoBaoException {
		if(json!=null){
			try {
				taobaoUserId = json.getString("taobao_user_id");
				taobaoUserNick = json.getString("taobao_user_nick");
				refreshToken = json.getString("refresh_token");
				accessToken = json.getString("access_token");
				expiresIn= json.getInt("expires_in");
			} catch (JSONException jsone) {
				throw new TaoBaoException(jsone.getMessage() + ":" + json.toString(), jsone);
			}
		}
	}
	
	public String getTaobaoUserId() {
		return taobaoUserId;
	}

	public void setTaobaoUserId(String taobaoUserId) {
		this.taobaoUserId = taobaoUserId;
	}

	public String getTaobaoUserNick() {
		return taobaoUserNick;
	}

	public void setTaobaoUserNick(String taobaoUserNick) {
		this.taobaoUserNick = taobaoUserNick;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}


}
