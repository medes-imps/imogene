package org.imogene.android.preference;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

public class MyEditTextPreference extends EditTextPreference {

	public MyEditTextPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public CharSequence getSummary() {
		return getPersistedString(getContext().getString(android.R.string.unknownName));
	}

}
