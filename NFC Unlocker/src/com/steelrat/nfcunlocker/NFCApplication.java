package com.steelrat.nfcunlocker;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import android.app.Application;

public class NFCApplication extends Application {
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
