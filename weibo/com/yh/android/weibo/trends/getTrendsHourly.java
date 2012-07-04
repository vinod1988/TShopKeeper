package com.yh.android.weibo.trends;

import java.util.List;

import com.yh.android.weibo.Paging;
import com.yh.android.weibo.Trends;
import com.yh.android.weibo.Weibo;
import com.yh.android.weibo.WeiboException;

public class getTrendsHourly {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
    	Paging paging = new Paging();
	    paging.setCount(20);
	    paging.setPage(1);
		try {
    		Weibo weibo = new Weibo();
		    weibo.setToken(args[0],args[1]);
		    List<Trends> trendsHourly = weibo.getTrendsHourly(0);
			System.out.println("=======按小时返回热门话题=======");
			System.out.println(trendsHourly);
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
