package com.steelrat.nfcunlocker.settingsactivity;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;
import com.steelrat.nfcunlocker.AppDeviceAdminReceiver;
import com.steelrat.nfcunlocker.R;
import com.steelrat.nfcunlocker.unlockmethods.UnlockMethod;
import com.steelrat.nfcunlocker.unlockmethods.UnlockMethodFactory;

public abstract class SettingsActivityBase extends SherlockPreferenceActivity implements
		OnSharedPreferenceChangeListener {
	ComponentName mAppDeviceAdmin;
	DevicePolicyManager mDPM;
	UnlockMethod mPrevUnlockMethod;
	SharedPreferences mPreferences;
	Editor mEditor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		mEditor = mPreferences.edit();
		mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		mAppDeviceAdmin = new ComponentName(this, AppDeviceAdminReceiver.class);		
		mPrevUnlockMethod = UnlockMethodFactory.getUnlockMethod(this,
				mPreferences.getString("unlock_method", ""));
		
		mPreferences.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}

	protected boolean isActiveAdmin() {
		return mDPM.isAdminActive(mAppDeviceAdmin);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals("password") && isActiveAdmin()) {
			// Set screen lock password.
			mDPM.resetPassword(sharedPreferences.getString(key, ""), 0);
		}
	}

	protected void enableAdmin() {
		if (!isActiveAdmin()) {
			// Launch the activity to have the user enable our
			// admin.
			Intent intent = new Intent(
					DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			intent.putExtra(
					DevicePolicyManager.EXTRA_DEVICE_ADMIN,
					mAppDeviceAdmin);
			intent.putExtra(
					DevicePolicyManager.EXTRA_ADD_EXPLANATION,
					getString(R.string.device_admin_description));
			startActivityForResult(intent, 1);
		}
	}
	
	protected boolean changeUnlockMethod(String name) {
		UnlockMethod unlockMethod = UnlockMethodFactory.getUnlockMethod(this,
				mPreferences.getString(name, ""));

		// Deactivate previous unlock method.
		if (mPrevUnlockMethod != null) {
			mPrevUnlockMethod.onDeactivate();
		}

		if (unlockMethod.onActivate()) {
			// Set previous method to current. This is needed if user will
			// change
			// unlock methods more then once without leaving SettingsActivity.
			mPrevUnlockMethod = unlockMethod;
		} else {
			// Reactivate previous method on error.
			if (mPrevUnlockMethod != null) {
				mPrevUnlockMethod.onActivate();
			}
			
			return false;
		}
		
		return true;
	}
}