package com.yh.shopkeeper.activity;

import com.yh.android.framework.social.connect.Connection;
import com.yh.android.framework.social.connect.ConnectionRepository;
import com.yh.android.framework.social.connect.DuplicateConnectionException;
import com.yh.android.framework.social.oauth2.AccessGrant;
import com.yh.android.framework.social.oauth2.GrantType;
import com.yh.android.framework.social.oauth2.OAuth2Parameters;
import com.yh.android.framework.util.LinkedMultiValueMap;
import com.yh.android.framework.util.MultiValueMap;
import com.yh.android.taobao.fkw.api.OpenTaoBao;
import com.yh.android.taobao.fkw.connect.OpenTaoBaoConnectionFactory;

import com.yh.shopkeeper.AbstractAsyncActivity;
import com.yh.shopkeeper.AbstractWebViewActivity;
import com.yh.shopkeeper.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * @author Timo Jiang
 */
public class OAuthActivity extends AbstractAsyncActivity {

	private static final String TAG = OAuthActivity.class.getSimpleName();
	private ConnectionRepository connectionRepository;
	private OpenTaoBaoConnectionFactory connectionFactory;
	private final String callbackURL="http://www.oauth.net/2";
	
	private Activity activity;

	private WebView webView;
	
	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);
		this.connectionRepository = getApplicationContext().getConnectionRepository();
		this.connectionFactory = getApplicationContext().getOpenTaoBaoConnectionFactory();

		webView=(WebView)this.findViewById(R.id.webview_login_taobao);
		// OpenTaoBao uses javascript to redirect to the success page
		webView.getSettings().setJavaScriptEnabled(true);
		// Using a custom web view client to capture the access token
		webView.setWebViewClient(new TaoBaoOAuthWebViewClient());
		this.activity = this;
	}

	@Override
	public void onStart() {
		super.onStart();

		// display the OpenTaoBao authorization page
		webView.loadUrl(getAuthorizeUrl());
	}
	
	// ***************************************
	// Private methods
	// ***************************************
	private String getAuthorizeUrl() {
		/* 
		 * Generate the OpenTaoBao authorization url to be used in the browser or web view the display=touch parameter 
		 * requests the mobile formatted version of the OpenTaoBao authorization page
		 */
		OAuth2Parameters params = new OAuth2Parameters();
		params.setRedirectUri(callbackURL);
		params.setScope("item,promotion,item,usergrade");
		params.setState("1212");
		params.add("view", "wap");
		
		return this.connectionFactory.getOAuthOperations().buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, params);
	}

	private void displayOpenTaoBaoOptions() {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	// ***************************************
	// Private classes
	// ***************************************
	private class TaoBaoOAuthWebViewClient extends WebViewClient {

		/*
		 * The WebViewClient has another method called shouldOverridUrlLoading which does not capture the javascript 
		 * redirect to the success page. So we're using onPageStarted to capture the url.
		 */
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// parse the captured url
			Uri uri = Uri.parse(url);

			Log.d(TAG, url);

			String uriFragment = uri.getFragment();
			
			if(uriFragment==null && url.startsWith("http://www.oauth.net/2?code=")){
				
				// split to get the two different parameters
				
				String[] codeParam = url.replace(callbackURL, "").split("&");
				String[] authorizeParam = codeParam[0].split("=");
				String authorizationCode=authorizeParam[1];
				
				MultiValueMap<String, String> additionalParameters = new LinkedMultiValueMap<String, String>();
				additionalParameters.set("state", "1212");
				additionalParameters.set("scope", "item");
				additionalParameters.set("view", "wap");
									
				AccessGrant accessGrant=connectionFactory.getOAuthOperations().exchangeForAccess(authorizationCode, callbackURL, additionalParameters);
				
				Connection<OpenTaoBao> connection = connectionFactory.createConnection(accessGrant);
				try {
					connectionRepository.addConnection(connection);
				} catch (DuplicateConnectionException e) {
					// connection already exists in repository!
				}
		
				displayOpenTaoBaoOptions();
			}
			/*
			 * if there was an error with the oauth process, return the error description
			 * 
			 * The error query string will look like this:
			 * 
			 * ?error_reason=user_denied&error=access_denied&error_description=The +user+denied+your+request
			 */
			if (uri.getQueryParameter("error") != null) {
				CharSequence errorReason = uri.getQueryParameter("error_description").replace("+", " ");
				Log.v(TAG, errorReason.toString());
				Toast.makeText(getApplicationContext(), errorReason, Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void displayDialogError(CharSequence message) {
		
	}
}
