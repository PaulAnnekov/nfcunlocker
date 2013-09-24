package com.steelrat.nfcunlocker.unlockmethods;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.acra.ACRA;

import com.steelrat.nfcunlocker.R;
import com.stericson.RootTools.RootTools;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class InputUnlock extends UnlockMethod {
	public InputUnlock(Activity activity) {
		super(activity);
	}
	
	public void unlock(String password) {
		super.unlock(password);
				
        List<String> cmds = new ArrayList<String>();
        cmds.add("input text \"" + getPassword() + "\"");
        cmds.add("input keyevent 66");
        
        runAsRoot(cmds);
	}
	
	@Override
	public boolean onActivate() {
		super.onActivate();
	
		boolean isRootAccess = RootTools.isAccessGiven();
		if (!isRootAccess) {
			Context c = getActivity();
			Toast.makeText(c, c.getString(R.string.error_root_access), Toast.LENGTH_LONG).show();
		}
		
		return isRootAccess;
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
			e.printStackTrace();
			ACRA.getErrorReporter().handleException(e);
		} catch (InterruptedException e) {
			e.printStackTrace();
			ACRA.getErrorReporter().handleException(e);
		}
	}
	
	@Override
	public void onActivityEvent(String event) {
		super.onActivityEvent(event);
		
		// We can finish activity just after unlock.
		if (event.equals("onCreate")) {             
			getActivity().finish();
		}
	}
}
