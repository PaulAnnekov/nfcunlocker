package com.steelrat.nfcunlocker;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.SharedPreferences;
import android.util.Log;

public class DiscoveredActivity extends Activity {
	KeyguardLock keyguard;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.i("onCreate", "Tag scanned");
        
        // Get keyguard manager instance for check.
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE);  
        
        // Get password from settings.
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String syncConnPref = sharedPref.getString("password", "");
        
        // Check if screen is locked now + password set by user.
        if (keyguardManager.inKeyguardRestrictedInputMode() && !syncConnPref.isEmpty()) {
        	Log.i("onCreate", "Keyguard enabled");
        	
        	List<String> cmds = new ArrayList<String>();
            cmds.add("input text \"" + syncConnPref + "\"");
            cmds.add("input keyevent 66");
            
            NFCApplication.runAsRoot(cmds);
        } else {
        	Log.i("onCreate", "Keyguard disabled");
        }
        
		finish();
    }
}
