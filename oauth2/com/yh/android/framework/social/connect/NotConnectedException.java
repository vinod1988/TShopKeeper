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
package com.yh.android.framework.social.connect;

/**
 * Thrown by a {@link ConnectionRepository} when attempting to fetch a "primary" connection and the user is not connected to the provider in question.
 * @author Keith Donald
 * @see ConnectionRepository#getPrimaryConnection(Class)
 */
@SuppressWarnings("serial")
public class NotConnectedException extends ConnectionRepositoryException {

	private String providerId;

	public NotConnectedException(String providerId) {
		super("Not connected to provider '" + providerId + "'");
		this.providerId = providerId;
	}

	/**
	 * The id of the provider the current user is not connected to.
	 */
	public String getProviderId() {
		return providerId;
	}
	
}
