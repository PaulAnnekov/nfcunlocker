package com.steelrat.nfcunlocker;

import android.app.Activity;

abstract class UnlockMethod {
	private String mPassword;
	Activity mActivity;
	
	public UnlockMethod(Activity activity, String password) {
		mActivity = activity;
		mPassword = password;
	}
	
	public void unlock() {
		
	}
	
	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	}
	
		
	/*private boolean isActiveAdmin() {
		return mDPM.isAdminActive(mAppDeviceAdmin);
	}*/
	
	/**
	 * Called by activity on some event methods.
	 * 
	 * @param event Activity event's method, e.g. onCreate, onDestroy...
	 * @return true, if activity must be finished and false otherwise.
	 */
	public void onActivityEvent(String event) {}

	protected String getPassword() {
		return mPassword;
	}

	protected void setPassword(String password) {
		mPassword = password;
	}
}
