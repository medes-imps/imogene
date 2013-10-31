package org.imogene.android.app;

import greendroid.app.GDPreferenceActivity;

import org.imogene.android.Constants.Extras;
import org.imogene.android.template.R;

import android.os.Bundle;
import android.text.TextUtils;

public class PreferenceActivity extends GDPreferenceActivity {
	
	protected int mLayoutId = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String filename = getIntent().getStringExtra(Extras.EXTRA_FILENAME);
		if (!TextUtils.isEmpty(filename)) {
			mLayoutId = getResources().getIdentifier(filename, "xml", getPackageName());
		}
		
		if (mLayoutId == 0) {
			mLayoutId = R.xml.preferences;
		}
		
		addPreferencesFromResource(mLayoutId);
	}

}
