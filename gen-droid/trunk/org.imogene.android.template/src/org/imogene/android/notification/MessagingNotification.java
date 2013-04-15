package org.imogene.android.notification;

import java.util.Date;
import java.util.HashSet;

import org.imogene.android.Constants.Extras;
import org.imogene.android.Constants.Intents;
import org.imogene.android.database.ImogBeanCursor;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.database.sqlite.stmt.QueryBuilder;
import org.imogene.android.domain.ImogBean;
import org.imogene.android.domain.ImogHelper;
import org.imogene.android.template.R;
import org.imogene.android.util.content.ContentUrisUtils;
import org.imogene.android.util.database.DatabaseUtils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;

public class MessagingNotification {
	
	private static final Intent sNotificationOnDeleteIntent = new Intent(Intents.ACTION_NOTIFICATION_DELETED);
	
	private static NotificationHelper sNotificationHelper;
	
	public static void setNotificationHelper(NotificationHelper helper) {
		sNotificationHelper = helper;
	}
	
	/**
	 * Checks to see if there are any "unseen" entities. Shows the most recent
	 * notification if there is one. Does its work and query in a worker thread.
	 * 
	 * @param context the context to use
	 */
	public static void nonBlockingUpdateNewEntityIndicator(final Context context) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				blockingUpdateNewMessageIndicator(context);
			}
		}).start();
	}

	public static void blockingUpdateNewMessageIndicator(Context context) {
		HashSet<NotificationInfo> accumulator = new HashSet<NotificationInfo>();

		sNotificationHelper.feedAccumulator(context, accumulator);

		sNotificationHelper.cancelAll(context);

		for (NotificationInfo info : accumulator) {
			info.deliver(context);
		}
	}
	
	public static void accumulate(HashSet<NotificationInfo> accumulator, NotificationInfo info) {
		if (info != null) {
			accumulator.add(info);
		}
	}

	public static void cancelNotification(Context context, int notificationId) {
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(notificationId);
	}
	
	public static NotificationInfo getEntityNotificationInfo(
			Context context, 
			Uri uri,
			int notificationId,
			int titleId,
			int drawable) {
		QueryBuilder builder = ImogOpenHelper.getHelper().queryBuilder(uri);
		builder.where().eq(ImogBean.Columns.UNREAD, 1);
		builder.orderBy(ImogBean.Columns.MODIFIED, false);
		ImogBeanCursor c = (ImogBeanCursor) builder.query();
		if (c == null) {
			return null;
		}

		try {
			if (!c.moveToFirst()) {
				return null;
			}

			int count = c.getCount();

			Intent clickIntent = new Intent(Intent.ACTION_VIEW);
			if (count > 1) {
				clickIntent.setData(uri);
				clickIntent.putExtra(Extras.EXTRA_SORT_KEY, ImogBean.Columns.UNREAD);
				clickIntent.putExtra(Extras.EXTRA_ASCENDING, false);
			} else {
				clickIntent.setData(ContentUrisUtils.withAppendedId(uri, c.getId()));
			}

			String title = context.getString(titleId);
			String description = context.getResources().getQuantityString(R.plurals.ig_numberOfEntities, count, count);
			CharSequence ticker = buildTickerMessage(title, description);
			Date modified = c.getModified();

			NotificationInfo info = new NotificationInfo(
					notificationId,
					clickIntent,
					description,
					drawable,
					ticker,
					modified != null ? modified.getTime() : System.currentTimeMillis(),
					title);
			return info;
		} finally {
			c.close();
		}

	}
	
	private static void updateNotification(
			Context context,
			int notificationId,
			Intent clickIntent,
			String description,
			int iconRes,
			CharSequence ticker,
			long timeMillis,
			String title) {
		Notification notification = new Notification(iconRes, ticker, timeMillis);

		// Make a startActivity() PendingIntent for the notification.
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		// Update the notification.
		notification.setLatestEventInfo(context, title, description, pendingIntent);

		boolean vibrateAlways = true;
		boolean vibrateSilent = false;

		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		boolean nowSilent = audioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE;

		if (vibrateAlways || vibrateSilent && nowSilent) {
			notification.defaults |= Notification.DEFAULT_VIBRATE;
		}

		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		notification.defaults |= Notification.DEFAULT_LIGHTS;

		// set up delete intent
		notification.deleteIntent = PendingIntent.getBroadcast(context, 0, sNotificationOnDeleteIntent, 0);

		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		nm.notify(notificationId, notification);
	}
	
    private static CharSequence buildTickerMessage(String header, String body) {
        StringBuilder buf = new StringBuilder(header.replace('\n', ' ').replace('\r', ' '));
        buf.append(':').append(' ');

        int offset = buf.length();
        if (!TextUtils.isEmpty(body)) {
            body = body.replace('\n', ' ').replace('\r', ' ');
            buf.append(body);
        }

        SpannableString spanText = new SpannableString(buf.toString());
        spanText.setSpan(new StyleSpan(Typeface.BOLD), 0, offset,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spanText;
    }
    
    public static interface NotificationHelper {
    	
    	public void feedAccumulator(Context context, HashSet<NotificationInfo> accumulator);
    	
    	public void cancelAll(Context context);
    }

	public static class NotificationInfo {
		public int mNotificationId;
		public Intent mClickIntent;
		public String mDescription;
		public int mIconResourceId;
		public CharSequence mTicker;
		public long mTimeMillis;
		public String mTitle;

		public NotificationInfo(int notificationId, Intent clickIntent,
				String description, int iconResourceId, CharSequence ticker,
				long timeMillis, String title) {
			mNotificationId = notificationId;
			mClickIntent = clickIntent;
			mDescription = description;
			mIconResourceId = iconResourceId;
			mTicker = ticker;
			mTimeMillis = timeMillis;
			mTitle = title;
		}

		public void deliver(Context context) {
			updateNotification(context, mNotificationId, mClickIntent,
					mDescription, mIconResourceId, mTicker, mTimeMillis, mTitle);
		}

	}
	
	public static class OnDeletedReceiver extends BroadcastReceiver {
		
		@Override
		public void onReceive(final Context context, Intent intent) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					ContentResolver res = context.getContentResolver();
					for (Uri uri : ImogHelper.getInstance().getAllUris()) {
						DatabaseUtils.markAs(res, uri, false);
					}
				}
			}).start();
		}
	};
}
