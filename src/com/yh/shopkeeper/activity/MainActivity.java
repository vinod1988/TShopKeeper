/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yh.shopkeeper.activity;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.nineoldandroids.animation.ObjectAnimator;
import com.yh.android.framework.social.connect.ConnectionRepository;
import com.yh.android.taobao.fkw.api.OpenTaoBao;
import com.yh.shopkeeper.AbstractAsyncActivity;
import com.yh.shopkeeper.R;
import com.yh.shopkeeper.activity.fragment.FragmentDashBoard;

import static com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import static com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;

public class MainActivity extends AbstractAsyncActivity implements ActionBar.TabListener {

	private ConnectionRepository connectionRepository;
	private OpenTaoBao openTaoBao;
	
    private final Handler handler = new Handler();
    private boolean useLogo = false;
    private boolean showHomeUp = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.connectionRepository = getApplicationContext().getConnectionRepository();
        if(!isConnected()){
        	displayOpenTaoBaoAuthorization();
        }
        
        setContentView(R.layout.main_layout);
        final ActionBar ab = getSupportActionBar();
        // set defaults for logo & home up
        ab.setDisplayHomeAsUpEnabled(showHomeUp);
        ab.setDisplayUseLogoEnabled(useLogo);
    }
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
    }
       
    // ***************************************
	// Private methods
	// ***************************************
	private boolean isConnected() {
		return connectionRepository.findPrimaryConnection(OpenTaoBao.class) != null;
	}
	
	private void displayOpenTaoBaoAuthorization() {
		Intent intent = new Intent();
		intent.setClass(this, OAuthActivity.class);
		startActivity(intent);
		this.finish();
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.main_menu, menu);

        // set up a listener for the refresh item
        final MenuItem refresh = (MenuItem) menu.findItem(R.id.menu_refresh);
        refresh.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            // on selecting show progress spinner for 1s
            public boolean onMenuItemClick(MenuItem item) {
                // item.setActionView(R.layout.progress_action);
                handler.postDelayed(new Runnable() {
                    public void run() {
                        refresh.setActionView(null);
                    }
                }, 1000);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            // TODO handle clicking the app icon/logo
            return false;
        case R.id.menu_refresh:
            // switch to a progress animation
            item.setActionView(R.layout.indeterminate_progress_action);
            return true;
        case R.id.menu_logo:
            useLogo = !useLogo;
            item.setChecked(useLogo);
            getSupportActionBar().setDisplayUseLogoEnabled(useLogo);
            return true;
        case R.id.menu_up:
            showHomeUp = !showHomeUp;
            item.setChecked(showHomeUp);
            getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeUp);
            return true;
        case R.id.menu_nav_tabs:
            item.setChecked(true);
            return true;
        case R.id.menu_nav_label:
            item.setChecked(true);
            showStandardNav();
            return true;
        case R.id.menu_nav_drop_down:
            item.setChecked(true);
            showDropDownNav();
            return true;
        case R.id.menu_bak_none:
            item.setChecked(true);
            getSupportActionBar().setBackgroundDrawable(null);
            return true;
        case R.id.menu_bak_gradient:
            item.setChecked(true);
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ad_action_bar_gradient_bak));
            return true;
        case R.id.exit:
        	exit();
			return true;
        case R.id.about:
        	Intent intent = new Intent(this, AboutActivity.class);
			startActivity(intent);
			return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    public void exitActivity() {
		this.finish();
		android.os.Process.killProcess(android.os.Process.myPid());
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

    private void showStandardNav() {
        ActionBar ab = getSupportActionBar();
        if (ab.getNavigationMode() != ActionBar.NAVIGATION_MODE_STANDARD) {
            ab.setDisplayShowTitleEnabled(true);
            ab.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        }
    }

    private void showDropDownNav() {
        ActionBar ab = getSupportActionBar();
        if (ab.getNavigationMode() != ActionBar.NAVIGATION_MODE_LIST) {
            ab.setDisplayShowTitleEnabled(false);
            ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        }
    }

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}