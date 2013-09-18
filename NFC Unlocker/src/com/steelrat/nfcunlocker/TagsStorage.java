package com.steelrat.nfcunlocker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.SimpleAdapter;

public class TagsStorage {
	SharedPreferences mSettings;
	SharedPreferences.Editor mEditor;
	final String TAGS_FILE_NAME = "tags";
	
	TagsStorage(Activity activity) {
		mSettings = activity.getSharedPreferences(TAGS_FILE_NAME, Activity.MODE_PRIVATE);
	    mEditor = mSettings.edit();
	}
	
	public void addTag(String name, byte[] id) {
		mEditor.putString(bytesToHex(id), name);
		mEditor.commit();
	}
	
	public ArrayList<Map<String, String>> getAllTags() {
		Map<String, String> tagsMap = (Map<String, String>) mSettings.getAll();
		
		ArrayList<Map<String, String>> tagsList = new ArrayList<Map<String, String>>();
		for(Entry<String, String> entry : tagsMap.entrySet()) {
			HashMap<String, String> item = new HashMap<String, String>();
		    item.put("id", entry.getKey());
		    item.put("name", entry.getValue());
			tagsList.add(item);
		}
		
		return tagsList;
	}
	
	public void removeTag(String id) {
		mEditor.remove(id);
		mEditor.commit();
	}
	
	public boolean isExists(String id) {
		return mSettings.getString(id, null) != null;
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
