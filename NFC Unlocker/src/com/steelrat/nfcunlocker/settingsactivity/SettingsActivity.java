package com.steelrat.nfcunlocker.settingsactivity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import com.steelrat.nfcunlocker.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SettingsActivity extends SettingsActivityBase {

	@Override
	public void onBuildHeaders(List<Header> target) {
		loadHeadersFromResource(R.xml.pref_headers, target);
	}

	public static class GeneralPreferenceFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.pref_general);
			
			final SettingsActivity activity = (SettingsActivity) getActivity();
			
			findPreference("unlock_method").setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
				
				@Override
				public boolean onPreferenceChange(Preference preference, Object newValue) {
					return activity.changeUnlockMethod((String) newValue);
				}
			});
			
			findPreference("password").setOnPreferenceClickListener(new OnPreferenceClickListener() {
				
				@Override
				public boolean onPreferenceClick(Preference preference) {
					activity.enableAdmin();
					
					return false;
				}
			});
		}
	}
	
	public static class AboutPreferenceFragment extends PreferenceFragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.settings_about, container, false);
			
			SettingsActivity activity = (SettingsActivity) getActivity();
			activity.loadAbout(rootView);
			
			return rootView;
		}
	}
}
