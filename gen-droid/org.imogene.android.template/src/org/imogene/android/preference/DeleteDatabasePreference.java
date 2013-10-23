package org.imogene.android.preference;

import org.imogene.android.Constants.Intents;

import android.content.Context;
import android.content.Intent;
import android.preference.DialogPreference;
import android.util.AttributeSet;

public class DeleteDatabasePreference extends DialogPreference {
	
	public DeleteDatabasePreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DeleteDatabasePreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onDialogClosed(boolean positiveResult) {
		super.onDialogClosed(positiveResult);
		if (positiveResult)
			getContext().sendBroadcast(new Intent(Intents.ACTION_RM_DATABASE));
	}
}
