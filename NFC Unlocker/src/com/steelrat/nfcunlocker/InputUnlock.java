package com.steelrat.nfcunlocker;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Service;
import android.app.KeyguardManager.KeyguardLock;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.WindowManager;

public class InputUnlock extends UnlockMethod {
	public InputUnlock(Activity activity, String password) {
		super(activity, password);
	}
	
	public void unlock() {
		super.unlock();
				
        List<String> cmds = new ArrayList<String>();
        cmds.add("input text \"" + getPassword() + "\"");
        cmds.add("input keyevent 66");
        
        runAsRoot(cmds);
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
	
		// Just request Root access.
		runAsRoot(null);
	}
	
	/**
	 * Executes commands with Root privileges.
	 * 
	 * @param cmds
	 */
	private void runAsRoot(List<String> cmds) {
    	Process process;
		try {
			process = Runtime.getRuntime().exec("su");
		
            DataOutputStream os = new DataOutputStream(process.getOutputStream());

            if (cmds != null) {
            	for (String tmpCmd : cmds) {
            		os.writeBytes(tmpCmd+"\n");
	            }
            }         
            
            os.writeBytes("exit\n");
            os.flush();
            os.close();

            process.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onActivityEvent(String event) {
		super.onActivityEvent(event);
		
		// We can finish activity just after unlock.
		if (event.equals("onCreate")) {             
			mActivity.finish();
		}
	}
}
