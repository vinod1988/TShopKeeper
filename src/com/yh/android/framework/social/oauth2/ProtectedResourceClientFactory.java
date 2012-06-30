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
package com.yh.android.framework.social.oauth2;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;


import com.yh.android.framework.http.client.ClientHttpRequestFactory;
import com.yh.android.framework.http.client.ClientHttpRequestInterceptor;
import com.yh.android.framework.social.support.ClientHttpRequestFactorySelector;
import com.yh.android.framework.util.ClassUtils;
import com.yh.android.framework.web.client.RestTemplate;

/**
 * Factory for RestTemplate instances that execute requests for resources protected by the OAuth 2 protocol.
 * Encapsulates the configuration of the interceptor that adds the necessary Authorization header to each request before it is executed.
 * Also hides the differences between Spring 3.0.x and 3.1 implementation.
 * @author Keith Donald
 */
class ProtectedResourceClientFactory {

	private static boolean interceptorsSupported = ClassUtils.isPresent("org.springframework.http.client.ClientHttpRequestInterceptor", ProtectedResourceClientFactory.class.getClassLoader());
	
	private static boolean listBasedInterceptors = false;

	private static Method setInterceptorsMethod;

	static {
		if (interceptorsSupported) {
			try {
				setInterceptorsMethod = RestTemplate.class.getMethod("setInterceptors", List.class);
				listBasedInterceptors = true;
			} catch (NoSuchMethodException e) {
				try {
					setInterceptorsMethod = RestTemplate.class.getMethod("setInterceptors", new ClientHttpRequestInterceptor[0].getClass());
				} catch (NoSuchMethodException shouldntHappen) {}
			}
		}
	}

	public static RestTemplate create(String accessToken, OAuth2Version version) {
		RestTemplate client = new RestTemplate(ClientHttpRequestFactorySelector.getRequestFactory());
		if (interceptorsSupported) {
			// favored
			OAuth2RequestInterceptor interceptor = new OAuth2RequestInterceptor(accessToken, version);
			try {
				if (listBasedInterceptors) {
					List<ClientHttpRequestInterceptor> interceptors = new LinkedList<ClientHttpRequestInterceptor>();
					interceptors.add(interceptor);
					setInterceptorsMethod.invoke(client, interceptors);			
				} else {
					setInterceptorsMethod.invoke(client, new Object[] {new ClientHttpRequestInterceptor[] { interceptor }});
				}
			} catch (Exception shouldntHappen) {}
		} else {
			// 3.0.x compatibility
			client.setRequestFactory(new Spring30OAuth2RequestFactory(client.getRequestFactory(), accessToken, version));
		}
		return client;				
	}

	public static ClientHttpRequestFactory addOAuthSigning(ClientHttpRequestFactory requestFactory, String accessToken, OAuth2Version version) {
		if (interceptorsSupported) {
			return requestFactory;
		}
		return new Spring30OAuth2RequestFactory(requestFactory, accessToken, version);
	}

}
