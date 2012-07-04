package com.yh.android.taobao.fkw.api;

import com.taobao.api.ApiException;
import com.taobao.api.domain.Shop;

public interface ShopOperations {

	Shop requestRemainShowCase() throws ApiException;
	
}
