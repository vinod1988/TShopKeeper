package com.yh.shopkeeper.utils;

import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;

public class DialogCloseListener implements OnKeyListener {

	@Override
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)
				&& (event.getAction() == KeyEvent.ACTION_DOWN)) {
			android.os.Process.killProcess(android.os.Process.myPid());
			return true;
		}
		return false;
	}

}
