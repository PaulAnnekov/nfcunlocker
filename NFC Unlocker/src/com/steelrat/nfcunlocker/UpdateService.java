package com.steelrat.nfcunlocker;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

abstract class UpdateService extends Service {		
	BroadcastReceiver mReceiver;
    boolean mIsRegistered = false;
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	if (!mIsRegistered) {
    		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
	        registerReceiver(mReceiver, filter);
    	}
    	Log.i("UpdateService", "onStartCommand");
    	return super.onStartCommand(intent, flags, startId);
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	
    	if (mIsRegistered) {
    		unregisterReceiver(mReceiver);
    	}
    }

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
