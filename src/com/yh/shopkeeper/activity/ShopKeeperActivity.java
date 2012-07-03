package com.yh.shopkeeper.activity;

import com.yh.android.framework.social.connect.ConnectionRepository;
import com.yh.android.taobao.fkw.api.OpenTaoBao;

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

public class ShopKeeperActivity extends AbstractAsyncActivity {
			
	private ConnectionRepository connectionRepository;
	private OpenTaoBao openTaoBao;
	
	private Button beginOuathBtn;
	private Button btnStart;
	private TextView txtView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sigin_activity_layout);
		this.connectionRepository = getApplicationContext().getConnectionRepository();
		this.btnStart=(Button)this.findViewById(R.id.button2);
		this.beginOuathBtn=  (Button) findViewById(R.id.button1);
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
	
}