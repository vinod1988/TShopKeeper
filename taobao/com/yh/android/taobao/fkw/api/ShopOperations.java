package com.yh.android.taobao.fkw.api;

import com.taobao.api.ApiException;
import com.taobao.api.response.ItemsInventoryGetResponse;
import com.taobao.api.response.ShopRemainshowcaseGetResponse;

public interface ShopOperations {

	ShopRemainshowcaseGetResponse requestRemainShowCase() throws ApiException;
	
	ItemsInventoryGetResponse inventoryGetRequest(String title,String banner,Long cid,String seller_cids,Long pageNo,Long pageSize) throws ApiException;
}
