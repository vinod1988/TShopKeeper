package com.yh.shopkeeper;

import java.util.ArrayList;
import java.util.List;

import com.yh.shopkeeper.R;
import com.yh.shopkeeper.activity.AboutActivity;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.style.SuperscriptSpan;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

public class BaseActivity extends Activity {
	private static List<Activity> activityList = new ArrayList<Activity>();
	private static Activity currentActivity;

	public BaseActivity() {
		super();
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addActivity(this);
		setCurrentActivity(this);
	}

	@Override
	protected void onResume() {
		setCurrentActivity(this);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		removeActivity(this);
		super.onDestroy();
	}

	private static boolean addActivity(Activity activity) {
		return activityList.add(activity);
	}

	private static boolean removeActivity(Activity activity) {
		return activityList.remove(activity);
	}

	public static List<Activity> getActivities() {
		return activityList;
	}

	public static void exitActivity() {
		for (Activity activity : activityList) {
			if (activity != null) {
				activity.finish();
			}
		}
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	private static void setCurrentActivity(Activity activity) {
		BaseActivity.currentActivity = activity;
	}

	public static Activity getCurrentActivity() {
		return currentActivity;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = this.getMenuInflater();
		menuInflater.inflate(R.menu.menu_common, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.exit) {
			Builder builder = new Builder(this).setTitle(R.string.app_exit_title)
					.setMessage(R.string.app_exit_confirm_info)
					.setPositiveButton(R.string.app_ok, new OnClickListener() {
						@Override
						public void onClick(DialogInterface paramDialogInterface, int paramInt) {
							exitActivity();
						}
					}).setNegativeButton(R.string.app_cancel, null);
			builder.create().show();
		} else if (item.getItemId() == R.id.about) {
			Intent intent = new Intent(this, AboutActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	public void exit() {
		Builder builder = new Builder(this).setTitle(R.string.app_exit_title)
				.setMessage(R.string.app_exit_confirm_info)
				.setPositiveButton(R.string.app_ok, new OnClickListener() {
					@Override
					public void onClick(DialogInterface paramDialogInterface, int paramInt) {
						exitActivity();
					}
				}).setNegativeButton(R.string.app_cancel, null);
		builder.create().show();
	}

}
