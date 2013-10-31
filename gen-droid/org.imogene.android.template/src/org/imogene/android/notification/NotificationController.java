package org.imogene.android.notification;

import java.util.List;

import org.imogene.android.Constants.Extras;
import org.imogene.android.database.ImogBeanCursor;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.database.sqlite.stmt.QueryBuilder;
import org.imogene.android.domain.ImogBean;
import org.imogene.android.domain.ImogHelper;
import org.imogene.android.domain.ImogHelper.EntityInfo;
import org.imogene.android.domain.ImogHelper.ImogBeanCallback;
import org.imogene.android.template.R;
import org.imogene.android.util.content.ContentUrisUtils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.SparseArray;

public class NotificationController {

	private static final String TAG = NotificationController.class.getName();

	/**
	 * Minimum interval between notification sounds. Since a long sync (after a long period of being offline) can cause
	 * several notifications consecutively, it can be pretty overwhelming to get a barrage of notification sounds.
	 * Throttle them using this value.
	 */
	private static final long MIN_SOUND_INTERVAL_MS = 15 * 1000; // 15 seconds

	private static NotificationController sInstance;
	private static NotificationThread sNotificationThread;
	private static Handler sNotificationHandler;
	private final NotificationManager mNotificationManager;
	private final AudioManager mAudioManager;
	private final Context mContext;
	private final SparseArray<ContentObserver> mNotificationMap;

	/**
	 * Timestamp indicating when the last notification sound was played. Used for throttling.
	 */
	private long mLastMessageNotifyTime;

	/** Singleton access */
	public static synchronized NotificationController getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new NotificationController(context);
		}
		return sInstance;
	}

	private NotificationController(Context context) {
		mContext = context.getApplicationContext();
		mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		mNotificationMap = new SparseArray<ContentObserver>();
	}

	/**
	 * Tells the notification controller if it should be watching for changes to the entities table. This is the main
	 * life cycle method for entities notifications. When we start observing database changes, we check the entities
	 * state.
	 * 
	 * @param watch If {@code true}, we register observers. Otherwise, all observers are unregistered.
	 */
	public void watchForMessages(final boolean watch) {
		Log.i(TAG, "Notifications being toggled: " + watch);
		// Don't create the thread if we're only going to stop watching
		if (!watch && sNotificationThread == null)
			return;

		ensureHandlerExists();
		// Run this on the message notification handler
		sNotificationHandler.post(new Runnable() {
			@Override
			public void run() {
				if (watch) {
					// otherwise, start new observers for all notified accounts
					registerMessageNotification();
				} else {
					unregisterMessageNotification();

					// tear down the event loop
					sNotificationThread.quit();
					sNotificationThread = null;
				}
			}
		});
	}

	/**
	 * Registers the observers for changes. NOTE: This must be called on the notification handler thread.
	 */
	private void registerMessageNotification() {
		final ContentResolver resolver = mContext.getContentResolver();
		final List<Uri> hidden = ImogHelper.getInstance().getHiddenUris(mContext);
		ImogHelper.getInstance().doWithImogBeans(new ImogBeanCallback() {

			@Override
			public void doWith(Class<? extends ImogBean> clazz, Uri uri) {
				EntityInfo info = ImogHelper.getEntityInfo(clazz);
				if (info == null) {
					// Not an observable entity
					return;
				}
				ContentObserver obs = mNotificationMap.get(info.notificationId);
				if (hidden.contains(uri)) {
					if (obs != null) {
						resolver.unregisterContentObserver(obs);
						mNotificationMap.delete(info.notificationId);
					}
				} else {
					if (obs != null) {
						// we're already observing; nothing to do
						return;
					}
					EntityContentObserver observer = new EntityContentObserver(sNotificationHandler, info);
					resolver.registerContentObserver(uri, true, observer);
					mNotificationMap.put(info.notificationId, observer);
					observer.onChange(true);
				}

			}
		});
	}

	/**
	 * Unregisters the observers. NOTE: This must be called on the notification handler thread.
	 */
	private void unregisterMessageNotification() {
		ContentResolver resolver = mContext.getContentResolver();
		for (int i = 0; i < mNotificationMap.size(); i++) {
			resolver.unregisterContentObserver(mNotificationMap.valueAt(i));
		}
		mNotificationMap.clear();
	}

	/**
	 * Ensures the notification handler exists and is ready to handle requests.
	 */
	private static synchronized void ensureHandlerExists() {
		if (sNotificationThread == null) {
			sNotificationThread = new NotificationThread();
			sNotificationHandler = new Handler(sNotificationThread.getLooper());
		}
	}

	/**
	 * Returns a {@link Notification} that resumes the informations about the entity and ready to be post in the status
	 * bar.
	 * 
	 * @param title the notification title
	 * @param contentText the notification main text
	 * @param ticker the ticker text displayed in the status bar
	 * @param intent the intent when notification is clicked
	 * @param iconRes the icon resource identifier
	 * @return The built notification
	 */
	private Notification createNotification(CharSequence title, CharSequence contentText, CharSequence ticker,
			Intent intent, int iconRes) {
		Notification notification = new Notification(iconRes, ticker, System.currentTimeMillis());

		// Make a startActivity() PendingIntent for the notification.
		PendingIntent pendingIntent = null;
		if (intent != null) {
			pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		}

		// Update the notification.
		notification.setLatestEventInfo(mContext, title, contentText, pendingIntent);

		long now = System.currentTimeMillis();
		boolean enableAudio = (now - mLastMessageNotifyTime) > MIN_SOUND_INTERVAL_MS;
		if (enableAudio) {
			setupSoundAndVibration(notification);
		}

		// TODO set up delete intent
		// Intent intent = new Intent(context, OnDeletedReceiver.class);
		// notification.deleteIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

		mLastMessageNotifyTime = now;
		return notification;
	}

	/**
	 * Sets up the notification's sound and vibration.
	 * 
	 * @param notification the notification to update
	 */
	private void setupSoundAndVibration(Notification notification) {
		boolean vibrateAlways = true;
		boolean vibrateSilent = false;

		boolean nowSilent = mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE;

		if (vibrateAlways || vibrateSilent && nowSilent) {
			notification.defaults |= Notification.DEFAULT_VIBRATE;
		}

		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		notification.defaults |= Notification.DEFAULT_LIGHTS;
	}

	/**
	 * Convenient method to create a ticker message from the title and the content text.
	 * 
	 * @param header the title text
	 * @param body the content text
	 * @return a resume of both parameters
	 */
	private static CharSequence buildTickerMessage(String header, String body) {
		StringBuilder buf = new StringBuilder(header.replace('\n', ' ').replace('\r', ' '));
		buf.append(':').append(' ');

		int offset = buf.length();
		if (!TextUtils.isEmpty(body)) {
			body = body.replace('\n', ' ').replace('\r', ' ');
			buf.append(body);
		}

		SpannableString spanText = new SpannableString(buf.toString());
		spanText.setSpan(new StyleSpan(Typeface.BOLD), 0, offset, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		return spanText;
	}

	/**
	 * Observer invoked whenever an entity we're notifying the user about changes.
	 */
	private static class EntityContentObserver extends ContentObserver {

		private final EntityInfo mInfo;

		public EntityContentObserver(Handler handler, EntityInfo info) {
			super(handler);
			mInfo = info;
		}

		@Override
		public void onChange(boolean selfChange) {
			QueryBuilder builder = ImogOpenHelper.getHelper().queryBuilder(mInfo.contentUri);
			builder.where().eq(ImogBean.Columns.UNREAD, 1);
			builder.orderBy(ImogBean.Columns.MODIFIED, false);
			ImogBeanCursor c = (ImogBeanCursor) builder.query();
			if (c == null) {
				return;
			}
			try {
				if (!c.moveToFirst()) {
					sInstance.mNotificationManager.cancel(mInfo.notificationId);
					return;
				}

				int count = c.getCount();

				Intent intent = new Intent(Intent.ACTION_VIEW);
				if (count > 1) {
					intent.setData(mInfo.contentUri);
					intent.putExtra(Extras.EXTRA_SORT_KEY, ImogBean.Columns.UNREAD);
					intent.putExtra(Extras.EXTRA_ASCENDING, false);
				} else {
					intent.setData(ContentUrisUtils.withAppendedId(mInfo.contentUri, c.getId()));
				}

				String title = sInstance.mContext.getString(mInfo.description_sg);
				String description = sInstance.mContext.getResources().getQuantityString(R.plurals.ig_numberOfEntities,
						count, count);
				CharSequence ticker = buildTickerMessage(title, description);

				Notification n = sInstance.createNotification(title, description, ticker, intent, mInfo.drawable);

				if (n != null) {
					sInstance.mNotificationManager.notify(mInfo.notificationId, n);
				}
			} finally {
				c.close();
			}
		}

	}

	/**
	 * Thread to handle all notification actions through its own {@link Looper}.
	 */
	private static class NotificationThread implements Runnable {
		/** Lock to ensure proper initialization */
		private final Object mLock = new Object();
		/** The {@link Looper} that handles messages for this thread */
		private Looper mLooper;

		NotificationThread() {
			new Thread(null, this, "EmailNotification").start();
			synchronized (mLock) {
				while (mLooper == null) {
					try {
						mLock.wait();
					} catch (InterruptedException ex) {
					}
				}
			}
		}

		@Override
		public void run() {
			synchronized (mLock) {
				Looper.prepare();
				mLooper = Looper.myLooper();
				mLock.notifyAll();
			}
			Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
			Looper.loop();
		}

		void quit() {
			mLooper.quit();
		}

		Looper getLooper() {
			return mLooper;
		}
	}
}
