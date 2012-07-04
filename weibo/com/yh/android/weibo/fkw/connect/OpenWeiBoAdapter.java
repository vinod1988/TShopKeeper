package com.yh.android.weibo.fkw.connect;

import com.weibo.net.WeiboOAuth2;
import com.yh.android.framework.social.connect.ApiAdapter;
import com.yh.android.framework.social.connect.ConnectionValues;
import com.yh.android.framework.social.connect.UserProfile;
import com.yh.android.framework.social.connect.UserProfileBuilder;
import com.yh.android.weibo.OAuthConstant;
import com.yh.android.weibo.User;
import com.yh.android.weibo.Weibo;
import com.yh.android.weibo.fkw.api.OpenWeiBo;

/**
 * OpenTaoBao ApiAdapter implementation.
 */
public class OpenWeiBoAdapter implements ApiAdapter<OpenWeiBo> {
	
	public void setConnectionValues(OpenWeiBo instagram, ConnectionValues values) {
		User profile=null;
		try {
			Weibo weibo = OAuthConstant.getInstance().getWeibo();
			weibo.setToken(WeiboOAuth2.getInstance().getAccessToken().getToken(), WeiboOAuth2.getInstance().getAccessToken().getSecret());			
			User user = weibo.verifyCredentials();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(profile!=null){
			values.setDisplayName(profile.getScreenName());
		}
	}


	@Override
	public boolean test(OpenWeiBo api) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserProfile fetchUserProfile(OpenWeiBo api) {
		User profile = null;
		try {
			Weibo weibo = OAuthConstant.getInstance().getWeibo();
			weibo.setToken(WeiboOAuth2.getInstance().getAccessToken().getToken(), WeiboOAuth2.getInstance().getAccessToken().getSecret());			
			User user = weibo.verifyCredentials();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new UserProfileBuilder().setName(profile.getScreenName()).setUsername(profile.getName()).build();
	}


	@Override
	public void updateStatus(OpenWeiBo api, String message) {
		// TODO Auto-generated method stub
		
	}

}