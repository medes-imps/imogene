package org.imogene.android.sync;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * MailboxAlarmReceiver is used to "wake up" the ExchangeService at the appropriate time(s). It may also be used for
 * individual sync adapters, but this isn't implemented at the present time.
 * 
 */
public class SyncAlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		long mailboxId = intent.getLongExtra("mailbox", SyncManager.ALARM_KICK_ID);
		// SYNC_SERVICE_ID tells us that the service is asking to be started
		if (mailboxId == SyncManager.ALARM_START_ID) {
			SyncManager.start(context);
		} else {
			SyncManager.alert();
		}
	}
}
