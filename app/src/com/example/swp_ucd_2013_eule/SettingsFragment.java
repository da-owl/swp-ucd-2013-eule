package com.example.swp_ucd_2013_eule;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * SettingsFragment provides a view containing the settings for our APP. For
 * example the IP address of the ExlapProxy, or the user name.
 * 
 */
public class SettingsFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.activity_settings);
	}

}