package org.imogene.android.app;

import greendroid.app.GDPreferenceActivity;

import org.imogene.android.template.R;

import android.os.Bundle;

public class HighPreferences extends GDPreferenceActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.high_preferences);
	}

}
