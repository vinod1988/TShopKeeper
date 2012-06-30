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
package com.yh.android.framework.social.oauth1;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import com.yh.android.framework.http.MediaType;
import com.yh.android.framework.http.client.ClientHttpRequestFactory;
import com.yh.android.framework.http.converter.ByteArrayHttpMessageConverter;
import com.yh.android.framework.http.converter.FormHttpMessageConverter;
import com.yh.android.framework.http.converter.HttpMessageConverter;
import com.yh.android.framework.http.converter.StringHttpMessageConverter;
import com.yh.android.framework.http.converter.json.MappingJacksonHttpMessageConverter;
import com.yh.android.framework.social.ApiBinding;
import com.yh.android.framework.social.support.ClientHttpRequestFactorySelector;
import com.yh.android.framework.web.client.RestTemplate;

/**
 * Base class for OAuth 1-based provider API bindings.
 * @author Craig Walls
 */
public abstract class AbstractOAuth1ApiBinding implements ApiBinding {

	private final OAuth1Credentials credentials;

	private final RestTemplate restTemplate;

	/**
	 * Constructs the API template without user authorization. This is useful for accessing operations on a provider's API that do not require user authorization.
	 */
	protected AbstractOAuth1ApiBinding() {
		credentials = null;
		restTemplate = new RestTemplate(ClientHttpRequestFactorySelector.getRequestFactory());
		restTemplate.setMessageConverters(getMessageConverters());
		configureRestTemplate(restTemplate);
	}

	/**
	 * Constructs the API template with OAuth credentials necessary to perform operations on behalf of a user.
	 * @param consumerKey the application's consumer key
	 * @param consumerSecret the application's consumer secret
	 * @param accessToken the access token
	 * @param accessTokenSecret the access token secret
	 */
	protected AbstractOAuth1ApiBinding(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
		credentials = new OAuth1Credentials(consumerKey, consumerSecret, accessToken, accessTokenSecret);
		restTemplate = ProtectedResourceClientFactory.create(credentials);
		restTemplate.setMessageConverters(getMessageConverters());
		configureRestTemplate(restTemplate);
	}
	
	/**
	 * Set the ClientHttpRequestFactory. This is useful when custom configuration of the request factory is required, such as configuring custom SSL details.
	 * @param requestFactory the request factory
	 */
	public void setRequestFactory(ClientHttpRequestFactory requestFactory) {
		if (isAuthorized()) {
			restTemplate.setRequestFactory(ProtectedResourceClientFactory.addOAuthSigning(requestFactory, credentials));
		} else {
			restTemplate.setRequestFactory(requestFactory);
		}
	}

	// implementing ApiBinding
	
	public boolean isAuthorized() {
		return credentials != null;
	}

	// public implementation operations
	
	/**
	 * Obtains a reference to the REST client backing this API binding and used to perform API calls.
	 * Callers may use the RestTemplate to invoke other API operations not yet modeled by the binding interface.
	 * Callers may also modify the configuration of the RestTemplate to support unit testing the API binding with a mock server in a test environment.
	 * During construction, subclasses may apply customizations to the RestTemplate needed to invoke a specific API.
	 * @see RestTemplate#setMessageConverters(java.util.List)
	 * @see RestTemplate#setErrorHandler(com.yh.android.framework.web.client.ResponseErrorHandler)
	 */
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	// subclassing hooks
	
	/**
	 * Subclassing hook to enable customization of the RestTemplate used to consume provider API resources.
	 * An example use case might be to configure a custom error handler.
	 * Note that this method is called after the RestTemplate has been configured with the message converters returned from getMessageConverters().
	 * @param restTemplate the RestTemplate to configure.
	 */
	protected void configureRestTemplate(RestTemplate restTemplate) {		
	}
	
	/**
	 * Returns a list of {@link HttpMessageConverter}s to be used by the internal {@link RestTemplate}.
	 * By default, this includes a {@link StringHttpMessageConverter}, a {@link MappingJacksonHttpMessageConverter}, a {@link ByteArrayHttpMessageConverter}, and a {@link FormHttpMessageConverter}.
	 * The {@link FormHttpMessageConverter} is set to use "UTF-8" character encoding.
	 * Override this method to add additional message converters or to replace the default list of message converters.
	 */
	protected List<HttpMessageConverter<?>> getMessageConverters() {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new StringHttpMessageConverter());
		messageConverters.add(getFormMessageConverter());
		messageConverters.add(getJsonMessageConverter());
		messageConverters.add(getByteArrayMessageConverter());
		return messageConverters;
	}
	
	/**
	 * Returns an {@link FormHttpMessageConverter} to be used by the internal {@link RestTemplate}.
	 * By default, the message converter is set to use "UTF-8" character encoding.
	 * Override to customize the message converter (for example, to set supported media types or message converters for the parts of a multipart message). 
	 * To remove/replace this or any of the other message converters that are registered by default, override the getMessageConverters() method instead.
	 */
	protected FormHttpMessageConverter getFormMessageConverter() {
		FormHttpMessageConverter converter = new FormHttpMessageConverter();
		converter.setCharset(Charset.forName("UTF-8"));
		return converter;
	}
	
	/**
	 * Returns a {@link MappingJacksonHttpMessageConverter} to be used by the internal {@link RestTemplate}.
	 * Override to customize the message converter (for example, to set a custom object mapper or supported media types).
	 * To remove/replace this or any of the other message converters that are registered by default, override the getMessageConverters() method instead.
	 */
	protected MappingJacksonHttpMessageConverter getJsonMessageConverter() {
		return new MappingJacksonHttpMessageConverter(); 
	}
	
	/**
	 * Returns a {@link ByteArrayHttpMessageConverter} to be used by the internal {@link RestTemplate} when consuming image or other binary resources.
	 * By default, the message converter supports "image/jpeg", "image/gif", and "image/png" media types.
	 * Override to customize the message converter (for example, to set supported media types).
	 * To remove/replace this or any of the other message converters that are registered by default, override the getMessageConverters() method instead.	 
	 */
	protected ByteArrayHttpMessageConverter getByteArrayMessageConverter() {
		ByteArrayHttpMessageConverter converter = new ByteArrayHttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.IMAGE_JPEG, MediaType.IMAGE_GIF, MediaType.IMAGE_PNG));
		return converter;
	}
	
}