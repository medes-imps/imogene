package org.imogene.android.preference;

import java.util.UUID;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

public class RenewHardwareId extends DialogPreference {
	
	public RenewHardwareId(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RenewHardwareId(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	public CharSequence getSummary() {
		return getPersistedString(null);
	}
	
	@Override
	protected void onDialogClosed(boolean positiveResult) {
		super.onDialogClosed(positiveResult);
		if (positiveResult) {
			persistString(UUID.randomUUID().toString());
			notifyChanged();
		}
	}

}
