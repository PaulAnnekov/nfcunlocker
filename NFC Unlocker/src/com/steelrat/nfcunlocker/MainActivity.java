package com.steelrat.nfcunlocker;


import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class MainActivity extends SherlockListActivity {
	TagsStorage mTagsStorage;
	ListAdapter mListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		registerForContextMenu(getListView());
		
		mListAdapter = getListAdapter();
		mTagsStorage = new TagsStorage(this);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, mTagsStorage.getAllTags());
		
		setListAdapter(adapter);
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
				intent = new Intent(this, SettingsActivity.class);
				startActivity(intent);
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
	    switch (item.getItemId()) {
	        case R.id.action_edit:
	            return true;
	        case R.id.action_delete:
	            return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
}