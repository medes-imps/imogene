package org.imogene.client.i18n;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.imogene.client.i18n.messages"; //$NON-NLS-1$

	public static String application_title;

	public static String bugreport_prefs_logs_title;
	public static String bugreport_prefs_logs_server;
	public static String bugreport_prefs_logs_database;
	public static String bugreport_prefs_logs_webapp;
	public static String bugreport_prefs_logs_sync;
	public static String bugreport_prefs_logs_runtime;
	public static String bugreport_prefs_logs_maxfiles;
	public static String bugreport_prefs_logs_maxfiles_error;

	public static String bugreport_prefs_smtp_title;
	public static String bugreport_prefs_smtp_auth;
	public static String bugreport_prefs_smtp_tls;
	public static String bugreport_prefs_smtp_host;
	public static String bugreport_prefs_smtp_port;
	public static String bugreport_prefs_smtp_user;
	public static String bugreport_prefs_smtp_password;
	public static String bugreport_prefs_smtp_destination;

	public static String bugreport_send_dialog_title;
	public static String bugreport_send_dialog_message;
	public static String bugreport_send_dialog_message_hint;
	public static String bugreport_send_dialog_success;
	public static String bugreport_send_dialog_sending;
	public static String bugreport_send_error;

	public static String dashboard_view_title;
	public static String dashboard_form_title;
	public static String dashboard_launch_title;
	public static String dashboard_launch_description;
	public static String dashboard_library_title;
	public static String dashboard_library_message;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}

	/**
	 * Bind the given message's substitution locations with the given string values.
	 * 
	 * @param message the message to be manipulated
	 * @param bindings An array of objects to be inserted into the message
	 * @return the manipulated String
	 * @throws IllegalArgumentException if the text appearing within curly braces in the given message does not map to
	 *         an integer
	 */
	public static String binds(String message, Object... objects) {
		return bind(message, objects);
	}
}
