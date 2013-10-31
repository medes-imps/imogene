package org.imogene.android.receiver;

import org.imogene.android.Constants.Extras;
import org.imogene.android.Constants.Intents;
import org.imogene.android.app.PreferenceActivity;
import org.imogene.android.push.PushService;
import org.imogene.android.sync.SynchronizationService;
import org.imogene.android.util.FormatHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ServicingReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
			SynchronizationService.actionReschedule(context);
			PushService.actionReconnect(context);
		} else if (Intent.ACTION_CONFIGURATION_CHANGED.equals(action)) {
			FormatHelper.updateFormats();
		} else if (Intents.ACTION_SECRET_CODE.equals(action)) {
			Intent i = new Intent(Intent.ACTION_MAIN);
			i.setClass(context, PreferenceActivity.class);
			i.putExtra(Extras.EXTRA_FILENAME, "ig_high_preferences");
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}
	}

}
