package com.yh.shopkeeper.activity.utils;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.yh.shopkeeper.R;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkHelper {
	
	public static HttpResponse HttpPostsWithResponse(String url, List<NameValuePair> params, List<NameValuePair> headers)
			throws Exception {
		HttpPost request = new HttpPost(url);
		HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
		request.setEntity(entity);
		if (!Utils.isEmpty(headers)) {
			for (NameValuePair header : headers) {
				request.setHeader(header.getName(), header.getValue());
			}
		}
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = httpClient.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new RuntimeException("read url failed");
		} else {
			return httpResponse;
		}
	}

	public static String HttpPosts(String url, List<NameValuePair> params) throws Exception {
		return HttpPosts(url, params, null);
	}

	public static String HttpPosts(String url, List<NameValuePair> params, List<NameValuePair> headers)
			throws Exception {
		String result = "";
		HttpPost request = new HttpPost(url);
		HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
		request.setEntity(entity);
		if (!Utils.isEmpty(headers)) {
			for (NameValuePair header : headers) {
				request.setHeader(header.getName(), header.getValue());
			}
		}
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = httpClient.execute(request);
		if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new RuntimeException("read url failed");
		} else {
			result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		}
		return result;
	}

	public static String HttpGets(String url) throws Exception {
		String result = "";
		HttpGet httpGet = new HttpGet(url);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = httpClient.execute(httpGet);
		if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new RuntimeException("read url failed");
		} else {
			result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		}
		return result;
	}

	public static boolean isNetworkAvailable(Context context, boolean showToast) {
		boolean available = isNetworkAvailable(getNetworkInfo(context));
		if (!available && showToast) {
			CommonHelper.display(context, R.string.fmt_iap_err);
		}
		return available;
	}

	public static boolean isNetworkAvailable(Context context) {
		return isNetworkAvailable(context, true);
	}

	public static NetworkInfo getNetworkInfo(Context context) {
		ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cManager.getActiveNetworkInfo();
	}

	public static boolean isNetworkAvailable(NetworkInfo networkInfo) {
		return (networkInfo != null && networkInfo.isConnected());
	}

}
