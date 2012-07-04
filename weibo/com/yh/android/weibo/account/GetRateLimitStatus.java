/**
 * 
 */
package com.yh.android.weibo.account;

import com.yh.android.weibo.RateLimitStatus;
import com.yh.android.weibo.Weibo;
import com.yh.android.weibo.WeiboException;

/**
 * @author sina
 *
 */
public class GetRateLimitStatus {

	/**
	 * 获取当前用户API访问频率限制
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		try {
			Weibo weibo = new Weibo();
			weibo.setToken(args[0], args[1]);
			RateLimitStatus limitStatus = weibo.rateLimitStatus();
			System.out.println(limitStatus.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}