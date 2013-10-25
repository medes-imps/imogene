package org.imogene.android.preference;

import org.imogene.android.push.PushService;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;

public class PushEnablePreference extends CheckBoxPreference {

	public PushEnablePreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setChecked(boolean checked) {
		if (checked != isChecked()) {
			if (checked) {
				PushService.actionStart(getContext());
			} else {
				PushService.actionStop(getContext());
			}
		}
		super.setChecked(checked);
	}
}
