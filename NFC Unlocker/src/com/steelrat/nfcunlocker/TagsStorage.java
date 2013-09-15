package com.steelrat.nfcunlocker;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.SharedPreferences;

public class TagsStorage {
	SharedPreferences mSettings;
	SharedPreferences.Editor mEditor;
	final String TAGS_FILE_NAME = "tags";
	
	TagsStorage(Activity activity) {
		mSettings = activity.getSharedPreferences(TAGS_FILE_NAME, Activity.MODE_PRIVATE);
	    mEditor = mSettings.edit();
	}
	
	public void addTag(String name, byte[] id) {
		mEditor.putString(name, bytesToHex(id));
		mEditor.commit();
	}
	
	public Map<String, String> getAllTags() {
		Map<String, String> tags = (Map<String, String>) mSettings.getAll();
		
		return tags;
	}
	
	public static String bytesToHex(byte[] bytes) {
		final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		
	    char[] hexChars = new char[bytes.length * 2];
	    int v;
	    for ( int j = 0; j < bytes.length; j++ ) {
	        v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    
	    return new String(hexChars);
	}
}
