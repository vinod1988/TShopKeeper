package com.yh.android.taobao.fkw.api.impl;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.domain.Shop;
import com.taobao.api.request.ItemsInventoryGetRequest;
import com.taobao.api.request.ShopRemainshowcaseGetRequest;
import com.taobao.api.response.ItemsInventoryGetResponse;
import com.taobao.api.response.ShopRemainshowcaseGetResponse;
import com.yh.android.taobao.fkw.Constants;
import com.yh.android.taobao.fkw.api.ShopOperations;

public class ShopTemplate extends AbstractOpenTaoBaoOperations implements ShopOperations{

	public ShopTemplate(OpenTaoBaoTemplate openTaobao, boolean isAuthorized) {
		super(openTaobao, isAuthorized);
	}

	@Override
	public ShopRemainshowcaseGetResponse requestRemainShowCase() throws ApiException {
		DefaultTaobaoClient client = new DefaultTaobaoClient(Constants.BASE_URL, Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
		ShopRemainshowcaseGetRequest req=new ShopRemainshowcaseGetRequest();
		ShopRemainshowcaseGetResponse response=client.execute(req,opentaobao.withAccessTokenString());
		return response;
	}

	@Override
	public ItemsInventoryGetResponse inventoryGetRequest(String title,String banner,Long cid,String sellerCids,Long pageNo,Long pageSize) throws ApiException {
		// TODO Auto-generated method stub
		DefaultTaobaoClient client = new DefaultTaobaoClient(Constants.BASE_URL, Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
		ItemsInventoryGetRequest req=new ItemsInventoryGetRequest();
		req.setBanner(banner);
		if(title.length()>0){
			req.setQ(title);
		}
		if(title.length()>0){
			req.setQ(title);
		}
		if(cid>0){
			req.setCid(cid);
		}
		if(sellerCids.length()>0){
			req.setSellerCids(sellerCids);
		}
		req.setFields("approve_status,num_iid,title,nick,type,cid,pic_url,num,props,valid_thru, list_time,price,has_discount,has_invoice,has_warranty,has_showcase, modified,delist_time,postage_id,seller_cids,outer_id");
		req.setPageNo(pageNo);
		req.setPageSize(pageSize);
		req.setOrderBy("list_time:desc");
		req.setIsTaobao(true);
		req.setIsEx(true);
		ItemsInventoryGetResponse response=client.execute(req,opentaobao.withAccessTokenString());
		return response;
	}

}
