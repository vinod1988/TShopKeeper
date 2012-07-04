package com.yh.android.weibo.tags;

import java.util.List;

import com.yh.android.weibo.Tag;
import com.yh.android.weibo.Weibo;

public class DestroyTags {
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
        try {
        	Weibo weibo = new Weibo();
			weibo.setToken(args[0],args[1]);
            List<Tag> list =  (List<Tag>) weibo.destory_batchTags(args[2]);
        	for(Tag status : list) {
        		System.out.println( status.toString());
        	}
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

