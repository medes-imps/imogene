package org.imogene.android.preference;

import org.imogene.android.update.UpdateActivity;

import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.util.AttributeSet;

public class CheckUpdatePreference extends Preference {
	
	public CheckUpdatePreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onClick() {
		super.onClick();
		Intent intent = new Intent(getContext(), UpdateActivity.class);
		getContext().startActivity(intent);
	}

}
