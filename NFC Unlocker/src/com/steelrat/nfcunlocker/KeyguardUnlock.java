package com.steelrat.nfcunlocker;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

/**
 * Screen unlock method that uses KeyguardLock class to dismiss/re-enable keyguard and 
 * DevicePolicyManager to clear/set password.
 * 
 * <h3>How does this works:</h3>
 * <ol>
 * <li>Clear the password and disable keyguard using KeyguardLock.disableKeyguard() method.
 * We are clearing the password because without this action if we will try to switch to another
 * app - keyguard will appear.
 * <li>Launch service that will register and control broadcast receiver for Screen Off action.
 * <li>When Screen Off action is received then we are re-enabling keyguard and setting password back.
 * </ol>
 * 
 * @author SteelRat
 *
 */
public class KeyguardUnlock extends UnlockMethod {
	DevicePolicyManager mDPM;
	static KeyguardLock mKeyguardLock;
	public KeyguardUnlock(Activity activity, String password) {
		super(activity, password);
		
		mDPM = (DevicePolicyManager) getActivity().getSystemService(Context.DEVICE_POLICY_SERVICE);
	}
	
	public void unlock() {
		super.unlock();
			
		// Clear password and disable keyguard.
		mDPM.resetPassword("", 0);
		getKeyguardLock(NFCApplication.getContext()).disableKeyguard();
		
		// Launch service that will host broadcast receiver for screen off action.
		Intent i = new Intent(getActivity(), KeyguardService.class);
        i.putExtra("password", getPassword());
        getActivity().startService(i);
	}
	
	@Override
	public void onActivityEvent(String event) {
		super.onActivityEvent(event);
		
		// We can finish activity just after unlock.
		if (event.equals("onCreate")) {             
			getActivity().finish();
		}
	}
	
	static KeyguardLock getKeyguardLock(Context context) {
		if (mKeyguardLock == null) {
			KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Activity.KEYGUARD_SERVICE);  
	        
			mKeyguardLock = keyguardManager.newKeyguardLock("nfcunlocker");
		}
				
		return mKeyguardLock;
	}
	
	public static class KeyguardService extends Service {
		BroadcastReceiver mReceiver;
	    boolean mIsRegistered = false;
	    
	    @Override
	    public int onStartCommand(Intent intent, int flags, int startId) {
	    	// Create new broadcast receiver for screen off action.
	    	if (!mIsRegistered) {
	    		mReceiver = new ScreenReceiver(intent.getExtras().getString("password"));
	    		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		        registerReceiver(mReceiver, filter);
	    	}
	    	
	    	return super.onStartCommand(intent, flags, startId);
	    }
	    
	    @Override
	    public void onDestroy() {
	    	super.onDestroy();
	    	
	    	if (mIsRegistered) {
	    		unregisterReceiver(mReceiver);
	    	}
	    }
		
		private class ScreenReceiver extends BroadcastReceiver {
			String mPassword;
			
			public ScreenReceiver(String password) {
				mPassword = password;
			}
			
			@Override
			public void onReceive(Context context, Intent intent) {
				DevicePolicyManager DPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);               
				
				// Re-enable keyguard and set password back.
				getKeyguardLock(NFCApplication.getContext()).reenableKeyguard();
				DPM.resetPassword(mPassword, 0);
			}
		}
		
		@Override
		public IBinder onBind(Intent intent) {
			return null;
		}
	}
}
