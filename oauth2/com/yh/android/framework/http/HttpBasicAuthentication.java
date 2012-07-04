/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yh.android.framework.http;

import com.yh.android.framework.util.Base64Utils;

/**
 * Represents HTTP Basic Authentication. Intended for use
 * with {@link com.yh.android.framework.http.client.ClientHttpRequest}
 * and {@link com.yh.android.framework.web.client.RestTemplate}.
 * @see <a href="http://www.ietf.org/rfc/rfc2617.txt">RFC2617</a>
 * @author Jonathan Sweemer
 * @author Roy Clarkson
 */
public class HttpBasicAuthentication extends HttpAuthentication {

	private final String username;
	private final String password;

	public HttpBasicAuthentication(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the value for the 'Authorization' HTTP header.
	 */
	public String getHeaderValue() {
		byte[] bytes = String.format("%s:%s", username, password).getBytes();
		return String.format("Basic %s", Base64Utils.encodeToString(bytes));
	}

	@Override
	public String toString() {
		String s = null;
		try {
			s = String.format("Authorization: %s", getHeaderValue());
		} catch (RuntimeException re) {
			return null;
		}
		return s;
	}

}
