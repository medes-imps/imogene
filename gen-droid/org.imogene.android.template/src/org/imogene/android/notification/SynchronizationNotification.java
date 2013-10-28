package org.imogene.android.notification;

import org.imogene.android.sync.SynchronizationService;
import org.imogene.android.template.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

public class SynchronizationNotification {

	private static final int SYNC_STATE_ID = 1111;
	private static final int NOTIFICATION_SENT_ID = 1112;

	private static SynchronizationNotification sIntance;

	public static SynchronizationNotification getInstance(Context context) {
		if (sIntance == null) {
			sIntance = new SynchronizationNotification(context);
		}
		return sIntance;
	}

	private final Context mContext;
	private final NotificationManager mNotificationManager;

	private SynchronizationNotification(Context context) {
		mContext = context.getApplicationContext();
		mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	public void notifyState(String msg) {
		Notification notif = new Notification(R.drawable.ig_logo_android_s, msg, System.currentTimeMillis());
		notif.flags = Notification.FLAG_NO_CLEAR;

		PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, new Intent(), 0);
		notif.setLatestEventInfo(mContext, mContext.getString(R.string.ig_synchronization), msg, contentIntent);

		mNotificationManager.notify(SYNC_STATE_ID, notif);
	}

	public void notifySent() {
		Notification notification = new Notification(R.drawable.ig_logo_android_s,
				mContext.getString(R.string.ig_notification_sent_ticker), System.currentTimeMillis());

		// Update the notification.
		PendingIntent pi = PendingIntent.getBroadcast(mContext, 0, new Intent(mContext, OnClickSentReceiver.class), 0);
		notification.setLatestEventInfo(mContext, mContext.getString(R.string.ig_notification_sent_title),
				mContext.getString(R.string.ig_notification_sent_description), pi);

		boolean vibrateAlways = true;
		boolean vibrateSilent = false;

		AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
		boolean nowSilent = audioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE;

		if (vibrateAlways || vibrateSilent && nowSilent) {
			notification.defaults |= Notification.DEFAULT_VIBRATE;
		}

		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		notification.defaults |= Notification.DEFAULT_LIGHTS;

		mNotificationManager.notify(NOTIFICATION_SENT_ID, notification);
	}

	public void notifySentFailed() {
		Notification notification = new Notification(R.drawable.ig_logo_android_s,
				mContext.getString(R.string.ig_notification_sent_failed_ticker), System.currentTimeMillis());

		// Update the notification.
		PendingIntent pi = PendingIntent.getBroadcast(mContext, 0, new Intent(mContext, OnClickSentFailedReceiver.class),
				0);
		notification.setLatestEventInfo(mContext, mContext.getString(R.string.ig_notification_sent_failed_title),
				mContext.getString(R.string.ig_notification_sent_failed_description), pi);

		boolean vibrateAlways = true;
		boolean vibrateSilent = false;

		AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
		boolean nowSilent = audioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE;

		if (vibrateAlways || vibrateSilent && nowSilent) {
			notification.defaults |= Notification.DEFAULT_VIBRATE;
		}

		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		notification.defaults |= Notification.DEFAULT_LIGHTS;

		mNotificationManager.notify(NOTIFICATION_SENT_ID, notification);
	}
	
	public void cancel() {
		mNotificationManager.cancel(SYNC_STATE_ID);
	}

	public static class OnClickSentReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			nm.cancel(NOTIFICATION_SENT_ID);
		}
	};

	public static class OnClickSentFailedReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			nm.cancel(NOTIFICATION_SENT_ID);
			SynchronizationService.actionCheck(context);
		}
	};

}
