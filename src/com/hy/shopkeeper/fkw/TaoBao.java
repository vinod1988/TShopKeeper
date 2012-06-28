package com.hy.shopkeeper.fkw;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.hy.shopkeeper.org.json.JSONException;
import com.hy.shopkeeper.org.json.JSONObject;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.domain.Shop;
import com.taobao.api.domain.User;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.api.request.ShopRemainshowcaseGetRequest;
import com.taobao.api.request.UserGetRequest;
import com.taobao.api.response.ShopGetResponse;
import com.taobao.api.response.ShopRemainshowcaseGetResponse;
import com.taobao.api.response.UserGetResponse;

public class TaoBao implements java.io.Serializable {
			
	private static final long serialVersionUID = -1486360080128882436L;
		
	public static String requestAuthorize() throws TaoBaoException, JSONException{
		final String authorizationURL = "https://oauth.taobao.com/authorize";
		final String callbackURL="http://www.oauth.net/2";
		TaoBaoParameter params = new TaoBaoParameter();
		params.add("response_type", "code");
		params.add("client_id", Constants.CONSUMER_KEY);
		params.add("redirect_uri", callbackURL);
		params.add("state", "1212");
		params.add("scope", "item,promotion,item,usergrade");
		params.add("view", "wap");
		return  authorizationURL+"?"+Utility.encodeParameters(params);
	}
	
	public static TaoBaoAccessToken requestAccessToken(String authorization_code) throws TaoBaoException, JSONException{
		
		final String requestTokenURL = "https://oauth.taobao.com/token";
		final String callbackURL="http://www.oauth.net/2";
		Map<String, String> param = new HashMap<String, String>();
		param.put("client_id", Constants.CONSUMER_KEY);
		param.put("client_secret", Constants.CONSUMER_SECRET);
		param.put("grant_type", "authorization_code");
		param.put("redirect_uri", callbackURL);
		param.put("code", authorization_code);
		param.put("state", "1212");
		param.put("scope", "item");
		param.put("view", "wap");
		String responseJson="";
		try{
			responseJson=WebUtils.doPost(requestTokenURL, param, 3000, 3000);
		}catch(IOException io){
			throw new TaoBaoException(io);
		}
		JSONObject json=new JSONObject(responseJson);
		TaoBaoAccessToken accessToken=new TaoBaoAccessToken(json);
		return accessToken;
	}	
	
	public static User requestUserInfo(String token) throws ApiException{
		DefaultTaobaoClient client = new DefaultTaobaoClient(Constants.BASE_URL, Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
		UserGetRequest req=new UserGetRequest();
		req.setFields("user_id,uid,nick,sex,buyer_credit,seller_credit,location,created,last_visit,birthday,type,status,alipay_no,alipay_account,alipay_account,email,consumer_protection,alipay_bind");
		UserGetResponse response = client.execute(req);
        return response.getUser();
	}
	
	public static Shop requestRemainShowCase(String token) throws ApiException{
		DefaultTaobaoClient client = new DefaultTaobaoClient(Constants.BASE_URL, Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
		ShopRemainshowcaseGetRequest req=new ShopRemainshowcaseGetRequest();
		ShopRemainshowcaseGetResponse response=client.execute(req,token);
		return response.getShop();
	}
}