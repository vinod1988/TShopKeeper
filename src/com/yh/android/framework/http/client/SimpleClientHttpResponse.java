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

package com.yh.android.framework.http.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;


import com.yh.android.framework.http.HttpHeaders;
import com.yh.android.framework.http.HttpStatus;
import com.yh.android.framework.util.StringUtils;

/**
 * {@link ClientHttpResponse} implementation that uses standard J2SE facilities.
 * Obtained via {@link SimpleBufferingClientHttpRequest#execute()} and
 * {@link SimpleStreamingClientHttpRequest#execute()}.
 *
 * @author Arjen Poutsma
 * @author Roy Clarkson
 * @since 1.0
 */
final class SimpleClientHttpResponse extends AbstractClientHttpResponse {

	private static final String AUTHENTICATION_ERROR = "Received authentication challenge is null";

	private final HttpURLConnection connection;

	private HttpHeaders headers;

	SimpleClientHttpResponse(HttpURLConnection connection) {
		this.connection = connection;
	}
	
	public int getRawStatusCode() throws IOException {
		return this.connection.getResponseCode();
	}

	@Override
	public HttpStatus getStatusCode() throws IOException {
		try {
			return HttpStatus.valueOf(getRawStatusCode());
		} catch (IOException ex) {
			/* 
			 * If credentials are incorrect or not provided for Basic Auth, then 
			 * Android throws this exception when an HTTP 401 is received. Checking 
			 * for this response and returning the proper status.
			 */
			if (ex.getLocalizedMessage().equals(AUTHENTICATION_ERROR)) {
				return HttpStatus.UNAUTHORIZED;
			} else {
				throw ex;
			}
		}
	}

	public String getStatusText() throws IOException {
		try {
			return this.connection.getResponseMessage();
		} catch (IOException ex) {
			/* 
			 * If credentials are incorrect or not provided for Basic Auth, then 
			 * Android throws this exception when an HTTP 401 is received. Checking 
			 * for this response and returning the proper status.
			 */
			if (ex.getLocalizedMessage().equals(AUTHENTICATION_ERROR)) {
				return HttpStatus.UNAUTHORIZED.getReasonPhrase();
			} else {
				throw ex;
			}
		}
	}

	public HttpHeaders getHeaders() {
		if (this.headers == null) {
			this.headers = new HttpHeaders();
			// Header field 0 is the status line for most HttpURLConnections, but not on GAE
			String name = this.connection.getHeaderFieldKey(0);
			if (StringUtils.hasLength(name)) {
				this.headers.add(name, this.connection.getHeaderField(0));
			}
			int i = 1;
			while (true) {
				name = this.connection.getHeaderFieldKey(i);
				if (!StringUtils.hasLength(name)) {
					break;
				}
				this.headers.add(name, this.connection.getHeaderField(i));
				i++;
			}
		}
		return this.headers;
	}

	@Override
	protected InputStream getBodyInternal() throws IOException {
		InputStream errorStream = this.connection.getErrorStream();
		return (errorStream != null ? errorStream : this.connection.getInputStream());
	}

	@Override
	protected void closeInternal() {
		this.connection.disconnect();
	}

}
