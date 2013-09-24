package com.steelrat.nfcunlocker.unlockmethods;

import android.app.Activity;

public class UnlockMethodFactory {
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
