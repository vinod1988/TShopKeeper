package com.yh.shopkeeper.activity;

import org.springframework.social.oauth2.AccessGrant;
import com.hy.shopkeeper.common.AbstractWebViewActivity;
import com.hy.shopkeeper.fkw.TaoBao;
import com.hy.shopkeeper.fkw.TaoBaoException;
import com.hy.shopkeeper.org.json.JSONException;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * @author Timo Jiang
 */
public class OAuthActivity extends AbstractWebViewActivity {

	private static final String TAG = OAuthActivity.class.getSimpleName();

	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// OpenTaoBao uses javascript to redirect to the success page
		getWebView().getSettings().setJavaScriptEnabled(true);

		// Using a custom web view client to capture the access token
		getWebView().setWebViewClient(new TaoBaoOAuthWebViewClient());

	}

	@Override
	public void onStart() {
		super.onStart();

		// display the OpenTaoBao authorization page
		getWebView().loadUrl(getAuthorizeUrl());
	}

	// ***************************************
	// Private methods
	// ***************************************
	private String getAuthorizeUrl() {
		TaoBao taobao = OAuthConstant.getInstance().getTaoBao();
		String authroizeUrl="";
		try {
			authroizeUrl=taobao.requestAuthorize();
		} catch (TaoBaoException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authroizeUrl;
	}

	private void displayOpenTaoBaoOptions() {
		Intent intent = new Intent();
		intent.setClass(this, ShopKeeperActivity.class);
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

			/*
			 * The access token is returned in the URI fragment of the URL. See the Desktop Apps section all the way 
			 * at the bottom of this link:
			 * 
			 * http://developers.facebook.com/docs/authentication/
			 * 
			 * The fragment will be formatted like this:
			 * 
			 * #access_token=A&expires_in=0
			 */
			String uriFragment = uri.getFragment();

			// confirm we have the fragment, and it has an access_token parameter
			if (uriFragment != null && uriFragment.startsWith("access_token=")) {

				/*
				 * The fragment also contains an "expires_in" parameter. In this
				 * example we requested the offline_access permission, which
				 * basically means the access will not expire, so we're ignoring
				 * it here
				 */
				try {
					// split to get the two different parameters
					String[] params = uriFragment.split("&");

					// split to get the access token parameter and value
					String[] accessTokenParam = params[0].split("=");

					// get the access token value
					String accessToken = accessTokenParam[1];
					
				   // create the connection and persist it to the repository
					AccessGrant accessGrant = new AccessGrant(accessToken);
					OAuthConstant.getInstance().setAccessToken(accessGrant);

				} catch (Exception e) {
					// don't do anything if the parameters are not what is expected
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
				displayOpenTaoBaoOptions();
			}
		}
	}

	@Override
	public void displayDialogError(CharSequence message) {
		// TODO Auto-generated method stub
		
	}
}
