package com.steelrat.nfcunlocker.unlockmethods;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.view.WindowManager;

/**
 * Screen unlock method that uses FLAG_DISMISS_KEYGUARD window flag to unlock the lock screen and 
 * DevicePolicyManager to clear/set password.
 * 
 * <h3>How does this works:</h3>
 * <ol>
 * <li>Clear the password and dismiss keyguard using FLAG_DISMISS_KEYGUARD window flag.
 * This flag will totally dismiss keyguard (it will not re-appear upon navigation) if lock screen is not secured
 * (we are clearing password for such purposes).
 * <li>Finish activity only in onAttachedToWindow event method because window flag will be applied right there.
 * <li>Set password back on activity destroy.
 * </ol>
 * 
 * @author SteelRat
 *
 */
public class FlagUnlock extends DevicePolicyUnlockMethod {
	DevicePolicyManager mDPM;

	public FlagUnlock(Activity activity) {
		super(activity);
	}
	
	public void unlock(String password) {
		super.unlock(password);

		clearPassword();
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
	}
	
	@Override
	public void onActivityEvent(String event) {
		super.onActivityEvent(event);

		// We can safely reset password in onDestroy method of activity because the screen should be already unlocked.
		if (event.equals("onDestroy")) {             
			restorePassword();
		// Finish event just after FLAG_DISMISS_KEYGUARD was applied to window.
		} else if (event.equals("onAttachedToWindow")) {
			getActivity().finish();
		}
	}
}
