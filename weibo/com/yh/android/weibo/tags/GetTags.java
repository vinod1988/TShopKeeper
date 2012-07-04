package com.yh.android.weibo.tags;

import java.util.List;

import com.yh.android.weibo.Tag;
import com.yh.android.weibo.Weibo;

public class GetTags{

	/**
	 * 获取tags；
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
        try {
        	Weibo weibo = new Weibo();
			weibo.setToken(args[0],args[1]);
            List<Tag> gettags=weibo.gettags(args[2]);
        	for(Tag status : gettags) {
        		System.out.println( status.toString());
        	}
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}