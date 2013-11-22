package org.imogene.sync.client.i18n;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.imogene.sync.client.i18n.messages"; //$NON-NLS-1$

	public static String sync_pref_title;
	public static String sync_pref_url;
	public static String sync_pref_url_error;
	public static String sync_pref_auto;
	public static String sync_pref_period;
	public static String sync_pref_terminal;

	public static String ntp_pref_title;
	public static String ntp_pref_host;
	public static String ntp_pref_host_error;
	public static String ntp_pref_rate;

	public static String sync_pref_period_15min;
	public static String sync_pref_period_1hour;
	public static String sync_pref_period_2hours;
	public static String sync_pref_period_4hours;
	public static String sync_pref_period_8hours;
	public static String sync_pref_period_1day;

	public static String sync_title;
	public static String sync_init;
	public static String sync_open;
	public static String sync_open_error;
	public static String sync_send;
	public static String sync_receive;
	public static String sync_close;
	public static String sync_error;

	public static String sync_resume_send;
	public static String sync_resume_receive;
	public static String sync_resume_open;
	public static String sync_resume_sending;
	public static String sync_resume_receiving;
	public static String sync_resume_close;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
