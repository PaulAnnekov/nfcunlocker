package com.steelrat.nfcunlocker.settingsactivity;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;

import com.steelrat.nfcunlocker.R;

@SuppressWarnings("deprecation")
public class SettingsActivityOA extends SettingsActivityBase {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.pref_general);
		
		findPreference("unlock_method").setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				return changeUnlockMethod((String) newValue);
			}
		});
		
		findPreference("password").setOnPreferenceClickListener(new OnPreferenceClickListener() {
			
			@Override
			public boolean onPreferenceClick(Preference preference) {
				enableAdmin();
				
				return false;
			}
		});	
	}
}
