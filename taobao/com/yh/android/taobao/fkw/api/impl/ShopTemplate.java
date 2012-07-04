package com.yh.android.taobao.fkw.api.impl;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.domain.Shop;
import com.taobao.api.request.ShopRemainshowcaseGetRequest;
import com.taobao.api.response.ShopRemainshowcaseGetResponse;
import com.yh.android.taobao.fkw.Constants;
import com.yh.android.taobao.fkw.api.ShopOperations;

public class ShopTemplate extends AbstractOpenTaoBaoOperations implements ShopOperations{

	public ShopTemplate(OpenTaoBaoTemplate openTaobao, boolean isAuthorized) {
		super(openTaobao, isAuthorized);
	}

	@Override
	public Shop requestRemainShowCase() throws ApiException {
		DefaultTaobaoClient client = new DefaultTaobaoClient(Constants.BASE_URL, Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
		ShopRemainshowcaseGetRequest req=new ShopRemainshowcaseGetRequest();
		ShopRemainshowcaseGetResponse response=client.execute(req,opentaobao.withAccessTokenString());
		return response.getShop();
	}

}
