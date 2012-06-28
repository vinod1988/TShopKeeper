package com.yh.shopkeeper.utils;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceHelper {
	public static SharedPreferences getInstance(Context context) {
		return context.getSharedPreferences("hy", Context.MODE_PRIVATE);
	}

	public static void saveData(Context context, Map<String, Object> list) {
		if (list != null && list.size() > 0) {
			Editor editor = getInstance(context).edit();
			for (String key : list.keySet()) {
				Object value = list.get(key);
				if (value instanceof Integer) {
					editor.putInt(key, (Integer) value);
				}
				if (value instanceof Long) {
					editor.putLong(key, (Long) value);
				}
				if (value instanceof Float) {
					editor.putFloat(key, (Float) value);
				}
				if (value instanceof String) {
					editor.putString(key, (String) value);
				}
				if (value instanceof Boolean) {
					editor.putBoolean(key, (Boolean) value);
				}
			}
			editor.commit();
		}
	}
}
