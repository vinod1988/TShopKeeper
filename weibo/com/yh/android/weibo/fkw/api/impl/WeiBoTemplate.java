package com.yh.android.weibo.fkw.api.impl;

import com.yh.android.weibo.User;
import com.yh.android.weibo.fkw.api.WeiBoOperations;


public class WeiBoTemplate extends AbstractOpenWeiBoOperations implements WeiBoOperations{

	public WeiBoTemplate(OpenWeiBoTemplate openWeiBo, boolean isAuthorized) {
		super(openWeiBo, isAuthorized);
	}

	@Override
	public User getUser() {
		// TODO Auto-generated method stub
		return null;
	}

}
