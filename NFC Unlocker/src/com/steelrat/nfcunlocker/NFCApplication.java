package com.steelrat.nfcunlocker;

import com.steelrat.nfcunlocker.unlockmethods.FlagUnlock;
import com.steelrat.nfcunlocker.unlockmethods.InputUnlock;
import com.steelrat.nfcunlocker.unlockmethods.KeyguardUnlock;
import com.steelrat.nfcunlocker.unlockmethods.UnlockMethod;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

public class NFCApplication extends Application {
	static Context mContext;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		mContext = this;
	}
	
	public static Context getContext() {
		return mContext;
	}
	
	public static UnlockMethod getUnlockMethod(Activity activity, String methodName) {
		UnlockMethod unlockMethod = null;
		
		if (methodName.equals("FlagUnlock")) {
			unlockMethod = new FlagUnlock(activity);
		} else if (methodName.equals("InputUnlock")) {
			unlockMethod = new InputUnlock(activity);
		} else if (methodName.equals("KeyguardUnlock")) {
			unlockMethod = new KeyguardUnlock(activity);
		}
		
		return unlockMethod;
	}
}
