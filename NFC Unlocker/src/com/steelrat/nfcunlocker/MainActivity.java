package com.steelrat.nfcunlocker;


import java.util.ArrayList;
import java.util.Map;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.steelrat.nfcunlocker.settingsactivity.SettingsActivity;
import com.steelrat.nfcunlocker.settingsactivity.SettingsActivityBase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.SimpleAdapter;

public class MainActivity extends SherlockListActivity {
	TagsStorage mTagsStorage;
	SimpleAdapter mListAdapter;
	ArrayList<Map<String, String>> mTags;
	AlertDialog mDialog;
	boolean mIsDialog = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Load state.
		if (savedInstanceState != null) {
			mIsDialog = savedInstanceState.getBoolean("isDialog", true);
		}
		
		setContentView(R.layout.activity_main);
		
		if (!isNfcSupported()) {
			findViewById(android.R.id.list).setVisibility(View.GONE);
			findViewById(R.id.errorText).setVisibility(View.VISIBLE);
			return;
		}
		
		if (mIsDialog)
			checkNFCState();
			
		registerForContextMenu(getListView());
		
		mTagsStorage = new TagsStorage(this);
		mTags = mTagsStorage.getAllTags();
		
	    String[] from = {"name", "id"};
	    int[] to = {android.R.id.text1, android.R.id.text2};
		
	    mListAdapter = new SimpleAdapter(this, mTags,
	            android.R.layout.simple_list_item_2, from, to);
	    
		setListAdapter(mListAdapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		
		switch(item.getItemId()) {
		    case R.id.action_add:
		    	intent = new Intent(this, AddActivity.class);
				startActivity(intent);
				break;
			case R.id.action_settings:
				startActivity(SettingsActivityBase.getIntent(this));
				break;
			default:
				return super.onOptionsItemSelected(item);
		}	
		
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    
	    getMenuInflater().inflate(R.menu.main_context, menu);
	}
	
	@Override
	public boolean onContextItemSelected(android.view.MenuItem item) {
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    Map<String, String> tag = (Map<String, String>) mListAdapter.getItem(info.position);

	    switch (item.getItemId()) {
	        case R.id.action_delete:
	        	mTagsStorage.removeTag(tag.get("id"));
	        	mTags.remove(tag);
	        	mListAdapter.notifyDataSetChanged();
	            return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
	
	private boolean isNfcSupported() {
		NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);

		return adapter != null;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if (mDialog != null)
			mDialog.dismiss();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putBoolean("isDialog", mDialog != null && mDialog.isShowing());
	}
	
	private void checkNFCState() {
		 NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
		 if (adapter == null) {
			 return;
		 }
			 
		 if (!adapter.isEnabled()) {
			 AlertDialog.Builder builder = new AlertDialog.Builder(this);
			 builder.setMessage(R.string.nfc_disabled_message)
			        .setTitle(R.string.nfc_disabled_title)
			        .setPositiveButton(R.string.enable, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (Build.VERSION.SDK_INT >= 16) {	
					            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
					        } else {
					            startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
					        }
						}
					})
					.setNegativeButton(android.R.string.cancel, null);
			 mDialog = builder.create();
			 mDialog.show();
		 } 
	}
}