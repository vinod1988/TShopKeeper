package com.yh.android.taobao.fkw.connect;

import com.yh.android.framework.social.ApiException;
import com.yh.android.framework.social.connect.ApiAdapter;
import com.yh.android.framework.social.connect.ConnectionValues;
import com.yh.android.framework.social.connect.UserProfile;
import com.yh.android.framework.social.connect.UserProfileBuilder;
import com.yh.android.taobao.fkw.api.OpenTaoBao;

import com.taobao.api.domain.User;

/**
 * OpenTaoBao ApiAdapter implementation.
 */
public class OpenTaoBaoAdapter implements ApiAdapter<OpenTaoBao> {
	
	public void setConnectionValues(OpenTaoBao instagram, ConnectionValues values) {
		User profile=null;
		try {
			profile = instagram.userOperations().getUser();
		} catch (com.taobao.api.ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(profile!=null){
			values.setDisplayName(profile.getNick());
			values.setImageUrl(profile.getAvatar());
		}
		
	}

	public UserProfile fetchUserProfile(OpenTaoBao instagram) {
		User profile = null;
		try {
			profile = instagram.userOperations().getUser();
		} catch (com.taobao.api.ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new UserProfileBuilder().setName(profile.getNick()).setUsername(profile.getNick()).build();
	}

	public void updateStatus(OpenTaoBao instagram, String message) {
		// 
	}

	@Override
	public boolean test(OpenTaoBao api) {
		// TODO Auto-generated method stub
		return false;
	}

}