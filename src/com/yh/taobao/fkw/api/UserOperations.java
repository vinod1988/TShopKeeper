package com.yh.taobao.fkw.api;

import java.util.List;

import com.taobao.api.ApiException;
import com.taobao.api.domain.User;


/**
 * 提供了用户基本信息查询功能
 */
public interface UserOperations {
	
	/**
	 * 得到单个用户
	 * @return 用户信息
	 */
	User getUser() throws ApiException;
	
	/**
	 * 得到单个用户
	 * @return 用户信息
	 */
	User getUser(String nick) throws ApiException;
	
	
	/**
	 * 传入多个淘宝会员帐号返回多个用户公开信息
	 * @param  nicks 用户昵称，多个以半角逗号(,)分隔，最多40个,hz0799,南古月
	 * @return 用户信息列表
	 */
	List<User> getUserList(String nicks) throws ApiException;
		
	//public static final String USER_ENDPOINT = "taobao.user.get";
	//public static final String USERS_ENDPOINT = "taobao.users.get";
	
}
