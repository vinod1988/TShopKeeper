package com.yh.shopkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.yh.shopkeeper.R;
import com.yh.shopkeeper.service.UpgradeService;
import com.yh.shopkeeper.utils.SharedPreferenceHelper;

public class LaunchActivity extends Activity {
	
	private final int ID_DIALOG_UPDATE = 9;
	private final int ID_GOTO_MAIN = 1;
	private final int ID_ALL_COMPLETED = 8;
		
	private UpgradeService mUpdateService;
	private SharedPreferences mPreferences;
	private Boolean mShowNewFunction;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
		
		ImageView img=(ImageView)this.findViewById(R.id.splash_img);
		mPreferences = SharedPreferenceHelper.getInstance(this);
		mShowNewFunction = mPreferences.getBoolean("show_new_function", true);
		
		img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//handler.sendEmptyMessage(ID_GOTO_MAIN);
			}
		});
	}
	
	@Override
	public void onStart() {
		super.onStart();
		startApp();
	}
	
	private void startApp() {
		Thread backThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				handler.sendMessage(handler.obtainMessage());
				//nextStep(-1);
				nextStep(ID_ALL_COMPLETED);
				
			}
		});
		backThread.start();
	}
	
	private void nextStep(int currentStep) {
		switch (currentStep) {
			case ID_DIALOG_UPDATE:
				handler.sendEmptyMessage(ID_ALL_COMPLETED);
				break;
			case ID_ALL_COMPLETED:
				handler.sendEmptyMessage(ID_GOTO_MAIN);
				break;
			default:
				handler.sendEmptyMessage(ID_DIALOG_UPDATE);
				break;
	   }
	}
	
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case -1:
					android.os.Process.killProcess(android.os.Process.myPid());
					break;
				case ID_DIALOG_UPDATE:
					mUpdateService = new UpgradeService(LaunchActivity.this,
							handler);
					mUpdateService.updateInfo();
					break;
				case ID_GOTO_MAIN:
					Intent intent = new Intent();
					if(mShowNewFunction){
						intent.setClass(LaunchActivity.this, NewFunctionIntroActivity.class);
					}else{
						intent.setClass(LaunchActivity.this, MainActivity.class);
					}
					startActivity(intent);
					finish();
					break;
			}
		}
	};
}
