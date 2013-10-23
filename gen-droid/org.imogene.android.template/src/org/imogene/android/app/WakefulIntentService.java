package org.imogene.android.app;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

public abstract class WakefulIntentService extends IntentService {

	private static final String LOCK_NAME_STATIC = "org.imogene.android.app.WakefulIntentService";
	private static volatile PowerManager.WakeLock lockStatic = null;

	protected abstract void doWakefulWork(Intent intent);

	private synchronized static PowerManager.WakeLock getLock(Context context) {
		if (lockStatic == null) {
			PowerManager mgr = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
			lockStatic = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, LOCK_NAME_STATIC);
			lockStatic.setReferenceCounted(true);
		}
		return lockStatic;
	}

	public static void sendWakefulWork(Context context, Intent i) {
		getLock(context.getApplicationContext()).acquire();
		context.startService(i);
	}

	public static void sendWakefulWork(Context context, Class<?> clazz) {
		sendWakefulWork(context, new Intent(context, clazz));
	}

	public WakefulIntentService(String name) {
		super(name);
		setIntentRedelivery(true);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if ((flags & START_FLAG_REDELIVERY) != 0) { // if crash restart...
			getLock(this.getApplicationContext()).acquire(); // ...then quick grab the lock
		}

		super.onStartCommand(intent, flags, startId);

		return START_REDELIVER_INTENT;
	}

	@Override
	protected final void onHandleIntent(Intent intent) {
		try {
			doWakefulWork(intent);
		} finally {
			getLock(this.getApplicationContext()).release();
		}
	}
}
