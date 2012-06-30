/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yh.android.framework.social.connect.support;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


import com.yh.android.framework.core.GenericTypeResolver;
import com.yh.android.framework.social.ExpiredAuthorizationException;
import com.yh.android.framework.social.ServiceProvider;
import com.yh.android.framework.social.connect.ApiAdapter;
import com.yh.android.framework.social.connect.Connection;
import com.yh.android.framework.social.connect.ConnectionData;
import com.yh.android.framework.social.oauth2.AccessGrant;
import com.yh.android.framework.social.oauth2.OAuth2ServiceProvider;

/**
 * An OAuth2-based Connection implementation.
 * In general, this implementation is expected to be suitable for all OAuth2-based providers and should not require subclassing.
 * Subclasses of {@link OAuth2ConnectionFactory} should be favored to encapsulate details specific to an OAuth2-based provider.
 * @author Keith Donald
 * @param <A> the service provider's API type
 * @see OAuth2ConnectionFactory
 */
public class OAuth2Connection<A> extends AbstractConnection<A> {

	private final OAuth2ServiceProvider<A> serviceProvider;

	private String accessToken;
	
	private String refreshToken;
	
	private Long expireTime;

	private A api;
	
	private A apiProxy;

	/**
	 * Creates a new {@link OAuth2Connection} from a access grant response.
	 * Designed to be called to establish a new {@link OAuth2Connection} after receiving an access grant successfully.
	 * The providerUserId may be null in this case: if so, this constructor will try to resolve it using the service API obtained from the {@link OAuth2ServiceProvider}.
	 * @param providerId the provider id e.g. "facebook".
	 * @param providerUserId the provider user id (may be null if not returned as part of the access grant)
	 * @param accessToken the granted access token
	 * @param refreshToken the granted refresh token
	 * @param expireTime the access token expiration time
	 * @param serviceProvider the OAuth2-based ServiceProvider
	 * @param apiAdapter the ApiAdapter for the ServiceProvider
	 */
	public OAuth2Connection(String providerId, String providerUserId, String accessToken, String refreshToken, Long expireTime,
			OAuth2ServiceProvider<A> serviceProvider, ApiAdapter<A> apiAdapter) {
		super(apiAdapter);
		this.serviceProvider = serviceProvider;
		initAccessTokens(accessToken, refreshToken, expireTime);
		initApi();
		initApiProxy();
		initKey(providerId, providerUserId);
	}
	
	/**
	 * Creates a new {@link OAuth2Connection} from the data provided.
	 * Designed to be called when re-constituting an existing {@link Connection} from {@link ConnectionData}.
	 * @param data the data holding the state of this connection
	 * @param serviceProvider the OAuth2-based ServiceProvider
	 * @param apiAdapter the ApiAdapter for the ServiceProvider
	 */
	public OAuth2Connection(ConnectionData data, OAuth2ServiceProvider<A> serviceProvider, ApiAdapter<A> apiAdapter) {
		super(data, apiAdapter);
		this.serviceProvider = serviceProvider;
		initAccessTokens(data.getAccessToken(), data.getRefreshToken(), data.getExpireTime());
		initApi();
		initApiProxy();
	}

	// implementing Connection

	public boolean hasExpired() {
		synchronized (getMonitor()) {
			return expireTime != null && System.currentTimeMillis() >= expireTime;
		}
	}

	public void refresh() {
		synchronized (getMonitor()) {
			AccessGrant accessGrant = serviceProvider.getOAuthOperations().refreshAccess(refreshToken, null, null);
			initAccessTokens(accessGrant.getAccessToken(), accessGrant.getRefreshToken(), accessGrant.getExpireTime());
			initApi();
		}
	}

	public A getApi() {
		if (apiProxy != null) {
			return apiProxy;
		} else {
			synchronized (getMonitor()) {
				return api;
			}
		}
	}

	public ConnectionData createData() {
		synchronized (getMonitor()) {
			return new ConnectionData(getKey().getProviderId(), getKey().getProviderUserId(), getDisplayName(), getProfileUrl(), getImageUrl(), accessToken, null, refreshToken, expireTime);
		}
	}

	// internal helpers
	
	private void initAccessTokens(String accessToken, String refreshToken, Long expireTime) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expireTime = expireTime;		
	}
	
	private void initApi() {
		api = serviceProvider.getApi(accessToken);
	}
	
	@SuppressWarnings("unchecked")
	private void initApiProxy() {
		Class<?> apiType = GenericTypeResolver.resolveTypeArgument(serviceProvider.getClass(), ServiceProvider.class);
		if (apiType.isInterface()) {
			apiProxy = (A) Proxy.newProxyInstance(apiType.getClassLoader(), new Class[] { apiType }, new ApiInvocationHandler());
		}		
	}
	
	private class ApiInvocationHandler implements InvocationHandler {

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			synchronized (getMonitor()) {
				if (hasExpired()) {
					throw new ExpiredAuthorizationException();
				}
				try {
					return method.invoke(OAuth2Connection.this.api, args);
				} catch (InvocationTargetException e) {
					throw e.getTargetException();
				}
			}
		}
	}

}