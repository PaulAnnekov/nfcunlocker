package com.steelrat.nfcunlocker.helpers;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

public class AppDeviceAdminReceiver extends DeviceAdminReceiver {
	@Override
	public void onEnabled(Context context, Intent intent) {
		super.onEnabled(context, intent);
	}
	
	@Override
	public void onDisabled(Context context, Intent intent) {
		super.onDisabled(context, intent);
	}
}
