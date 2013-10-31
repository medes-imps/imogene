package org.imogene.android.util.ntp;

import android.os.SystemClock;

public class SntpProvider {

	private static long getRealTimeFromNtp(String ntpServer) throws SntpException {
		SntpClient client = new SntpClient();
		if (client.requestTime(ntpServer, 15000)) {
			/*
			 * test done with NTP if (false) { long time = client.getNtpTime(); long timeReference =
			 * client.getNtpTimeReference(); int certainty = (int)(client.getRoundTripTime()/2);
			 * 
			 * Log.i(TAG, "calling native_inject_time: " + time + " reference: " + timeReference + " certainty: " +
			 * certainty); }
			 */

			long now = client.getNtpTime() + SystemClock.elapsedRealtime() - client.getNtpTimeReference();

			// SystemClock.setCurrentTimeMillis(time);
			// native_inject_time(time, timeReference, certainty);

			return now;
		} else {
			throw new SntpException();
		}
	}

	public static long getTimeOffsetFromNtp(String serverUrl) throws SntpException {
		try {
			long real = SntpProvider.getRealTimeFromNtp(serverUrl);
			long local = System.currentTimeMillis();
			return local - real;
		} catch (SntpException e) {
			throw new SntpException();
		}
	}

}
