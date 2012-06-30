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

import com.yh.android.framework.util.MultiValueMap;

/**
 * A service interface for the OAuth2 flow.
 * This interface allows you to conduct the "OAuth dance" with a service provider on behalf of a user. 
 * @author Keith Donald
 * @author Roy Clarkson
 */
public interface OAuth2Operations {

	/**
	 * Construct the URL to redirect the user to for authorization.
	 * @param grantType specifies whether to use client-side or server-side OAuth flow
	 * @param parameters authorization parameters needed to build the URL
	 * @return the absolute authorize URL to redirect the user to for authorization
	 */ 
	String buildAuthorizeUrl(GrantType grantType, OAuth2Parameters parameters);

	/**
	 * Construct the URL to redirect the user to for authentication.
	 * The authenticate URL differs from the authorizationUrl slightly in that it does not require the user to authorize the app multiple times.
	 * This provides a better user experience for "Sign in with Provider" scenarios.
	 * @param grantType specifies whether to use client-side or server-side OAuth flow
	 * @param parameters authorization parameters needed to build the URL 
	 * @return the absolute authenticate URL to redirect the user to for authorization
	 */ 
	String buildAuthenticateUrl(GrantType grantType, OAuth2Parameters parameters);
	
	/**
	 * Exchange the authorization code for an access grant.
	 * @param authorizationCode the authorization code returned by the provider upon user authorization
	 * @param redirectUri the authorization callback url; this value must match the redirectUri registered with the provider
	 * @param additionalParameters any additional parameters to be sent when exchanging the authorization code for an access grant. Should not be encoded. 
	 * @return the access grant.
	 */
	AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> additionalParameters);

	/**
	 * Refreshes a previous access grant.
	 * @param refreshToken the refresh token from the previous access grant.
	 * @param scope optional scope to narrow to when refreshing access; if null, the existing scope is preserved.
	 * @param additionalParameters any additional parameters to be sent when refreshing a previous access grant. Should not be encoded. 
	 * @return the access grant.
	 */
	AccessGrant refreshAccess(String refreshToken, String scope, MultiValueMap<String, String> additionalParameters);
	
}
