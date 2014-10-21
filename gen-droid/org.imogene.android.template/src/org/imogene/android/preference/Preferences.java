package org.imogene.android.preference;

import java.util.Date;

import org.apache.commons.codec.binary.Base64;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import fr.medes.android.util.encryption.EncryptionManager;

public class Preferences {

	public static final String MULTILOGIN_ENABLED = "multiloginEnabled";
	public static final String ADMIN_LOGIN = "adminLogin";
	public static final String ADMIN_PASSWORD = "adminPassword";
	public static final String CURRENT_LOGIN = "currentLogin";
	public static final String SYNC_LOGIN = "syncLogin";
	public static final String SYNC_PASSWORD = "syncPassword";
	public static final String SYNC_TERMINAL = "syncTerminal";
	public static final String SYNC_SERVER = "syncServer";
	public static final String SYNC_PERIOD = "syncPeriod";
	public static final String SYNC_ENABLED = "syncEnabled";
	public static final String SYNC_CHECK = "syncCheck";
	public static final String SYNC_BIDIRECTIONAL_ENABLED = "syncBidirectionnalEnabled";
	public static final String SHORT_PASSWORD = "shortPassword";
	public static final String SYNC_ONSAVE_ENABLED = "syncOnSaveEnabled";
	public static final String WIZARD_ENABLED = "wizardEnabled";
	public static final String DEBUG_ENABLED = "debugEnabled";
	public static final String NTP_HOST = "ntpHost";
	public static final String NTP_OFFSET = "ntpOffset";
	public static final String NTP_LASTUPDATE = "ntpLastUpdate";
	public static final String WS_SERVER = "webServiceServer";
	public static final String UPDATE_SERVER = "updateServer";
	public static final String UPDATE_AVAILABLE = "updateAvailable";
	public static final String PUSH_HOST = "pushHost";
	public static final String PUSH_PORT = "pushPort";
	public static final String PUSH_ENABLED = "pushEnabled";
	public static final String PUSH_SSL_ENABLED = "pushSslEnabled";
	public static final String PUSH_STARTED = "pushStarted";
	public static final String PUSH_RETRY_INTERVAL = "pushRetryInterval";
	public static final String MAP_AUTOMATIC_CACHE = "mapAutomaticCache";
	public static final String MAP_CLEAR_CACHE = "mapClearCache";
	public static final String MAP_PRECACHE_AREA = "mapPrecacheArea";
	public static final String DELETE_DATABASE = "deleteDatabase";
	public static final String DELETE_HISTORY = "deleteHistory";

	private static Preferences sPreferences;

	private final SharedPreferences mSharedPreferences;
	private final EncryptionManager mEncryptionManager;

	private Preferences(Context context) {
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		mEncryptionManager = EncryptionManager.getInstance();
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

	public long getNtpOffset() {
		return mSharedPreferences.getLong(NTP_OFFSET, 0);
	}

	public void setNtpOffset(long value) {
		mSharedPreferences.edit().putLong(NTP_OFFSET, value)
				.putString(NTP_LASTUPDATE, new Date(System.currentTimeMillis() + value).toLocaleString()).commit();
	}

	public String getNtpHost() {
		return mSharedPreferences.getString(NTP_HOST, "%Android_sntpServer%");
	}

	public void setNtpHost(String value) {
		mSharedPreferences.edit().putString(NTP_HOST, value).commit();
	}

	public String getShortPassword() {
		return getDecryptedString(SHORT_PASSWORD);
	}

	public void setShortPassword(String value) {
		setEncryptedString(SHORT_PASSWORD, value);
	}

	public String getSyncTerminal() {
		return mSharedPreferences.getString(SYNC_TERMINAL, null);
	}

	public void setSyncTerminal(String value) {
		mSharedPreferences.edit().putString(SYNC_TERMINAL, value).commit();
	}

	public String getSyncServer() {
		return mSharedPreferences.getString(SYNC_SERVER, null);
	}

	public void setSyncServer(String value) {
		mSharedPreferences.edit().putString(SYNC_SERVER, value).commit();
	}

	public String getWebServiceServer() {
		return mSharedPreferences.getString(WS_SERVER, null);
	}

	public boolean isSyncEnabled() {
		return mSharedPreferences.getBoolean(SYNC_ENABLED, false);
	}

	public long getSyncPeriod() {
		return Long.parseLong(mSharedPreferences.getString(SYNC_PERIOD, "15"));
	}

	public boolean isSyncBirdirectionnalEnabled() {
		return mSharedPreferences.getBoolean(SYNC_BIDIRECTIONAL_ENABLED, true);
	}

	public boolean isSyncOnSaveEnabled() {
		return mSharedPreferences.getBoolean(SYNC_ONSAVE_ENABLED, true);
	}

	public boolean isDebugEnabled() {
		return mSharedPreferences.getBoolean(DEBUG_ENABLED, false);
	}

	public boolean isWizardEnabled() {
		return mSharedPreferences.getBoolean(WIZARD_ENABLED, false);
	}

	public String getSyncLogin() {
		return getDecryptedString(SYNC_LOGIN);
	}

	public void setSyncLogin(String value) {
		setEncryptedString(SYNC_LOGIN, value);
	}

	public String getSyncPassword() {
		return getDecryptedString(SYNC_PASSWORD);
	}

	public void setSyncPassword(String value) {
		setEncryptedString(SYNC_PASSWORD, value);
	}

	public boolean isMultiloginEnabled() {
		return mSharedPreferences.getBoolean(MULTILOGIN_ENABLED, false);
	}

	public void setMultiloginEnabled(boolean value) {
		mSharedPreferences.edit().putBoolean(MULTILOGIN_ENABLED, value).commit();
	}

	public String getCurrentLogin() {
		if (isMultiloginEnabled()) {
			String login = getDecryptedString(CURRENT_LOGIN);
			return login != null ? login : getSyncLogin();
		} else {
			return getSyncLogin();
		}
	}

	public void setCurrentLogin(String value) {
		setEncryptedString(CURRENT_LOGIN, value);
	}

	public void clearCurrentLogin() {
		mSharedPreferences.edit().remove(CURRENT_LOGIN).commit();
	}

	public boolean isSetAdmin() {
		SharedPreferences sp = mSharedPreferences;
		return sp.contains(ADMIN_LOGIN) && sp.contains(ADMIN_PASSWORD);
	}

	public boolean isAdmin(String login, String password) {
		if (login != null & password != null & isSetAdmin()) {
			String adminLogin = getDecryptedString(ADMIN_LOGIN);
			String adminPassword = getDecryptedString(ADMIN_PASSWORD);
			return login.equals(adminLogin) && password.equals(adminPassword);
		}
		return false;
	}

	public void setAdminLogin(String value) {
		setEncryptedString(ADMIN_LOGIN, value);
	}

	public void setAdminPassword(String value) {
		setEncryptedString(ADMIN_PASSWORD, value);
	}

	public String getUpdateServer() {
		return mSharedPreferences.getString(UPDATE_SERVER, null);
	}

	public String getPushHost() {
		return mSharedPreferences.getString(PUSH_HOST, null);
	}

	public int getPushPort() {
		return Integer.parseInt(mSharedPreferences.getString(PUSH_PORT, "0"));
	}

	public boolean isPushEnabled() {
		return mSharedPreferences.getBoolean(PUSH_ENABLED, false);
	}

	public boolean isPushSslEnabled() {
		return mSharedPreferences.getBoolean(PUSH_SSL_ENABLED, false);
	}

	public boolean isPushStarted() {
		return mSharedPreferences.getBoolean(PUSH_STARTED, false);
	}

	public void setPushStarted(boolean value) {
		mSharedPreferences.edit().putBoolean(PUSH_STARTED, value).commit();
	}

	public long getPushRetryInterval(long defaultValue) {
		return mSharedPreferences.getLong(PUSH_RETRY_INTERVAL, defaultValue);
	}

	public void setPushRetryInterval(long value) {
		mSharedPreferences.edit().putLong(PUSH_RETRY_INTERVAL, value).commit();
	}

	private String getDecryptedString(String key) {
		String enc = mSharedPreferences.getString(key, null);
		return decrypt(enc);
	}

	private void setEncryptedString(String key, String value) {
		mSharedPreferences.edit().putString(key, encrypt(value)).commit();
	}

	private String decrypt(String value) {
		if (value != null) {
			return new String(mEncryptionManager.decrypt(Base64.decodeBase64(value.getBytes())));
		}
		return null;
	}

	private String encrypt(String value) {
		if (value != null) {
			return new String(Base64.encodeBase64(mEncryptionManager.encrypt(value.getBytes())));
		}
		return null;
	}

}
