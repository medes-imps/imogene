package org.imogene.sync.client.ui;

public interface ISyncConstants {

	public static final int VERSION_NUMBER = 1;

	public static final int MINUTE_IN_MILLIS = 60 * 1000;

	public static final String VERSION = "org.imogene.sync.client.Version"; //$NON-NLS-1$
	public static final String SYNC_URL = "org.imogene.sync.client.Url"; //$NON-NLS-1$
	public static final String SYNC_AUTO = "org.imogene.sync.client.Auto"; //$NON-NLS-1$
	public static final String SYNC_PERIOD = "org.imogene.sync.client.Period"; //$NON-NLS-1$
	public static final String SYNC_TERMINAL = "org.imogene.sync.client.Terminal"; //$NON-NLS-1$
	public static final String SYNC_LOGIN = "org.imogene.sync.client.Login"; //$NON-NLS-1$
	public static final String SYNC_PASSWORD = "org.imogene.sync.client.Password"; //$NON-NLS-1$
	public static final String SYNC_LAST = "org.imogene.sync.client.Last"; //$NON-NLS-1$
	public static final String NTP_HOST = "org.imogene.sync.client.NtpHost"; //$NON-NLS-1$
	public static final String NTP_RATE = "org.imogene.sync.client.Rate"; //$NON-NLS-1$
	public static final String NTP_OFFSET = "org.imogene.sync.client.Offset"; //$NON-NLS-1$

	public static final String DEFAULT_SYNC_URL = "http://localhost/imogene-sync"; //$NON-NLS-1$
	public static final boolean DEFAULT_SYNC_AUTO = false;
	public static final long DEFAULT_SYNC_PERIOD = 15; // every 15 minutes
	public static final String DEFAULT_NTP_HOST = "africa.pool.ntp.org"; //$NON-NLS-1$
	public static final long DEFAULT_NTP_RATE = 15; // every 15 minutes
	public static final long DEFAULT_NTP_OFFSET = 0; // milliseconds

}
