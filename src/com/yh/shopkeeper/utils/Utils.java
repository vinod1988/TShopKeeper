package com.yh.shopkeeper.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

public class Utils {
	
	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}
	
	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	public static boolean isEmpty(String str) {
		return (str == null || str.trim().equals(""));
	}

	public static boolean isEmpty(CharSequence chars) {
		return (chars == null || chars.toString().trim().equals(""));
	}
}
