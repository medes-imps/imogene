package org.imogene.android.preference;

import org.imogene.android.app.UnSyncDialog;
import org.imogene.android.util.database.DatabaseUtils;

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
		if (positiveResult) {
			new Thread() {
				@Override
				public void run() {
					if (DatabaseUtils.hasUnSync()) {
						Intent i = new Intent(getContext(), UnSyncDialog.class);
						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						getContext().startActivity(i);
					} else {
						DatabaseUtils.deleteAll(getContext());
					}
				};
			}.start();
		}
	}
}
