package com.hy.shopkeeper.service;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.request.UserGetRequest;

public class UserService {
	
	public void getUser(){
		
		DefaultTaobaoClient client = new DefaultTaobaoClient(Constants.API_URL, Constants.APP_KEY, Constants.APP_SECRET);
		UserGetRequest req=new UserGetRequest();
		req.setFields("user_id,nick,seller_credit");
		req.setNick("hz0799");
		//UserGetResponse response = client.Execute(req, sessionKey);
		
	}

}
