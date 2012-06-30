package com.yh.shopkeeper;

import com.actionbarsherlock.app.SherlockActivity;
import com.yh.shopkeeper.R;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

public abstract class AbstractAsyncActivity extends SherlockActivity implements AsyncActivity {
	
    protected static final String TAG = AbstractAsyncActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    private boolean destroyed = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyed = true;
    }

    // ***************************************
	// Activity methods
	// ***************************************
	@Override
	public MainApplication getApplicationContext() {
		return (MainApplication) super.getApplicationContext();
	}

    public void showLoadingProgressDialog() {
    	showProgressDialog(this.getString(R.string.app_downloading));
    }

    public void showProgressDialog(CharSequence message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
        }
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && !destroyed) {
            progressDialog.dismiss();
        }
    }
    
    public void displayDialogError(CharSequence message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message);
		builder.setCancelable(false);
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
		     	
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
}
