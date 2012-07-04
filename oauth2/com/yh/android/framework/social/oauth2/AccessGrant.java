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

import java.io.Serializable;

import javax.crypto.spec.SecretKeySpec;

/**
 * OAuth2 access token.
 * @author Keith Donald
 */
@SuppressWarnings("serial")
public class AccessGrant implements Serializable {

	private final String accessToken;

	private final String scope;

	private final String refreshToken;
	
	private final Long expireTime;
	
	 private String mOauth_verifier = "";
	 private String mOauth_Token_Secret = "";
	 private String[] responseStr = null;
	 private SecretKeySpec mSecretKeySpec;

	public AccessGrant(String accessToken) {
		this(accessToken, null, null, null);
	}

	public AccessGrant(String accessToken, String scope, String refreshToken, Integer expiresIn) {
		this.accessToken = accessToken;
		this.scope = scope;
		this.refreshToken = refreshToken;
		this.expireTime = expiresIn != null ? System.currentTimeMillis() + expiresIn * 1000l : null;
	}
	
	 public AccessGrant(String accessToken, String scope, String refreshToken, Integer expiresIn,String secret) {
		 this.accessToken = accessToken;
			this.scope = scope;
			this.refreshToken = refreshToken;
			this.expireTime = expiresIn != null ? System.currentTimeMillis() + expiresIn * 1000l : null;
			this.mOauth_Token_Secret = secret;
	}

	

	/**
	 * The access token value.
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * The scope of the access grant.
	 * May be null if the provider doesn't return the granted scope in the response.
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * The refresh token that can be used to renew the access token.
	 * May be null if the provider does not support refresh tokens.
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * The time (in milliseconds since Jan 1, 1970 UTC) when this access grant will expire.
	 * May be null if the token is non-expiring.
	 */
	public Long getExpireTime() {
		return expireTime;
	}
	
	
    public void setVerifier(String verifier) {
        mOauth_verifier = verifier;
    }

    public String getVerifier() {
        return mOauth_verifier;
    }

    public String getSecret() {
        return mOauth_Token_Secret;
    }
	
	public String getParameter(String parameter) {
        String value = null;
        for (String str : responseStr) {
            if (str.startsWith(parameter + '=')) {
                value = str.split("=")[1].trim();
                break;
            }
        }
        return value;
    }

	public void setSecretKeySpec(SecretKeySpec secretKeySpec) {
        this.mSecretKeySpec = secretKeySpec;
    }

	public SecretKeySpec getSecretKeySpec() {
        return mSecretKeySpec;
    }
}
