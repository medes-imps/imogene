package org.imogene.android.receiver;

import org.imogene.android.Constants.Intents;
import org.imogene.android.sync.SynchronizationService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SynchronizationReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if (Intents.ACTION_CHECK_SYNC.equals(intent.getAction())) {
			SynchronizationService.startService(context);
		} else if (Intents.ACTION_RESCHEDULE.equals(intent.getAction())) {
			SynchronizationService.actionReschedule(context);
		} else if (Intents.ACTION_CANCEL.equals(intent.getAction())) {
			SynchronizationService.actionCancel(context);
		}
	}

}
