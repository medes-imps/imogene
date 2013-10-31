package org.imogene.android.preference;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

public class BaseDialogPreference extends DialogPreference {
	
	public BaseDialogPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public BaseDialogPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

}
