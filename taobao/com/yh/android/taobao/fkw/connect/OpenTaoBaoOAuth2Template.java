package com.yh.android.taobao.fkw.connect;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.yh.android.framework.org.json.JSONException;
import com.yh.android.framework.org.json.JSONObject;
import com.yh.android.framework.social.oauth2.AccessGrant;
import com.yh.android.framework.social.oauth2.GrantType;
import com.yh.android.framework.social.oauth2.OAuth2Operations;
import com.yh.android.framework.social.oauth2.OAuth2Parameters;
import com.yh.android.framework.util.LinkedMultiValueMap;
import com.yh.android.framework.util.MultiValueMap;
import com.yh.android.taobao.fkw.TaoBaoAccessToken;

import com.taobao.api.internal.util.WebUtils;


public class OpenTaoBaoOAuth2Template implements OAuth2Operations {

	private final String clientId;
	private final String clientSecret;
	private final String accessTokenUrl;
	private final String authorizeUrl;
	private String authenticateUrl;
	

	public OpenTaoBaoOAuth2Template(String clientId, String clientSecret) {
		//clientId, clientSecret, "https://oauth.taobao.com/authorize", 
		this(clientId, clientSecret, "https://oauth.taobao.com/authorize", null, "https://oauth.taobao.com/token");
	}
	
	public OpenTaoBaoOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String authenticateUrl, String accessTokenUrl) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		String clientInfo = "?client_id=" + formEncode(clientId);
		this.authorizeUrl = authorizeUrl + clientInfo;
		if (authenticateUrl != null) {
			this.authenticateUrl = authenticateUrl + clientInfo;
		} else {
			this.authenticateUrl = null;
		}
		this.accessTokenUrl = accessTokenUrl;
	}
	
	public String buildAuthenticateUrl(GrantType grantType, OAuth2Parameters parameters) {
		return authenticateUrl != null ? buildAuthUrl(authenticateUrl, grantType, parameters) : buildAuthorizeUrl(grantType, parameters);
	}
	
	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters){
		AccessGrant accessToken=null;				
		try{
			String responseJson=WebUtils.doPost(accessTokenUrl, parameters.toSingleValueMap(), 3000, 3000);
			JSONObject json=new JSONObject(responseJson);			
			TaoBaoAccessToken taobao=new TaoBaoAccessToken(json);
			accessToken=taobao.getAccessGrant();
		}catch(IOException ex){
			
		}catch(JSONException ex){
			
		}
		return accessToken;
	}

	public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> additionalParameters) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("client_id", clientId);
		params.set("client_secret", clientSecret);
		params.set("code", authorizationCode);
		params.set("redirect_uri", redirectUri);
		params.set("grant_type", "authorization_code");
		if (additionalParameters != null) {
			params.putAll(additionalParameters);
		}
		return postForAccessGrant(accessTokenUrl, params);
	}

	public AccessGrant refreshAccess(String refreshToken, String scope, MultiValueMap<String, String> additionalParameters) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("client_id", clientId);
		params.set("client_secret", clientSecret);
		params.set("refresh_token", refreshToken);
		if (scope != null) {
			params.set("scope", scope);
		}
		params.set("grant_type", "refresh_token");
		if (additionalParameters != null) {
			params.putAll(additionalParameters);
		}
		return postForAccessGrant(accessTokenUrl, params);
	}

	
	/**
	 * Creates an {@link AccessGrant} given the response from the access token exchange with the provider.
	 * May be overridden to create a custom AccessGrant that captures provider-specific information from the access token response. 
	 * @param accessToken the access token value received from the provider
	 * @param scope the scope of the access token
	 * @param refreshToken a refresh token value received from the provider
	 * @param expiresIn the time (in seconds) remaining before the access token expires.
	 * @param response all parameters from the response received in the access token exchange.
	 * @return an {@link AccessGrant}
	 */
	protected AccessGrant createAccessGrant(String accessToken, String scope, String refreshToken, Integer expiresIn, Map<String, Object> response) {
		return new AccessGrant(accessToken, scope, refreshToken, expiresIn);
	}
	
	// internal helpers

	private String buildAuthUrl(String baseAuthUrl, GrantType grantType, OAuth2Parameters parameters) {
		StringBuilder authUrl = new StringBuilder(baseAuthUrl);
		if (grantType == GrantType.AUTHORIZATION_CODE) {
			authUrl.append('&').append("response_type").append('=').append("code");
		} else if (grantType == GrantType.IMPLICIT_GRANT) {
			authUrl.append('&').append("response_type").append('=').append("token");
		}
		for (Iterator<Entry<String, List<String>>> additionalParams = parameters.entrySet().iterator(); additionalParams.hasNext();) {
			Entry<String, List<String>> param = additionalParams.next();
			String name = formEncode(param.getKey());
			for (Iterator<String> values = param.getValue().iterator(); values.hasNext();) {
				authUrl.append('&').append(name).append('=').append(formEncode(values.next()));
			}
		}
		return authUrl.toString();		
	}
	
	private String formEncode(String data) {
		try {
			return URLEncoder.encode(data, "UTF-8");
		}
		catch (UnsupportedEncodingException ex) {
			// should not happen, UTF-8 is always supported
			throw new IllegalStateException(ex);
		}
	}
	
	@SuppressWarnings("unused")
	private AccessGrant extractAccessGrant(Map<String, Object> result) {
		return createAccessGrant((String) result.get("access_token"), (String) result.get("scope"), (String) result.get("refresh_token"), getIntegerValue(result, "expires_in"), result);
	}

	// Retrieves object from map into an Integer, regardless of the object's actual type. Allows for flexibility in object type (eg, "3600" vs 3600).
	private Integer getIntegerValue(Map<String, Object> map, String key) {
		try {
			return Integer.valueOf(String.valueOf(map.get(key))); // normalize to String before creating integer value;			
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public String buildAuthorizeUrl(GrantType grantType, OAuth2Parameters parameters) {
		return buildAuthUrl(authorizeUrl, grantType, parameters);
	}

}

