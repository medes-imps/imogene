package org.imogene.android.preference;

import org.imogene.android.sync.SynchronizationService;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;

public class SyncOnOffPreference extends CheckBoxPreference {

	public SyncOnOffPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public void setChecked(boolean checked) {
		if (checked != isChecked()) {
			if (checked)
				SynchronizationService.actionReschedule(getContext());
			else
				SynchronizationService.actionCancel(getContext());
		}
		super.setChecked(checked);
	}
}
