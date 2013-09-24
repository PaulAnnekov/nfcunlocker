package com.steelrat.nfcunlocker;

import com.steelrat.nfcunlocker.unlockmethods.FlagUnlock;
import com.steelrat.nfcunlocker.unlockmethods.InputUnlock;
import com.steelrat.nfcunlocker.unlockmethods.KeyguardUnlock;
import com.steelrat.nfcunlocker.unlockmethods.UnlockMethod;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import org.acra.*;
import org.acra.annotation.*;

@ReportsCrashes(
	formKey = "", // This is required for backward compatibility but not used
	formUri = "http://www.bugsense.com/api/acra?api_key=c5c1eaf9"
)
public class NFCApplication extends Application {
	static Context mContext;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		mContext = this;
		
		// The following line triggers the initialization of ACRA
        ACRA.init(this);
	}
	
	public static Context getContext() {
		return mContext;
	}
}
