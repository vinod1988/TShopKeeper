package com.yh.shopkeeper.activity.utils;

import java.lang.ref.SoftReference;
import java.util.HashMap;

public class CacheHolder {
	private static final HashMap<String, SoftReference<?>> CACHE = new HashMap<String, SoftReference<?>>();

	public static void addCache(String key, Object value) {
		CACHE.put(key, new SoftReference<Object>(value));
	}

	public static Object getCache(String key) {
		return CACHE.containsKey(key) ? CACHE.get(key).get() : null;
	}

	public static Object removeCache(String key) {
		return CACHE.containsKey(key) ? CACHE.remove(key).get() : null;
	}

	public static void clear() {
		CACHE.clear();
	}
}
