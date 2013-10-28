package org.imogene.android.preference;

import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.domain.SyncHistory;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

public class DeleteSyncHistoryPreference extends DialogPreference {

	public DeleteSyncHistoryPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DeleteSyncHistoryPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		super.onDialogClosed(positiveResult);
		if (positiveResult) {
			new Thread() {
				@Override
				public void run() {
					ImogOpenHelper.getHelper().delete(SyncHistory.Columns.TABLE_NAME, null, null);
				};
			}.start();

		}
	}

}
