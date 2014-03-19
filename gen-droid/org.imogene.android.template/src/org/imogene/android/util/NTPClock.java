package org.imogene.android.util;

import java.io.IOException;
import java.net.SocketException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.imogene.android.preference.Preferences;

import android.content.Context;
import fr.medes.android.util.ntp.NTPUtils;

/**
 * NTPClock uses Apache's NTPUPDClient which implements the Network Time Protocol (RFC-1305) specification:
 * http://www.faqs.org/ftp/rfc/rfc1305.pdf
 * <p/>
 * NTPClock does not synchronize the computer's clock, but merely uses NTP time to timestamp incoming market data.
 */
public class NTPClock {

	private static final long PERIOD = 60 * 60;

	private static NTPClock sInstance;

	private final NTPUDPClient mNtpClient;
	private final AtomicLong mOffset;
	private final Preferences mPreferences;
	private final ScheduledExecutorService mScheduler;

	public static synchronized NTPClock getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new NTPClock(context);
		}
		return sInstance;
	}

	private class NTPClockPoller implements Runnable {
		@Override
		public void run() {
			updateOffsetSync(mPreferences.getNtpHost());
		}
	}

	// private constructor for non-instantiability
	private NTPClock(Context context) {
		mPreferences = Preferences.getPreferences(context);
		mNtpClient = new NTPUDPClient();
		mNtpClient.setDefaultTimeout(10000);
		mOffset = new AtomicLong();
		mScheduler = Executors.newSingleThreadScheduledExecutor();
		mScheduler.scheduleWithFixedDelay(new NTPClockPoller(), 0, PERIOD, TimeUnit.SECONDS);
	}

	public void stop() {
		mScheduler.shutdown();
	}

	public boolean updateOffsetSync(String host) {
		try {
			long offset = NTPUtils.query(host);
			mOffset.set(offset);
			mPreferences.setNtpOffset(offset);
			return true;
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateOffsetSync() {
		String host = mPreferences.getNtpHost();
		return updateOffsetSync(host);
	}

	public void updateOffsetAsync() {
		new Thread() {
			@Override
			public void run() {
				updateOffsetSync();
			};
		}.start();
	}

	public void updateOffsetAsync(final String host) {
		new Thread() {
			@Override
			public void run() {
				updateOffsetSync(host);
			};
		}.start();
	}

	public long currentTimeMillis() {
		return System.currentTimeMillis() + getOffset();
	}

	public Date getTime() {
		return new Date(currentTimeMillis());
	}

	public long getOffset() {
		return mOffset.get();
	}
}