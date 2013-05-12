package com.steelrat.nfcunlocker;

import com.steelrat.nfcunlocker.unlockmethods.UnlockMethod;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.SharedPreferences;

public class DiscoveredActivity extends Activity {
	UnlockMethod mUnlockMethod;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
		KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE);
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String password = sharedPref.getString("password", "");		
        mUnlockMethod = NFCApplication.getUnlockMethod(this, sharedPref.getString("unlock_method", ""));
				  
		// Finish activity if the screen is not locked, unlock method is not set or the password is empty.
		if (!keyguardManager.inKeyguardRestrictedInputMode() || mUnlockMethod == null || password.length() == 0) {
			finish();
        	return;
		}
        
        mUnlockMethod.unlock(password);	
		mUnlockMethod.onActivityEvent("onCreate");
    }
	
	@Override
	public void onAttachedToWindow() {	
		super.onAttachedToWindow();
	
		mUnlockMethod.onActivityEvent("onAttachedToWindow");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
        
		if (mUnlockMethod != null) {
			mUnlockMethod.onActivityEvent("onDestroy");
		}
	}
}
