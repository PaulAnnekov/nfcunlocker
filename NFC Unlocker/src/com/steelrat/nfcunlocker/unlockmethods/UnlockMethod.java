package com.steelrat.nfcunlocker.unlockmethods;

import android.app.Activity;

public abstract class UnlockMethod {
	private String mPassword;
	private Activity mActivity;
	
	public UnlockMethod(Activity activity) {
		mActivity = activity;
	}
	
	public void unlock(String password) {
		mPassword = password;
	}
	
	/**
	 * Called on unlock method activation.
	 * @return boolean Activation result (success or fail).
	 */
	public boolean onActivate() {
		return true;
	}
	
	/**
	 * Called on unlock method deactivation.
	 */
	public void onDeactivate() {
		
	}
	
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

	protected Activity getActivity() {
		return mActivity;
	}

	protected void setActivity(Activity activity) {
		mActivity = activity;
	}
}
