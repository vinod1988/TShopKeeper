package com.yh.shopkeeper.activity;

import com.yh.shopkeeper.R;

import org.taptwo.android.widget.CircleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;
import org.taptwo.android.widget.ViewFlow.ViewSwitchListener;

import com.yh.shopkeeper.adapter.ViewFlowImageAdapter;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class NewFunctionIntroActivity extends Activity {

	private ViewFlow viewFlow;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_function_intro);

		viewFlow = (ViewFlow) findViewById(R.id.viewflow);
		ViewFlowImageAdapter adapter = new ViewFlowImageAdapter(this);
        viewFlow.setAdapter(adapter,0);
        CircleFlowIndicator indicator = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
		viewFlow.setFlowIndicator(indicator);

		viewFlow.setOnViewSwitchListener(new ViewSwitchListener(){

			@Override
			public void onSwitched(View view, int position) {
				if(position>=2){
					Intent intent = new Intent();
					intent.setClass(view.getContext(), MainActivity.class);
					startActivity(intent);
					finish();
				}
			}
		});
	}
	
	/* If your min SDK version is < 8 you need to trigger the onConfigurationChanged in ViewFlow manually, like this */	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		viewFlow.onConfigurationChanged(newConfig);
	}

}
