package org.imogene.android.receiver;

import org.imogene.android.Constants.Extras;
import org.imogene.android.Constants.Intents;
import org.imogene.android.app.HighPreferences;
import org.imogene.android.app.UnSyncDialog;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.domain.SyncHistory;
import org.imogene.android.preference.PreferenceHelper;
import org.imogene.android.util.FormatHelper;
import org.imogene.android.util.database.DatabaseUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ServicingReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
			boolean sync = PreferenceHelper.getSynchronizationStatus(context);
			if (sync) {
				context.sendBroadcast(new Intent(Intents.ACTION_RESCHEDULE));
			}
		} else if (Intent.ACTION_CONFIGURATION_CHANGED.equals(action)) {
			FormatHelper.updateFormats();
		} else if (Intents.ACTION_SECRET_CODE.equals(action)) {
			Intent i = new Intent(Intent.ACTION_MAIN);
			i.setClass(context, HighPreferences.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		} else if (Intents.ACTION_RM_SYNC_HISTORY.equals(intent.getAction())) {
			ImogOpenHelper.getHelper().delete(SyncHistory.Columns.TABLE_NAME, null, null);
		} else if (Intents.ACTION_RM_DATABASE.equals(intent.getAction())) {
			if (intent.hasExtra(Extras.EXTRA_FORCE)) {
				DatabaseUtils.deleteAll(context);
			} else {
				if (DatabaseUtils.hasUnSync()) {
					context.startActivity(new Intent(context,
							UnSyncDialog.class)
							.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
				} else {
					DatabaseUtils.deleteAll(context);
				}
			}
		}
	}
	


}
