package com.yh.shopkeeper.activity;

import com.weibo.net.AccessToken;
import com.weibo.net.DialogError;
import com.weibo.net.WeiboOAuth2;
import com.weibo.net.WeiboDialogListener;
import com.weibo.net.WeiboException;
import com.yh.android.framework.social.connect.ConnectionRepository;
import com.yh.android.taobao.fkw.api.OpenTaoBao;
import com.yh.android.weibo.Weibo;
import com.taobao.api.domain.Shop;
import com.yh.shopkeeper.AbstractAsyncActivity;
import com.yh.shopkeeper.R;
import com.yh.shopkeeper.activity.fragment.SampleList;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShopKeeperActivity extends AbstractAsyncActivity {
			
	private ConnectionRepository connectionRepository;
	private OpenTaoBao openTaoBao;
	
	private Button beginOuathBtn;
	private Button btnStart;
	private Button btnWeiBo;
	private TextView txtView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sigin_activity_layout);
		this.connectionRepository = getApplicationContext().getConnectionRepository();
		this.btnStart=(Button)this.findViewById(R.id.button2);
		this.beginOuathBtn=  (Button) findViewById(R.id.button1);
		this.btnWeiBo =  (Button) findViewById(R.id.btnWeiBo);
		
    	btnWeiBo.setOnClickListener(new Button.OnClickListener()
        {

            public void onClick( View v )
            {   
            	WeiboOAuth2 weibo = WeiboOAuth2.getInstance();
				// Oauth2.0
				weibo.setRedirectUrl(WeiboOAuth2.URL_OAUTH2_CALL_BACK);// 此处回调页内容应该替换为与appkey对应的应用回调页
				weibo.authorize(ShopKeeperActivity.this,new AuthDialogListener());
            }
        } );
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if (isConnected()) {
			this.beginOuathBtn.setVisibility(View.GONE);
			this.btnStart.setVisibility(View.VISIBLE);
			this.openTaoBao = connectionRepository.findPrimaryConnection(OpenTaoBao.class).getApi();
			new ShopTask(getApplicationContext()).execute();
			this.btnStart.setOnClickListener(new Button.OnClickListener(){
			     public void onClick( View v ){   
		        	Intent intent=new Intent(v.getContext(),SampleList.class);
		        	v.getContext().startActivity(intent);
		        }
		    });			
		} else {
			this.btnStart.setVisibility(View.GONE);
			this.beginOuathBtn.setVisibility(View.VISIBLE);
			showConnectOption();
		}
	}
		
	private void showConnectOption(){
		
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
		return connectionRepository.findPrimaryConnection(OpenTaoBao.class) != null;
	}
		
	private void displayOpenTaoBaoAuthorization() {
		Intent intent = new Intent();
		intent.setClass(this, OAuthActivity.class);
		startActivity(intent);
		finish();
	}
	
	private void displayList(Shop shop){
		if(shop!=null){
			TextView txtView=(TextView) findViewById(R.id.textView1);
			txtView.setText(shop.getDesc());
		}
	}
	
	private class ShopTask extends AsyncTask<Void, Integer, Shop> {

		private Context mContext;
		public ShopTask(Context context) {
			this.mContext = context;
		}

		@Override
		protected void onPreExecute() {
			showProgressDialog(mContext.getResources().getString(
					R.string.app_loading_data));
		}

		@Override
		protected Shop doInBackground(Void... arg0) {
			Shop shop=null;
			try {
				shop=openTaoBao.shopOperations().requestRemainShowCase();
			} catch (Exception ex) {
				ex.printStackTrace();
				shop = null;
			}
			return shop;
		}

		@Override
		protected void onPostExecute(Shop result) {
			dismissProgressDialog();
			displayList(result);
		}
	}
	
	class AuthDialogListener implements WeiboDialogListener {

		@Override
		public void onComplete(Bundle values) {
			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");
			AccessToken accessToken = new AccessToken(token, Weibo.CONSUMER_SECRET);
			accessToken.setExpiresIn(expires_in);
			WeiboOAuth2.getInstance().setAccessToken(accessToken);
			Intent intent = new Intent();
			intent.setClass(ShopKeeperActivity.this, TestActivity.class);
			startActivity(intent);
		}

		@Override
		public void onError(DialogError e) {
			Toast.makeText(getApplicationContext(),
					"Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}

		@Override
		public void onCancel() {
			Toast.makeText(getApplicationContext(), "Auth cancel",
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(getApplicationContext(),
					"Auth exception : " + e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}

	}
	
}