package com.yh.shopkeeper.activity;

import com.hy.shopkeeper.fkw.Constants;
import com.yh.shopkeeper.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShopKeeperActivity extends Activity {
	Button beginOuathBtn;
	TextView txtView;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sigin_activity_layout);
		System.setProperty("weibo4j.oauth.consumerKey", Constants.CONSUMER_KEY);
		System.setProperty("weibo4j.oauth.consumerSecret", Constants.CONSUMER_SECRET);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if (isConnected()) {
			showMainPage();
		} else {
			showConnectOption();
		}
	}
	
	private void showMainPage(){
		TextView txtView=(TextView) findViewById(R.id.textView1);
		txtView.setText(OAuthConstant.getInstance().getAccessToken().getAccessToken());
	}
	
	private void showConnectOption(){
		beginOuathBtn=  (Button) findViewById(R.id.button1);
		beginOuathBtn.setOnClickListener(new Button.OnClickListener(){
		     public void onClick( View v ){   
	        	displayOpenTaoBaoAuthorization();
	        }
	    });
	}
	
	// ***************************************
	// Private methods
	// ***************************************
	private boolean isConnected() {
		return OAuthConstant.getInstance().getAccessToken()!=null?true:false;
	}

	private void displayOpenTaoBaoAuthorization() {
		Intent intent = new Intent();
		intent.setClass(this, OAuthActivity.class);
		startActivity(intent);
		finish();
	}
}