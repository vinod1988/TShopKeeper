﻿
package com.yh.android.weibo.ids;

import com.yh.android.weibo.Weibo;
import com.yh.android.weibo.WeiboException;


public class GetFriendsIDs {

	/**
	 * 获取用户关注对象uid列表 
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		try {
			Weibo weibo = new Weibo();
			weibo.setToken(args[0],args[1]);
			//args[2]:关注用户的id
			long[] ids = weibo.getFriendsIDs(args[2]).getIDs();
			for(long id : ids) {
				System.out.println(id);
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
