package com.steelrat.nfcunlocker;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.util.Log;

public class NFCApplication extends Application {
	private static KeyguardLock mKeyguardLock;
	static Context mContext;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		mContext = this;
	}
	
	public static Context getContext() {
		return mContext;
	}
	
	static void runAsRoot(List<String> cmds) {
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
}
