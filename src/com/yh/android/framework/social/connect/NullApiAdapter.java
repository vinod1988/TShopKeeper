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

final class NullApiAdapter implements ApiAdapter<Object> {

	public static final NullApiAdapter INSTANCE = new NullApiAdapter();
	
	public boolean test(Object api) {
		return true;
	}

	public void setConnectionValues(Object api, ConnectionValues values) {
		
	}

	public UserProfile fetchUserProfile(Object api) {
		return UserProfile.EMPTY;
	}

	public void updateStatus(Object api, String message) {
	}

	// internal helpers
	
	private NullApiAdapter() {}
	
}
