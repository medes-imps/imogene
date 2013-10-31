package org.imogene.android.preference;

import java.util.Calendar;
import java.util.Date;

import org.imogene.android.util.base64.Base64;
import org.imogene.android.util.encryption.EncryptionManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {

	private static final String MULTILOGIN_ENABLED = "multiloginEnabled";
	private static final String ADMIN_LOGIN = "adminLogin";
	private static final String ADMIN_PASSWORD = "adminPassword";
	private static final String ADMIN_ROLES = "adminRoles";
	private static final String CURRENT_LOGIN = "currentLogin";
	private static final String CURRENT_ROLES = "currentRoles";
	private static final String SYNC_LOGIN = "syncLogin";
	private static final String SYNC_PASSWORD = "syncPassword";
	private static final String SYNC_ROLES = "syncRoles";
	private static final String SYNC_TERMINAL = "syncTerminal";
	private static final String SYNC_SERVER = "syncServer";
	private static final String SYNC_PERIOD = "syncPeriod";
	private static final String SYNC_ENABLED = "syncEnabled";
	private static final String SYNC_BIDIRECTIONAL_ENABLED = "syncBidirectionnalEnabled";
	private static final String SHORT_PASSWORD = "shortPassword";
	private static final String SYNC_ONSAVE_ENABLED = "syncOnSaveEnabled";
	private static final String WIZARD_ENABLED = "wizardEnabled";
	private static final String DEBUG_ENABLED = "debugEnabled";
	private static final String NTP_HOST = "ntpHost";
	private static final String NTP_OFFSET = "ntpOffset";
	private static final String WS_SERVER = "webServiceServer";
	private static final String HTTP_AUTHENTICATION_ENABLED = "httpAuthenticationEnabled";
	private static final String UPDATE_SERVER = "updateServer";
	private static final String PUSH_HOST = "pushHost";
	private static final String PUSH_PORT = "pushPort";
	private static final String PUSH_ENABLED = "pushEnabled";
	private static final String PUSH_SSL_ENABLED = "pushSslEnabled";

	private static Preferences sPreferences;

	private final SharedPreferences mSharedPreferences;

	private Preferences(Context context) {
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	/**
	 * TODO need to think about what happens if this gets GCed along with the Activity that initialized it. Do we lose
	 * ability to read Preferences in further Activities? Maybe this should be stored in the Application context.
	 */
	public static synchronized Preferences getPreferences(Context context) {
		if (sPreferences == null) {
			sPreferences = new Preferences(context);
		}
		return sPreferences;
	}

	public static SharedPreferences getSharedPreferences(Context context) {
		return getPreferences(context).mSharedPreferences;
	}

	public static Date getRealTime(Context context) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis() - getNtpOffset(context));
		return calendar.getTime();
	}

	public static long getNtpOffset(Context context) {
		return getPreferences(context).mSharedPreferences.getLong(NTP_OFFSET, 0);
	}

	public static void setNtpOffset(Context context, long value) {
		getPreferences(context).mSharedPreferences.edit().putLong(NTP_OFFSET, value).commit();
	}

	public static String getNtpHost(Context context) {
		return getPreferences(context).mSharedPreferences.getString(NTP_HOST, "%Android_sntpServer%");
	}

	public static void setNtpHost(Context context, String value) {
		getPreferences(context).mSharedPreferences.edit().putString(NTP_HOST, value).commit();
	}

	public static String getShortPassword(Context context) {
		return getDecryptedString(context, SHORT_PASSWORD);
	}

	public static void setShortPassword(Context context, String value) {
		setEncryptedString(context, SHORT_PASSWORD, value);
	}

	public static String getSyncTerminal(Context context) {
		return getPreferences(context).mSharedPreferences.getString(SYNC_TERMINAL, null);
	}

	public static void setSyncTerminal(Context context, String value) {
		getPreferences(context).mSharedPreferences.edit().putString(SYNC_TERMINAL, value).commit();
	}

	public static String getSyncServer(Context context) {
		return getPreferences(context).mSharedPreferences.getString(SYNC_SERVER, null);
	}

	public static void setSyncServer(Context context, String value) {
		getPreferences(context).mSharedPreferences.edit().putString(SYNC_SERVER, value).commit();
	}

	public static String getWebServiceServer(Context context) {
		return getPreferences(context).mSharedPreferences.getString(WS_SERVER, null);
	}

	public static boolean isSyncEnabled(Context context) {
		return getPreferences(context).mSharedPreferences.getBoolean(SYNC_ENABLED, false);
	}

	public static long getSyncPeriod(Context context) {
		return Long.parseLong(getPreferences(context).mSharedPreferences.getString(SYNC_PERIOD, "15"));
	}

	public static boolean isSyncBirdirectionnalEnabled(Context context) {
		return getPreferences(context).mSharedPreferences.getBoolean(SYNC_BIDIRECTIONAL_ENABLED, true);
	}

	public static boolean isSyncOnSaveEnabled(Context context) {
		return getPreferences(context).mSharedPreferences.getBoolean(SYNC_ONSAVE_ENABLED, true);
	}

	public static boolean isDebugEnabled(Context context) {
		return getPreferences(context).mSharedPreferences.getBoolean(DEBUG_ENABLED, false);
	}

	public static boolean isWizardEnabled(Context context) {
		return getPreferences(context).mSharedPreferences.getBoolean(WIZARD_ENABLED, false);
	}

	public static String getSyncLogin(Context context) {
		return getDecryptedString(context, SYNC_LOGIN);
	}

	public static void setSyncLogin(Context context, String value) {
		setEncryptedString(context, SYNC_LOGIN, value);
	}

	public static String getSyncPassword(Context context) {
		return getDecryptedString(context, SYNC_PASSWORD);
	}

	public static void setSyncPassword(Context context, String value) {
		setEncryptedString(context, SYNC_PASSWORD, value);
	}

	public static String getSyncRoles(Context context) {
		return getPreferences(context).mSharedPreferences.getString(SYNC_ROLES, null);
	}

	public static void setSyncRoles(Context context, String value) {
		getPreferences(context).mSharedPreferences.edit().putString(SYNC_ROLES, value).commit();
	}

	public static boolean isMultiloginEnabled(Context context) {
		return getPreferences(context).mSharedPreferences.getBoolean(MULTILOGIN_ENABLED, false);
	}

	public static void setMultiloginEnabled(Context context, boolean value) {
		getPreferences(context).mSharedPreferences.edit().putBoolean(MULTILOGIN_ENABLED, value).commit();
	}

	public static String getCurrentLogin(Context context) {
		if (isMultiloginEnabled(context)) {
			String login = getDecryptedString(context, CURRENT_LOGIN);
			return login != null ? login : getSyncLogin(context);
		} else {
			return getSyncLogin(context);
		}
	}

	public static void setCurrentLogin(Context context, String value) {
		setEncryptedString(context, CURRENT_LOGIN, value);
	}

	public static void clearCurrentLogin(Context context) {
		getPreferences(context).mSharedPreferences.edit().remove(CURRENT_LOGIN).commit();
	}

	public static String getCurrentRoles(Context context) {
		if (isMultiloginEnabled(context)) {
			String roles = getPreferences(context).mSharedPreferences.getString(CURRENT_ROLES, null);
			return roles != null ? roles : getSyncRoles(context);
		} else {
			return getSyncRoles(context);
		}
	}

	public static void setCurrentRoles(Context context, String value) {
		getPreferences(context).mSharedPreferences.edit().putString(CURRENT_ROLES, value).commit();
	}

	public static void clearCurrentRoles(Context context) {
		getPreferences(context).mSharedPreferences.edit().remove(CURRENT_ROLES).commit();
	}

	public static boolean isSetAdmin(Context context) {
		SharedPreferences sp = getPreferences(context).mSharedPreferences;
		return sp.contains(ADMIN_LOGIN) && sp.contains(ADMIN_PASSWORD) && sp.contains(ADMIN_ROLES);
	}

	public static boolean isAdmin(Context context, String login, String password) {
		if (login != null & password != null & isSetAdmin(context)) {
			String adminLogin = getDecryptedString(context, ADMIN_LOGIN);
			String adminPassword = getDecryptedString(context, ADMIN_PASSWORD);
			return login.equals(adminLogin) && password.equals(adminPassword);
		}
		return false;
	}
	
	public static void setAdminLogin(Context context, String value) {
		setEncryptedString(context, ADMIN_LOGIN, value);
	}
	
	public static void setAdminPassword(Context context, String value) {
		setEncryptedString(context, ADMIN_PASSWORD, value);
	}

	public static String getAdminRoles(Context context) {
		return getPreferences(context).mSharedPreferences.getString(ADMIN_ROLES, null);
	}
	
	public static void setAdminRoles(Context context, String value) {
		getPreferences(context).mSharedPreferences.edit().putString(ADMIN_ROLES, value).commit();
	}

	public static boolean isHttpAuthenticationEnabled(Context context) {
		return getPreferences(context).mSharedPreferences.getBoolean(HTTP_AUTHENTICATION_ENABLED, true);
	}

	public static String getUpdateServer(Context context) {
		return getPreferences(context).mSharedPreferences.getString(UPDATE_SERVER, null);
	}

	public static String getPushHost(Context context) {
		return getPreferences(context).mSharedPreferences.getString(PUSH_HOST, null);
	}

	public static int getPushPort(Context context) {
		return Integer.parseInt(getPreferences(context).mSharedPreferences.getString(PUSH_PORT, "0"));
	}

	public static boolean isPushEnabled(Context context) {
		return getPreferences(context).mSharedPreferences.getBoolean(PUSH_ENABLED, false);
	}

	public static boolean isPushSslEnabled(Context context) {
		return getPreferences(context).mSharedPreferences.getBoolean(PUSH_SSL_ENABLED, false);
	}

	private static String getDecryptedString(Context context, String key) {
		String enc = getPreferences(context).mSharedPreferences.getString(key, null);
		return decrypt(context, enc);
	}

	private static void setEncryptedString(Context context, String key, String value) {
		getPreferences(context).mSharedPreferences.edit().putString(key, encrypt(context, value)).commit();
	}

	private static String decrypt(Context context, String value) {
		if (value != null) {
			return new String(EncryptionManager.getInstance(context).decrypt(Base64.decodeBase64(value.getBytes())));
		}
		return null;
	}

	private static String encrypt(Context context, String value) {
		if (value != null) {
			return new String(Base64.encodeBase64(EncryptionManager.getInstance(context).encrypt(value.getBytes())));
		}
		return null;
	}

}
