/*
 * Copyright 2002-2009 the original author or authors.
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

package com.yh.android.framework.http.converter;

/**
 * Thrown by {@link com.yh.android.framework.http.converter.HttpMessageConverter} implementations when the
 * {@link com.yh.android.framework.http.converter.HttpMessageConverter#write(Object, com.yh.android.framework.http.MediaType, 
 * com.yh.android.framework.http.HttpOutputMessage) write} method fails.
 *
 * @author Arjen Poutsma
 * @author Roy Clarkson
 * @since 1.0
 */
public class HttpMessageNotWritableException extends HttpMessageConversionException {

	private static final long serialVersionUID = 1L;

	/**
	 * Create a new HttpMessageNotWritableException.
	 *
	 * @param msg the detail message
	 */
	public HttpMessageNotWritableException(String msg) {
		super(msg);
	}

	/**
	 * Create a new HttpMessageNotWritableException.
	 *
	 * @param msg the detail message
	 * @param cause the root cause (if any)
	 */
	public HttpMessageNotWritableException(String msg, Throwable cause) {
		super(msg, cause);
	}
}