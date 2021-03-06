/*
 * Copyright 2002-2012 the original author or authors.
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

package com.yh.android.framework.http.client.support;

import java.util.List;


import com.yh.android.framework.http.client.ClientHttpRequestFactory;
import com.yh.android.framework.http.client.ClientHttpRequestInterceptor;
import com.yh.android.framework.http.client.InterceptingClientHttpRequestFactory;
import com.yh.android.framework.util.CollectionUtils;

/**
 * Base class for {@link com.yh.android.framework.web.client.RestTemplate} and other HTTP accessing gateway helpers, adding
 * interceptor-related properties to {@link HttpAccessor}'s common properties.
 *
 * <p>Not intended to be used directly. See {@link com.yh.android.framework.web.client.RestTemplate}.
 *
 * @author Arjen Poutsma
 * @since 1.0
 */
public abstract class InterceptingHttpAccessor extends HttpAccessor {

	private List<ClientHttpRequestInterceptor> interceptors;

	/**
	 * Sets the request interceptors that this accessor should use.
	 */
	public void setInterceptors(List<ClientHttpRequestInterceptor> interceptors) {
		this.interceptors = interceptors;
	}

	/**
	 * Return the request interceptor that this accessor uses.
	 */
	public List<ClientHttpRequestInterceptor> getInterceptors() {
		return this.interceptors;
	}

	@Override
	public ClientHttpRequestFactory getRequestFactory() {
		ClientHttpRequestFactory delegate = super.getRequestFactory();
		if (!CollectionUtils.isEmpty(getInterceptors())) {
			return new InterceptingClientHttpRequestFactory(delegate, getInterceptors());
		}
		else {
			return delegate;
		}
	}

}
