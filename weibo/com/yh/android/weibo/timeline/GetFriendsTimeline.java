/**
 * 
 */
package com.yh.android.weibo.timeline;

import java.util.List;

import com.yh.android.weibo.Paging;
import com.yh.android.weibo.Status;
import com.yh.android.weibo.Weibo;
import com.yh.android.weibo.WeiboException;

/**
 * @author sina
 *
 */
public class GetFriendsTimeline {

	/**
	 * 获取当前用户所关注用户的最新微博信息 (别名: statuses/home_timeline) 
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		
		try {
			//获取前20条关注用户的微博信息
			Weibo weibo = new Weibo();
			weibo.setToken(args[0],args[1]);
			Paging page=new Paging(2);
			List<Status> statuses = weibo.getFriendsTimeline(page);
			for (Status status : statuses) {
	            System.out.println(status.getUser().getName() + ":" +
	                               status.getText());
	        }
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
