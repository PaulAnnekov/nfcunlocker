package com.steelrat.nfcunlocker.settingsactivity;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.view.ViewGroup;

import com.steelrat.nfcunlocker.R;

@SuppressWarnings("deprecation")
public class SettingsActivityOA extends SettingsActivityBase {
	final static String ACTION_PREFS_GENERAL = "com.steelrat.nfcunlocker.PREFS_GENERAL";
	final static String ACTION_PREFS_ABOUT = "com.steelrat.nfcunlocker.PREFS_ABOUT";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String action = getIntent().getAction();
		if (action != null && action.equals(ACTION_PREFS_GENERAL)) {
			loadGeneral();
		} else if (action != null && action.equals(ACTION_PREFS_ABOUT)) {
			loadAbout();
		} else {
			addPreferencesFromResource(R.xml.pref_headers_oa);
		}
	}

	private void loadGeneral() {
		addPreferencesFromResource(R.xml.pref_general);
		findPreference("unlock_method").setOnPreferenceChangeListener(
				new OnPreferenceChangeListener() {

					@Override
					public boolean onPreferenceChange(Preference preference,
							Object newValue) {
						return changeUnlockMethod((String) newValue);
					}
				});

		findPreference("password").setOnPreferenceClickListener(
				new OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference preference) {
						enableAdmin();
						
						return false;
					}
				});
	}

	private void loadAbout() {
		setContentView(R.layout.settings_about);

		ViewGroup aboutView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content))
				.getChildAt(0);
		updateAbout(aboutView);
	}
}
