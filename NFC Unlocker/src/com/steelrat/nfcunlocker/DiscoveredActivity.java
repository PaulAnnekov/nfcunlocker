package com.steelrat.nfcunlocker;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.WindowManager;

public class DiscoveredActivity extends Activity {
	UnlockMethod mUnlockMethod;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
         
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String password = sharedPref.getString("password", "");
		
        mUnlockMethod = new FlagUnlock(this, password);
        mUnlockMethod.unlock();
		
		mUnlockMethod.onActivityEvent("onCreate");
		
		//Get the window from the context
        //WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		/*DevicePolicyManager DPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        DPM.resetPassword("", 0);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        
        Intent i = new Intent(this, UpdateService.class);
        startService(i);*/
        
        /*DevicePolicyManager DPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        DPM.resetPassword("", 0);
        keyguard = NFCApplication.getKeyguardLock();
        keyguard.disableKeyguard();
        
        Intent i = new Intent(this, UpdateService.class);
        startService(i);*/
        
        /*// Get password from settings.
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String password = sharedPref.getString("password", "");
        
        // Check if screen is locked now + password set by user.
        if (keyguardManager.inKeyguardRestrictedInputMode() && !syncConnPref.isEmpty()) {
        	Log.i("onCreate", "Keyguard enabled");
        	
        	List<String> cmds = new ArrayList<String>();
            cmds.add("input text \"" + syncConnPref + "\"");
            cmds.add("input keyevent 66");
            
            NFCApplication.runAsRoot(cmds);
        } else {
        	Log.i("onCreate", "Keyguard disabled");
        }*/
        
        //finish();
    }
	
	@Override
	public void onAttachedToWindow() {	
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
	
		/*SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String password = sharedPref.getString("password", "");
		
		DevicePolicyManager DPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);               
        DPM.resetPassword(password, 0);*/
		mUnlockMethod.onActivityEvent("onAttachedToWindow");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
        
        mUnlockMethod.onActivityEvent("onDestroy");
	}
}
