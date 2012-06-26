/*
Copyright (c) 2007-2009, Yusuke Yamamoto
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
 * Neither the name of the Yusuke Yamamoto nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY Yusuke Yamamoto ``AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Yusuke Yamamoto BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.hy.shopkeeper.fkw;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.hy.shopkeeper.org.json.JSONException;
import com.hy.shopkeeper.org.json.JSONObject;
import com.taobao.api.internal.util.WebUtils;

/**
 * A java reporesentation of the <a href="http://open.t.sina.com.cn/wiki/">Weibo API</a>
 * @editor sinaWeibo
 */
/**
 * @author sinaWeibo
 *
 */

public class TaoBao extends TaoBaoSupport implements java.io.Serializable {
		
	private String baseURL = Configuration.getScheme() + "gw.api.tbsandbox.com/router/rest";
	private String app_key=Configuration.getOAuthConsumerKey();
	private String app_secret=Configuration.getOAuthConsumerSecret();
    //private static final String requestTokenURL = Configuration.getScheme() + "oauth.tbsandbox.com/token";
	//private static final String authorizationURL = Configuration.getScheme() +"oauth.tbsandbox.com/authorize";
	
	private static final String requestTokenURL = Configuration.getScheme() + "oauth.taobao.com/token";
	private static final String authorizationURL = Configuration.getScheme() +"oauth.taobao.com/authorize";
	
	private static final String callbackURL="http://www.oauth.net/2";
	
	private static final long serialVersionUID = -1486360080128882436L;
	
	public String requestAuthorize() throws TaoBaoException, JSONException{
		TaoBaoParameter params = new TaoBaoParameter();
		params.add("response_type", "code");
		params.add("client_id", app_key);
		params.add("redirect_uri", callbackURL);
		params.add("state", "1212");
		params.add("scope", "item,promotion,item,usergrade");
		params.add("view", "wap");
		return  authorizationURL+"?"+Utility.encodeParameters(params);
	}
	
	public TaoBaoAccessToken requestAccessToken(String authorization_code) throws TaoBaoException, JSONException{
		Map<String, String> param = new HashMap<String, String>();
		
		param.put("client_id", app_key);
		param.put("client_secret", app_secret);
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
}