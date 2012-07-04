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


import com.yh.android.framework.social.connect.ApiAdapter;
import com.yh.android.framework.social.connect.Connection;
import com.yh.android.framework.social.connect.ConnectionData;
import com.yh.android.framework.social.oauth1.OAuth1ServiceProvider;

/**
 * An OAuth1-based Connection implementation.
 * In general, this implementation is expected to be suitable for all OAuth1-based providers and should not require subclassing.
 * Subclasses of {@link OAuth1ConnectionFactory} should be favored to encapsulate details specific to an OAuth1-based provider.
 * @author Keith Donald
 * @param <A> the service API type
 * @see OAuth1ConnectionFactory
 */
public class OAuth1Connection<A> extends AbstractConnection<A> {

	private final OAuth1ServiceProvider<A> serviceProvider;
	
	private String accessToken;
	
	private String secret;

	private A api;

	/**
	 * Creates a new {@link OAuth1Connection} from a OAuth1 access token response.
	 * Designed to be called to establish a new {@link OAuth1Connection} after receiving an access token response successfully.
	 * The providerUserId may be null in this case: if so, this constructor will try to resolve it using the service API obtained from the {@link OAuth1ServiceProvider}.
	 * @param providerId the provider id e.g. "twitter"
	 * @param providerUserId the provider user ID (may be null if not returned as part of the access token response)
	 * @param accessToken the granted access token
	 * @param secret the access token secret (OAuth1-specific)
	 * @param serviceProvider the OAuth1-based ServiceProvider
	 * @param apiAdapter the ApiAdapter for the ServiceProvider
	 */
	public OAuth1Connection(String providerId, String providerUserId, String accessToken, String secret, OAuth1ServiceProvider<A> serviceProvider, ApiAdapter<A> apiAdapter) {
		super(apiAdapter);
		this.serviceProvider = serviceProvider;
		initAccessTokens(accessToken, secret);
		initApi();
		initKey(providerId, providerUserId);
	}

	/**
	 * Creates a new {@link OAuth1Connection} from the data provided.
	 * Designed to be called when re-constituting an existing {@link Connection} using {@link ConnectionData}.
	 * @param data the data holding the state of this connection
	 * @param serviceProvider the OAuth1-based ServiceProvider
	 * @param apiAdapter the ApiAdapter for the ServiceProvider
	 */
	public OAuth1Connection(ConnectionData data, OAuth1ServiceProvider<A> serviceProvider, ApiAdapter<A> apiAdapter) {
		super(data, apiAdapter);
		this.serviceProvider = serviceProvider;
		initAccessTokens(data.getAccessToken(), data.getSecret());
		initApi();
	}

	// implementing Connection
	
	public A getApi() {
		return api;
	}

	public ConnectionData createData() {
		synchronized (getMonitor()) {
			return new ConnectionData(getKey().getProviderId(), getKey().getProviderUserId(), getDisplayName(), getProfileUrl(), getImageUrl(), accessToken, secret, null, null);
		}
	}

	// internal helpers
	
	private void initAccessTokens(String accessToken, String secret) {
		this.accessToken = accessToken;
		this.secret = secret;
	}

	private void initApi() {
		api = serviceProvider.getApi(accessToken, secret);
	}
	
}
