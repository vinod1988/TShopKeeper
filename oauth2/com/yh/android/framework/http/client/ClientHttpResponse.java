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

import com.yh.android.framework.http.HttpInputMessage;
import com.yh.android.framework.http.HttpStatus;

/**
 * Represents a client-side HTTP response. Obtained via an calling of the {@link ClientHttpRequest#execute()}.
 *
 * <p>A <code>ClientHttpResponse</code> must be {@linkplain #close() closed}, typically in a
 * <code>finally</code> block.
 *
 * @author Arjen Poutsma
 * @since 1.0
 */
public interface ClientHttpResponse extends HttpInputMessage {

	/**
	 * Return the HTTP status code of the response.
	 * @return the HTTP status as an HttpStatus enum value
	 * @throws IOException in case of I/O errors
	 */
	HttpStatus getStatusCode() throws IOException;
	
	/**
	 * Return the HTTP status code of the response as integer
	 * @return the HTTP status as an integer
	 * @throws IOException in case of I/O errors
	 */
	int getRawStatusCode() throws IOException;

	/**
	 * Return the HTTP status text of the response.
	 * @return the HTTP status text
	 * @throws IOException in case of I/O errors
	 */
	String getStatusText() throws IOException;

	/**
	 * Closes this response, freeing any resources created.
	 */
	void close();

}
