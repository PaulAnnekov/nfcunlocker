package com.steelrat.nfcunlocker.unlockmethods;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

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
public class KeyguardUnlock extends DevicePolicyUnlockMethod {
	static KeyguardLock mKeyguardLock;
	
	public KeyguardUnlock(Activity activity) {
		super(activity);
	}
	
	public void unlock(String password) {
		super.unlock(password);
			
		Context context = getActivity();
		
		// Clear password and disable keyguard.
		clearPassword();
		getKeyguardLock(context).disableKeyguard();

		// Launch service that will host broadcast receiver for screen off action.
		Intent i = new Intent(context, KeyguardService.class);
        i.setAction(KeyguardService.ACTION_INIT);
		i.putExtra("password", password);
        context.startService(i);
	}
	
	@Override
	public void onActivityEvent(String event) {
		super.onActivityEvent(event);
		
		// We can finish activity just after unlock.
		if (event.equals("onCreate")) {             
			getActivity().finish();
		}
	}
	
	@Override
	public void onDeactivate() {
		super.onDeactivate();

		// Stop service that may running.
		Intent i = new Intent(getActivity(), KeyguardService.class);
		i.setAction("");
		getActivity().stopService(i);
	}
	
	private static KeyguardLock getKeyguardLock(Context context) {
		if (mKeyguardLock == null) {
			KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Activity.KEYGUARD_SERVICE);  
	        
			mKeyguardLock = keyguardManager.newKeyguardLock("nfcunlocker");
		}
				
		return mKeyguardLock;
	}
	
	public static class KeyguardService extends Service {
		BroadcastReceiver mReceiver;
		String mPassword;
		public static final String ACTION_INIT = "com.steelrat.nfcunlocker.unlockmethods.ACTION_INIT";
		public static final String ACTION_LOCK = "com.steelrat.nfcunlocker.unlockmethods.ACTION_LOCK";
		
	    @Override
		public int onStartCommand(Intent intent, int flags, int startId) {
	    	String action = intent.getAction();
	    	
	    	// Create new broadcast receiver for screen off action.
			if (action.equals(ACTION_INIT)) {
				mPassword = intent.getStringExtra("password");
				mReceiver = new ScreenReceiver();
				IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		        registerReceiver(mReceiver, filter);
			} else if (action.equals(ACTION_LOCK)) {
				stopSelf();
			}

			// Don't restart service after kill. Keyguard will be re-enabled in
			// onDestroy automatically.
			return START_NOT_STICKY;
		}
	    
	    @Override
	    public void onDestroy() {
	    	super.onDestroy();

	    	restoreKeygurad();
	    	
	    	if (mReceiver != null) {
	    		unregisterReceiver(mReceiver);
	    	}
	    }
		
	    private void restoreKeygurad() {
	    	// Re-enable keyguard and set password back.
			getKeyguardLock(this).reenableKeyguard();
			KeyguardUnlock.setPassword(this, mPassword);
	    }
	    
		private class ScreenReceiver extends BroadcastReceiver {		
			@Override
			public void onReceive(Context context, Intent intent) {
				// Send message to service to re-lock screen.
				Intent i = new Intent(context, KeyguardService.class);
		        i.setAction(KeyguardService.ACTION_LOCK);
		        context.startService(i);
			}
		}
		
		@Override
		public IBinder onBind(Intent intent) {
			return null;
		}
	}
}
