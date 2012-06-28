package com.yh.shopkeeper.utils;

import android.content.Context;
import android.os.Vibrator;

public class VibratorHelper {
	public static void Shake(Context ctx, int milseconds) {
		Vibrator vv = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);
		vv.vibrate(milseconds);
	}

	public static void Shake(Context ctx) {
		Shake(ctx, 20);
	}
}
