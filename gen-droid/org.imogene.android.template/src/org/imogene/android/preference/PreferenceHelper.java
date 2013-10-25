package org.imogene.android.preference;

import java.util.Calendar;
import java.util.Date;

import org.imogene.android.Constants.Categories;
import org.imogene.android.template.R;
import org.imogene.android.util.base64.Base64;
import org.imogene.android.util.encryption.EncryptionManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceHelper {

	public static SharedPreferences getSharedPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	public static Date getRealTime(Context context) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis() - getNtpOffset(context));
		return calendar.getTime();
	}

	public static long getNtpOffset(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getLong(
				context.getString(R.string.ig_ntp_offset_key), 0);
	}

	public static String getNtpServerUrl(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(
				context.getString(R.string.ig_ntp_server_key), "%Android_sntpServer%");
	}

	public static String getShortPassword(Context context) {
		return getDecryptedString(context, context.getString(R.string.ig_sync_shortpw_key));
	}

	public static String getHardwareId(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(
				context.getString(R.string.ig_sync_hardware_key), null);
	}

	public static String getServerUrl(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(
				context.getString(R.string.ig_sync_server_url_key), null);
	}

	public static String getWebServiceUrl(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(
				context.getString(R.string.ig_webservice_url_key), null);
	}

	public static boolean getSynchronizationStatus(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(
				context.getString(R.string.ig_sync_on_off_key), false);
	}

	public static long getSynchronizationPeriod(Context context) {
		return Long.parseLong(PreferenceManager.getDefaultSharedPreferences(context).getString(
				context.getString(R.string.ig_sync_time_key), "15"));
	}

	public static boolean getSynchronizationBidirectional(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(
				context.getString(R.string.ig_sync_bidirectional_key), true);
	}

	public static boolean isDebugActive(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(
				context.getString(R.string.ig_debug_save_key), false);
	}

	public static String getEditionCategory(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(
				context.getString(R.string.ig_app_wizard_key), false) ? Categories.CATEGORY_WIZARD
				: Categories.CATEGORY_CLASSIC;
	}

	public static String getSyncLogin(Context context) {
		return getDecryptedString(context, context.getString(R.string.ig_sync_login_key));
	}

	public static String getSyncPassword(Context context) {
		return getDecryptedString(context, context.getString(R.string.ig_sync_password_key));
	}

	public static String getSyncRoles(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(
				context.getString(R.string.ig_sync_roles_key), null);
	}

	public static boolean isMultilogin(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(
				context.getString(R.string.ig_multilogin), false);
	}

	public static String getCurrentLogin(Context context) {
		if (isMultilogin(context)) {
			String login = getDecryptedString(context, context.getString(R.string.ig_current_login_key));
			return login != null ? login : getSyncLogin(context);
		} else {
			return getSyncLogin(context);
		}
	}

	public static String getCurrentRoles(Context context) {
		if (isMultilogin(context)) {
			String roles = PreferenceManager.getDefaultSharedPreferences(context).getString(
					context.getString(R.string.ig_current_roles_key), null);
			return roles != null ? roles : getSyncRoles(context);
		} else {
			return getSyncRoles(context);
		}
	}

	private static String getDecryptedString(Context context, String key) {
		String enc = PreferenceManager.getDefaultSharedPreferences(context).getString(key, null);
		return decrypt(context, enc);
	}

	private static String decrypt(Context context, String value) {
		if (value != null)
			return new String(EncryptionManager.getInstance(context).decrypt(Base64.decodeBase64(value.getBytes())));
		return null;
	}

	public static boolean isSetAdmin(Context context) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		return sp.contains(context.getString(R.string.ig_admin_login_key))
				&& sp.contains(context.getString(R.string.ig_admin_password_key))
				&& sp.contains(context.getString(R.string.ig_admin_roles_key));
	}

	public static boolean isAdmin(Context context, String login, String password) {
		if (login != null & password != null & isSetAdmin(context)) {
			String adminLogin = getDecryptedString(context, context.getString(R.string.ig_admin_login_key));
			String adminPassword = getDecryptedString(context, context.getString(R.string.ig_admin_password_key));
			return login.equals(adminLogin) && password.equals(adminPassword);
		}
		return false;
	}

	public static String getAdminRoles(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(
				context.getString(R.string.ig_admin_roles_key), null);
	}

	public static boolean isHttpAuthenticated(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(
				context.getString(R.string.ig_http_authentication_key), true);
	}

	public static String getPushHost(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(
				context.getString(R.string.ig_push_host_key), null);
	}

	public static int getPushPort(Context context) {
		return Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(context).getString(
				context.getString(R.string.ig_push_port_key), "0"));
	}

	public static boolean isPushEnabled(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(
				context.getString(R.string.ig_push_enabled_key), false);
	}

	public static boolean isPushSsl(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(
				context.getString(R.string.ig_push_ssl_key), false);
	}

}
