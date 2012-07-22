package com.yh.shopkeeper.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yh.shopkeeper.R;
import com.yh.shopkeeper.utils.DateUtils;
import com.yh.shopkeeper.utils.NetworkHelper;
import com.yh.shopkeeper.utils.SharedPreferenceHelper;
import com.yh.shopkeeper.utils.Utils;

public class UpgradeService {
	private final String DEVICE_TYPE = "ANDROID";
	private final String APP_NAME = "OOCLite";
	private final String INSTALL_SOURCE = "GOOGLE_PLAY";
	
	private final int ID_GOTO_MAIN = 1;
	private final int UPGRADE = 1;
	private final int CANCEL = 0;
	private final String HAS_NEW_VERSION = "1";

	// information of latest version
	private Version newVersion;

	// current version
	private int currentVersionCode;
	private String currentVersionName;

	private Context mContext;
	private Handler mHandler;

	public UpgradeService(Context context, Handler handler) {
		this.mContext = context;
		this.mHandler = handler;

		// get current version code
		PackageInfo info = null;
		try {
			info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		this.currentVersionCode = info.versionCode;
		this.currentVersionName = info.versionName;
	}

	// interface to call
	public void updateInfo() {
		// check version
		checkVersion();

		// if has new version, show the alert every 15 day or force upgrade
		if (newVersion != null && newVersion.getReturnCode().equals(HAS_NEW_VERSION)) {
			showUpdateDialog();
		} else {
			completed(CANCEL);
		}
	}

	private void checkVersion() {
		if (NetworkHelper.isNetworkAvailable(mContext, false)) {
			newVersion=new Version();
			newVersion.setVersionUpdateNews("有新版本!");
			newVersion.setReturnCode("1"); 
		}
	}

	private Object readMetaData(String keyName) {
		try {
			ApplicationInfo appi = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(),
					PackageManager.GET_META_DATA);
			Bundle bundle = appi.metaData;
			Object value = bundle.get(keyName);
			return value;
		} catch (NameNotFoundException e) {
			return null;
		}
	}

	private void showUpdateDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(R.string.settings_update);
		builder.setIcon(android.R.drawable.ic_dialog_info);

		builder.setPositiveButton(R.string.app_download, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newVersion.getVersionUpdateUrl()));
				mContext.startActivity(webIntent);
				completed(UPGRADE);
			}
		});

		// if the indicator is force, user must update the version
		builder.setMessage(R.string.settings_new_version_title);
		builder.setNegativeButton(R.string.app_cancel, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				completed(CANCEL);
			}
		});
		
		builder.show();
	}

	private void completed(int status) {
		switch (status) {
		case UPGRADE:
			Editor editor = SharedPreferenceHelper.getInstance(mContext).edit();
			editor.putBoolean("show_new_function", true);
			editor.commit();
			((Activity) mContext).finish();
			break;
		case CANCEL:
			this.mHandler.sendEmptyMessage(ID_GOTO_MAIN);
			break;
		}
	}

}
