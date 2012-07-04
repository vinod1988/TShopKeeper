/**
 * 
 */
package com.yh.android.weibo.friendships;

import com.yh.android.weibo.User;
import com.yh.android.weibo.Weibo;
import com.yh.android.weibo.WeiboException;

/**
 * @author sina
 *
 */
public class DestroyFriendship {

	/**
	 * 取消关注
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		try {
			Weibo weibo = new Weibo();
			weibo.setToken(args[0],args[1]);
			User user = weibo.destroyFriendship(args[2]);//args[2]:关注用户的id
			System.out.println(user.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
