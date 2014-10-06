package org.imogene.android.sync;

import java.util.HashMap;

import org.imogene.android.preference.Preferences;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import fr.medes.android.util.Utility;

/**
 * The SyncManager handles the lifecycle of synchronization process
 * 
 * SyncManager uses ContentObservers to detect changes in order to maintain proper 2-way syncing of data. (More
 * documentation to follow)
 * 
 */
public class SyncManager extends Service implements Runnable {

	private static final String TAG = SyncManager.class.getName();

	// The SyncManager id
	public static final int ALARM_KICK_ID = -1;
	public static final int ALARM_START_ID = 0;

	private static final int SECONDS = 1000;
	private static final int MINUTES = 60 * SECONDS;
	private static final int ONE_DAY_MINUTES = 1440;

	private static final int CONNECTIVITY_WAIT_TIME = 10 * MINUTES;

	// We synchronize on this for all actions affecting the service and error maps
	private static final Object sSyncLock = new Object();
	// All threads can use this lock to wait for connectivity
	public static final Object sConnectivityLock = new Object();
	public static boolean sConnectivityHold = false;

	// Keeps track of which services require a wake lock (by mailbox id)
	private final HashMap<Long, Long> mWakeLocks = new HashMap<Long, Long>();
	// Keeps track of PendingIntents for mailbox alarms (by mailbox id)
	private final HashMap<Long, PendingIntent> mPendingIntents = new HashMap<Long, PendingIntent>();
	// The actual WakeLock obtained by SyncServiceManager
	private WakeLock mWakeLock = null;

	// The singleton SyncServiceManager object, with its thread and stop flag
	protected static SyncManager INSTANCE;
	protected static Thread sServiceThread = null;

	private static volatile boolean sStartingUp = false;
	private static volatile boolean sStop = false;

	// The reason for SyncServiceManager's next wakeup call
	private String mNextWaitReason;
	// Whether we have an unsatisfied "kick" pending
	private boolean mKicked = false;

	// Receiver of connectivity broadcasts
	private ConnectivityReceiver mConnectivityReceiver = null;

	private Preferences mPreferences;

	private void acquireWakeLock(long id) {
		synchronized (mWakeLocks) {
			Long lock = mWakeLocks.get(id);
			if (lock == null) {
				if (mWakeLock == null) {
					PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
					mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MAIL_SERVICE");
					mWakeLock.acquire();
					// STOPSHIP Remove
					Log.i(TAG, "+WAKE LOCK ACQUIRED");
				}
				mWakeLocks.put(id, System.currentTimeMillis());
			}
		}
	}

	private void releaseWakeLock(long id) {
		synchronized (mWakeLocks) {
			Long lock = mWakeLocks.get(id);
			if (lock != null) {
				mWakeLocks.remove(id);
				if (mWakeLocks.isEmpty()) {
					if (mWakeLock != null) {
						mWakeLock.release();
					}
					mWakeLock = null;
					// STOPSHIP Remove
					Log.i(TAG, "+WAKE LOCK RELEASED");
				} else {
					Log.i(TAG, "Release request for lock not held: " + id);
				}
			}
		}
	}

	private void clearAlarm(long id) {
		synchronized (mPendingIntents) {
			PendingIntent pi = mPendingIntents.get(id);
			if (pi != null) {
				AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
				alarmManager.cancel(pi);
				Log.i(TAG, "+Alarm cleared for " + id);
				mPendingIntents.remove(id);
			}
		}
	}

	private void setAlarm(long id, long millis) {
		synchronized (mPendingIntents) {
			PendingIntent pi = mPendingIntents.get(id);
			if (pi == null) {
				Intent i = new Intent(this, SyncAlarmReceiver.class);
				i.putExtra("mailbox", id);
				i.setData(Uri.parse("Box" + id));
				pi = PendingIntent.getBroadcast(this, 0, i, 0);
				mPendingIntents.put(id, pi);

				AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
				alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + millis, pi);
				Log.i(TAG, "+Alarm set for " + id + ", " + millis / 1000 + "s");
			}
		}
	}

	private void clearAlarms() {
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		synchronized (mPendingIntents) {
			for (PendingIntent pi : mPendingIntents.values()) {
				alarmManager.cancel(pi);
			}
			mPendingIntents.clear();
		}
	}

	static public void runAwake(long id) {
		SyncManager ssm = INSTANCE;
		if (ssm != null) {
			ssm.acquireWakeLock(id);
			ssm.clearAlarm(id);
		}
	}

	static public void runAsleep(long id, long millis) {
		SyncManager ssm = INSTANCE;
		if (ssm != null) {
			ssm.setAlarm(id, millis);
			ssm.releaseWakeLock(id);
		}
	}

	static public void alert() {
		checkSyncManagerRunning();
		Log.i(TAG, "SyncServiceManager alert");
		kick("ping SyncServiceManager");
	}

	private void waitForConnectivity() {
		boolean waiting = false;
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		while (!sStop) {
			NetworkInfo info = cm.getActiveNetworkInfo();
			if (info != null) {
				// We're done if there's an active network
				return;
			} else {
				// If this is our first time through the loop, shut down running service threads
				if (!waiting) {
					waiting = true;
				}
				// Wait until a network is connected (or 10 mins), but let the device sleep
				// We'll set an alarm just in case we don't get notified (bugs happen)
				synchronized (sConnectivityLock) {
					runAsleep(ALARM_KICK_ID, CONNECTIVITY_WAIT_TIME + 5 * SECONDS);
					try {
						Log.i(TAG, "Connectivity lock...");
						sConnectivityHold = true;
						sConnectivityLock.wait(CONNECTIVITY_WAIT_TIME);
						Log.i(TAG, "Connectivity lock released...");
					} catch (InterruptedException e) {
						// This is fine; we just go around the loop again
					} finally {
						sConnectivityHold = false;
					}
					runAwake(ALARM_KICK_ID);
				}
			}
		}
	}

	/**
	 * Note that there are two ways the SyncManager service can be created:
	 * 
	 * 1) as a background service instantiated via startService (which happens on boot, when the account is created,
	 * etc), in which case the service thread is spun up, sync happens, etc. and 2) to execute an RPC call from the UI,
	 * in which case the background service will already be running most of the time
	 * 
	 * Because there are edge cases in which our process can crash (typically, this has been seen in UI crashes, ANR's,
	 * etc.), it's possible for the UI to start up again without the background service having been started. We
	 * explicitly try to start the service in Welcome (to handle the case of the app having been reloaded). We also
	 * start the service on any startSync call (if it isn't already running)
	 */
	@Override
	public void onCreate() {
		Utility.runAsync(new Runnable() {
			@Override
			public void run() {
				// Quick checks first, before getting the lock
				if (sStartingUp)
					return;
				synchronized (sSyncLock) {
					Log.i(TAG, "!!! onCreate");
					// Try to start up properly; we might be coming back from a crash that the Email
					// application isn't aware of.
					if (INSTANCE != null) {
						startService(new Intent(INSTANCE, SyncManager.class));
					}
					if (sStop) {
						return;
					}
				}
			}
		});
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "!!! onStartCommand, startingUp = " + sStartingUp + ", running = " + (INSTANCE != null));
		if (!sStartingUp && INSTANCE == null) {
			sStartingUp = true;
			Utility.runAsync(new Runnable() {
				@Override
				public void run() {
					try {
						synchronized (sSyncLock) {
							// Update other services depending on final account configuration
							maybeStartSyncServiceManagerThread();
							if (sStop) {
								// If we were trying to stop, attempt a restart in 5 secs
								setAlarm(ALARM_START_ID, 5 * SECONDS);
							}
						}
					} finally {
						sStartingUp = false;
					}
				}
			});
		}
		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "!!! onDestroy");
		// Handle shutting down off the UI thread
		Utility.runAsync(new Runnable() {
			@Override
			public void run() {
				// Quick checks first, before getting the lock
				if (INSTANCE == null || sServiceThread == null)
					return;
				synchronized (sSyncLock) {
					// Stop the sync manager thread and return
					if (sServiceThread != null) {
						sStop = true;
						sServiceThread.interrupt();
					}
				}
			}
		});
	}

	void maybeStartSyncServiceManagerThread() {
		// Start our thread...
		if (sServiceThread == null || !sServiceThread.isAlive()) {
			Log.i(TAG, sServiceThread == null ? "Starting thread..." : "Restarting thread...");
			sServiceThread = new Thread(this, TAG);
			INSTANCE = this;
			sServiceThread.start();
		}
	}

	/**
	 * Start up the SyncManager service if it's not already running This is a stopgap for cases in which SyncManager
	 * died (due to a crash somewhere in org.imogene.android) and hasn't been restarted. See the comment for onCreate
	 * for details
	 */
	static void checkSyncManagerRunning() {
		SyncManager ssm = INSTANCE;
		if (ssm == null)
			return;
		if (sServiceThread == null) {
			Log.i(TAG, "!!! checkSyncServiceManagerServiceRunning; starting service...");
			ssm.startService(new Intent(ssm, SyncManager.class));
		}
	}

	@Override
	public void run() {
		sStop = false;
		Log.i(TAG, "Service thread running");

		// Synchronize here to prevent a shutdown from happening while we initialize our observers
		// and receivers
		synchronized (sSyncLock) {
			if (INSTANCE != null) {
				mConnectivityReceiver = new ConnectivityReceiver();
				registerReceiver(mConnectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
				mPreferences = Preferences.getPreferences(this);
			}
		}

		try {
			// Loop indefinitely until we're shut down
			while (!sStop) {
				runAwake(ALARM_KICK_ID);
				waitForConnectivity();
				mNextWaitReason = null;
				if (mPreferences.isSyncEnabled()) {
					SynchronizationController.getInstance(INSTANCE).synchronize();
				}
				long nextWait = mPreferences.getSyncPeriod() * SECONDS;
				try {
					synchronized (this) {
						if (!mKicked) {
							if (nextWait < 0) {
								Log.i(TAG, "Negative wait? Setting to 1s");
								nextWait = 1 * SECONDS;
							}
							if (nextWait > 10 * SECONDS) {
								if (mNextWaitReason != null) {
									Log.i(TAG, "Next awake " + nextWait / 1000 + "s: " + mNextWaitReason);
								}
								runAsleep(ALARM_KICK_ID, nextWait + (3 * SECONDS));
							}
							wait(nextWait);
						}
					}
				} catch (InterruptedException e) {
					// Needs to be caught, but causes no problem
					Log.i(TAG, "SyncServiceManager interrupted");
				} finally {
					synchronized (this) {
						if (mKicked) {
							Log.i(TAG, "Wait deferred due to kick");
							mKicked = false;
						}
					}
				}
			}
			Log.i(TAG, "Shutdown requested");
		} catch (RuntimeException e) {
			// Crash; this is a completely unexpected runtime error
			Log.e(TAG, "RuntimeException", e);
			throw e;
		} finally {
			shutdown();
		}
	}

	private void shutdown() {
		synchronized (sSyncLock) {
			// If INSTANCE is null, we've already been shut down
			if (INSTANCE != null) {
				Log.i(TAG, "Shutting down...");

				// Stop receivers
				if (mConnectivityReceiver != null) {
					try {
						// Maybe unregistered before, when service stopped as we are running in a separate thread
						unregisterReceiver(mConnectivityReceiver);
					} catch (Exception e) {
						Log.e(TAG, "Error stopping receiver", e);
					}
					mConnectivityReceiver = null;
				}

				// Clear pending alarms and associated Intents
				clearAlarms();

				// Release our wake lock, if we have one
				synchronized (mWakeLocks) {
					if (mWakeLock != null) {
						mWakeLock.release();
						mWakeLock = null;
					}
				}

				INSTANCE = null;
				sServiceThread = null;
				sStop = false;
				Log.i(TAG, "Goodbye");
			}
		}
	}

	static public void startManualSync(final Context context) {
		SyncManager ssm = INSTANCE;
		if (ssm != null) {
			kick("Start Manual Sync");
		} else {
			new Thread() {
				@Override
				public void run() {
					SynchronizationController.getInstance(context).synchronize();
				};
			}.start();
		}
	}

	static public void start(Context context) {
		context.startService(new Intent(context, SyncManager.class));
	}

	static public void startIfNeeded(Context context) {
		if (Preferences.getPreferences(context).isSyncEnabled()) {
			start(context);
		}
	}

	static public void stop(Context context) {
		context.stopService(new Intent(context, SyncManager.class));
	}

	/**
	 * Wake up SyncManager to check
	 */
	static public void kick(String reason) {
		SyncManager ssm = INSTANCE;
		if (ssm != null) {
			synchronized (ssm) {
				Log.i(TAG, "Kick: " + reason);
				ssm.mKicked = true;
				ssm.notify();
			}
		}
		if (sConnectivityLock != null) {
			synchronized (sConnectivityLock) {
				sConnectivityLock.notify();
			}
		}
	}

	public class ConnectivityReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle b = intent.getExtras();
			if (b != null) {
				NetworkInfo a = (NetworkInfo) b.get(ConnectivityManager.EXTRA_NETWORK_INFO);
				String info = "Connectivity alert for " + a.getTypeName();
				State state = a.getState();
				if (state == State.CONNECTED) {
					info += " CONNECTED";
					Log.i(TAG, info);
					synchronized (sConnectivityLock) {
						sConnectivityLock.notifyAll();
					}
					// kick("connected");
				} else if (state == State.DISCONNECTED) {
					info += " DISCONNECTED";
					Log.i(TAG, info);
					// kick("disconnected");
				}
			}
		}
	}

}
