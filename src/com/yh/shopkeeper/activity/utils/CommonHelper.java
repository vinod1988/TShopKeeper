package com.yh.shopkeeper.activity.utils;

import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

public class CommonHelper {
	// Display a toast
	public static void display(Context context, String string) {
		Toast tst = Toast.makeText(context, string, Toast.LENGTH_SHORT);
		// tst.setGravity(Gravity.CENTER, tst.getXOffset() / 2, tst.getYOffset()
		// / 2);
		tst.setGravity(Gravity.BOTTOM, 0, tst.getYOffset() / 2);
		tst.show();
	}

	public static void display(Context context, int resId) {
		Toast tst = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
		// tst.setGravity(Gravity.CENTER, tst.getXOffset() / 2, tst.getYOffset()
		// / 2);
		tst.setGravity(Gravity.BOTTOM, 0, tst.getYOffset() / 2);
		tst.show();
	}

	// send email

	public static Intent createShareIntent(String share) {
		final Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, share);
		intent.putExtra(Intent.EXTRA_SUBJECT, "ShopKeeper Mobile Solution");
		return Intent.createChooser(intent, "Share");
	}

	// You can call it to send email
	public static Intent sendEmail() {
		Uri uri = Uri.parse("mailto:");
		Intent it = new Intent(Intent.ACTION_SENDTO, uri);
		it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		return it;
	}

	private static String getEncryptKey(Context context) {
		SharedPreferences preference = SharedPreferenceHelper.getInstance(context);
		String key = preference.getString("ENCRYPT_KEY", null);
		if (TextUtils.isEmpty(key)) {
			key = UUID.randomUUID().toString();
			preference.edit().putString("ENCRYPT_KEY", key).commit();
		}
		return key;
	}

	public static String encrypt(Context context, String content) throws Exception {
		return AESEncryptor.encrypt(getEncryptKey(context), content);
	}

	public static String decrypt(Context context, String content) throws Exception {
		return AESEncryptor.decrypt(getEncryptKey(context), content);
	}
	
	
}
