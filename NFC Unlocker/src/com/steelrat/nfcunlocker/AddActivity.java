package com.steelrat.nfcunlocker;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.steelrat.nfcunlocker.helpers.TagsStorage;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class AddActivity extends SherlockActivity {
	NfcAdapter mAdapter;
	PendingIntent mPendingIntent;
	byte[] mId;
	TagsStorage mTags;
	EditText mTagNameEdit;
	TextView mTagIdText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_add);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mAdapter = NfcAdapter.getDefaultAdapter(this);
		mTags = new TagsStorage(this);
		mTagNameEdit = (EditText) findViewById(R.id.tagNameEdit);
		mTagIdText = (TextView) findViewById(R.id.tagIdText);
		
		mTagNameEdit.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {}
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){
	        	if (s.length() > 0 && mTagNameEdit.getError() != null) {
	            	mTagNameEdit.setError(null);
	            }
	        }
	    });
		
		// Create a generic PendingIntent that will be deliver to this activity.
		// The NFC stack
		// will fill in the intent with the details of the discovered tag before
		// delivering to
		// this activity.
		mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mAdapter != null)
			mAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
	}

	@Override
	public void onNewIntent(Intent intent) {
		mId = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
		mTagIdText.setText(TagsStorage.bytesToHex(mId));
		mTagIdText.setError(null);
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mAdapter != null)
			mAdapter.disableForegroundDispatch(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.add, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;
			case R.id.action_save:
				if (saveTag()) {
					NavUtils.navigateUpFromSameTask(this);
				}
				break;
			default:
				return super.onOptionsItemSelected(item);
		}

		return true;
	}
	
	private boolean saveTag() {
		String text = mTagNameEdit.getText().toString();
		if (text.isEmpty()) {
			mTagNameEdit.setError(getString(R.string.error_empty_tag_name));
			return false;
		} else if (mId == null) {
			mTagIdText.requestFocus();
			mTagIdText.setError(getString(R.string.error_no_tag_scanned));
			return false;
		}
		
		mTags.addTag(mTagNameEdit.getText().toString(), mId);
		
		return true;
	}
}
