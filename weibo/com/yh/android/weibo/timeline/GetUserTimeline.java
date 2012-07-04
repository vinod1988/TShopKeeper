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
public class GetUserTimeline {

	/**
	 * 获取用户发布的微博信息列表 
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		try {
			Weibo weibo = new Weibo();
			weibo.setToken(args[0],args[1]);
			//获取24小时内前20条用户的微博信息;args[2]:用户ID
			List<Status> statuses = weibo.getUserTimeline(args[2],new Paging(1,20));
			for (Status status : statuses) {
	            System.out.println(status.getUser().getName() + ":" +status.getId()+":"+
	                               status.getText() + status.getOriginal_pic());
	        }
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
