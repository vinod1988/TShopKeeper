package com.yh.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yh.android.framework.social.connect.ConnectionRepository;
import com.yh.android.taobao.fkw.api.OpenTaoBao;
import com.yh.android.weibo.fkw.api.OpenWeiBo;
import com.yh.shopkeeper.AbstractAsyncActivity;
import com.yh.shopkeeper.R;

public class LaunchActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
		ImageView img=(ImageView)this.findViewById(R.id.splash_img);
		img.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(v.getContext(), NewFunctionIntroActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}	
}
