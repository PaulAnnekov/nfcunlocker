package com.steelrat.nfcunlocker;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Service;
import android.app.KeyguardManager.KeyguardLock;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.WindowManager;

public class FlagUnlock extends UnlockMethod {
	DevicePolicyManager mDPM;

	public FlagUnlock(Activity activity, String password) {
		super(activity, password);
		
		mDPM = (DevicePolicyManager) mActivity.getSystemService(Context.DEVICE_POLICY_SERVICE);
	}
	
	public void unlock() {
		super.unlock();
				
        mDPM.resetPassword("", 0);
        mActivity.getWindow().addFlags(/*WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | */WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        
        /*Intent i = new Intent(mActivity, FlagUpdateService.class);
        i.putExtra("password", password);
        mActivity.startService(i);*/
        Log.i("FlagUnlock", "unlock");
	}
	
	@Override
	public void onActivityEvent(String event) {
		super.onActivityEvent(event);
		
		// We can safely reset password in onDestroy method of activity because the screen should be already unlocked.
		if (event.equals("onDestroy")) {             
			mDPM.resetPassword(getPassword(), 0);
		// Finish event just after FLAG_DISMISS_KEYGUARD was applied to window.
		} else if (event.equals("onAttachedToWindow")) {
			mActivity.finish();
		}
	}
	
	public static class FlagUpdateService extends UpdateService {
		@Override
		public int onStartCommand(Intent intent, int flags, int startId) {
			if (mReceiver == null) {
				mReceiver = new ScreenReceiver(intent.getExtras().getString("password"));
	    	}
			
			Log.i("FlagUpdateService", "onStartCommand");
			
			return super.onStartCommand(intent, flags, startId);
		}
		
		private class ScreenReceiver extends BroadcastReceiver {
			String mPassword;
			
			public ScreenReceiver(String password) {
				mPassword = password;
			}
			
			@Override
			public void onReceive(Context context, Intent intent) {
				Log.i("ScreenReceiver", "onReceive: '" + mPassword + "'");
				DevicePolicyManager DPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);               
		        DPM.resetPassword(mPassword, 0);
			}
		}
	}
}
