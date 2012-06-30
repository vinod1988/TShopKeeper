package com.yh.taobao.fkw.api.impl;

import java.util.List;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.domain.User;
import com.taobao.api.request.UserGetRequest;
import com.taobao.api.response.UserGetResponse;
import com.yh.taobao.fkw.Constants;
import com.yh.taobao.fkw.api.UserOperations;

/**
 * Implementation of {@link UserOperations}, providing a binding to Instagram's user-oriented REST resources.
 */
public class UserTemplate extends AbstractOpenTaoBaoOperations implements UserOperations {
	
	public UserTemplate(OpenTaoBaoTemplate instagram, boolean isAuthorizedForUser) {
		super(instagram, isAuthorizedForUser);
	}

	@Override
	public User getUser(String nick) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUserList(String nicks) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser() throws ApiException {
		requireUserAuthorization();
		DefaultTaobaoClient client = new DefaultTaobaoClient(Constants.BASE_URL, Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
		UserGetRequest req=new UserGetRequest();
		req.setFields("user_id,uid,nick,sex,buyer_credit,seller_credit,location,created,last_visit,birthday,type,status,alipay_no,alipay_account,alipay_account,email,consumer_protection,alipay_bind");
		UserGetResponse response = client.execute(req);
        return response.getUser();
	}

}
