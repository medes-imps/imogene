package org.imogene.android.preference;

import org.imogene.android.template.R;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.util.AttributeSet;

public class ServerUrlPreference extends MyEditTextPreference {

	public ServerUrlPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public void setText(String text) {
		if (text != getPersistedString(null))
			getSharedPreferences().edit().remove(getContext().getString(R.string.ig_sync_shortpw_key)).commit();
		super.setText(text);
		final String onOffKey = getContext().getString(R.string.ig_sync_on_off_key);
		final String oneShotKey = getContext().getString(R.string.ig_sync_one_shot_key);
		CheckBoxPreference onOff = (CheckBoxPreference) getPreferenceManager().findPreference(onOffKey);
		if (onOff != null) {
			onOff.setChecked(false);
			onOff.setEnabled(false);
		}
		Preference oneShot = getPreferenceManager().findPreference(oneShotKey);
		if (oneShot != null) {
			oneShot.setEnabled(false);
		}
	}

}
