package com.yh.taobao.fkw.connect;

import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;

import com.taobao.api.domain.User;
import com.yh.taobao.fkw.api.OpenTaoBao;

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