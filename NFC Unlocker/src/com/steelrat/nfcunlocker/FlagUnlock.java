package com.steelrat.nfcunlocker;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.view.WindowManager;

/**
 * Screen unlock method that uses FLAG_DISMISS_KEYGUARD window flag to unlock the lock screen and 
 * DevicePolicyManager to clear/set password.
 * 
 * <h3>How does this works:</h3>
 * <ol>
 * <li>Clear the password and dismiss keyguard using FLAG_DISMISS_KEYGUARD window flag.
 * This flag will totally dismiss keyguard (it will not re-appear on navigation) if lock screen is not secured
 * (we are clearing password for such purposes).
 * <li>Finish activity only in onAttachedToWindow event method because window flag will be applied right there.
 * <li>Set password back on activity destroy.
 * </ol>
 * 
 * @author SteelRat
 *
 */
public class FlagUnlock extends UnlockMethod {
	DevicePolicyManager mDPM;

	public FlagUnlock(Activity activity, String password) {
		super(activity, password);
		
		mDPM = (DevicePolicyManager) getActivity().getSystemService(Context.DEVICE_POLICY_SERVICE);
	}
	
	public void unlock() {
		super.unlock();
				
        mDPM.resetPassword("", 0);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
	}
	
	@Override
	public void onActivityEvent(String event) {
		super.onActivityEvent(event);
		
		// We can safely reset password in onDestroy method of activity because the screen should be already unlocked.
		if (event.equals("onDestroy")) {             
			mDPM.resetPassword(getPassword(), 0);
		// Finish event just after FLAG_DISMISS_KEYGUARD was applied to window.
		} else if (event.equals("onAttachedToWindow")) {
			getActivity().finish();
		}
	}
}
