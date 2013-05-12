package com.steelrat.nfcunlocker.unlockmethods;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Context;

public abstract class DevicePolicyUnlockMethod extends UnlockMethod {
	DevicePolicyManager mDPM;
	
	public DevicePolicyUnlockMethod(Activity activity) {
		super(activity);
	}
	
	protected void clearPassword() {
		setPassword(getActivity(), "");
	}
	
	protected void restorePassword() {
		setPassword(getActivity(), getPassword());		
	}
	
	private static DevicePolicyManager getPolicyManager(Context context) {
		return (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
	}
	
	public static void setPassword(Context context, String password) {
		getPolicyManager(context).resetPassword(password, 0);
	}
}
