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
package com.yh.android.framework.social.support;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.yh.android.framework.http.HttpHeaders;
import com.yh.android.framework.http.HttpStatus;
import com.yh.android.framework.http.client.ClientHttpResponse;
import com.yh.android.framework.util.ClassUtils;

/**
 * {@link com.yh.android.framework.http.client.ClientHttpResponse} implementation that uses
 * Apache Http Components HttpClient to execute requests.
 *
 * <p>Created via the {@link HttpComponentsClientHttpRequest}.
 *
 * @author Oleg Kalnichevski
 * @author Arjen Poutsma
 * @author Roy Clarkson
 * @since 3.0
 * @see HttpComponentsClientHttpRequest#execute()
 */
final class HttpComponentsClientHttpResponse implements ClientHttpResponse {
	
	private static final boolean VERSION_4_1_AVAILABLE = ClassUtils.hasMethod(EntityUtils.class, "consume", new Class<?>[]{HttpEntity.class});

	private final HttpResponse httpResponse;

	private HttpHeaders headers;

	public HttpComponentsClientHttpResponse(HttpResponse httpResponse) {
		this.httpResponse = httpResponse;
	}

	public HttpStatus getStatusCode() throws IOException {
		return HttpStatus.valueOf(httpResponse.getStatusLine().getStatusCode());
	}

	public String getStatusText() throws IOException {
		return httpResponse.getStatusLine().getReasonPhrase();
	}

	public int getRawStatusCode() throws IOException {
		return httpResponse.getStatusLine().getStatusCode();
	}
	
	public HttpHeaders getHeaders() {
		if (headers == null) {
			headers = new HttpHeaders();
			for (Header header : httpResponse.getAllHeaders()) {
				headers.add(header.getName(), header.getValue());
			}
		}
		return headers;
	}

	public InputStream getBody() throws IOException {
		HttpEntity entity = httpResponse.getEntity();
		return entity != null ? entity.getContent() : null;
	}
	
	public void close() {
		HttpEntity entity = httpResponse.getEntity();
		// Release underlying connection back to the connection manager
		if (VERSION_4_1_AVAILABLE) {
			HttpComponentsClient_4_0.close(entity);
		} else {
			HttpComponentsClient_4_0.close(entity);
		}
	}


	// internal helpers

//	private static class HttpComponentsClient_4_1 {
//
//		public static void close(HttpEntity entity) {
//			if (entity != null) {
//				try {
//					// Release underlying connection back to the connection manager
//					EntityUtils.consume(entity);
//				}
//				catch (IOException e) {
//					// ignore
//				}
//			}
//		}
//	}
	
	private static class HttpComponentsClient_4_0 {

		@SuppressWarnings("deprecation")
		public static void close(HttpEntity entity) {
			if (entity != null) {
				try {
					// Release underlying connection back to the connection manager
					entity.consumeContent();
				}
				catch (IOException e) {
					// ignore
				}
			}
		}
	}

}
