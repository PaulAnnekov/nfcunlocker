package com.steelrat.nfcunlocker;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

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
	
	public static String getVersion() {	
		String version = "";
		
		try {
			PackageInfo pInfo;
			pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
			version = pInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			ACRA.getErrorReporter().handleException(e);
		} 
		
		return version;
	}
}
