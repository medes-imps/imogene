package org.imogene.android.update;

import org.imogene.android.template.R;

import android.content.Context;
import android.preference.PreferenceManager;

public class UpdatePreferenceHelper {

	public static String getUpdateServer(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.ig_check_server_key), null);
	}
	
	public static String getApplicationsUrl(Context context) {
		String result = getUpdateServer(context);
		if (result == null) {
			return null;
		}
		if (!result.endsWith("/")) {
			result += "/";
		}
		return result + "market.xml";
	}
	
	public static String getApplicationUrl(Context context, long id) {
		String result = getUpdateServer(context);
		if (result == null) {
			return null;
		}
		if (!result.endsWith("/")) {
			result += "/";
		}
		return result + "application/" + id + ".xml";
	}
	
	public static String getApplicationUrl(Context context, String packageName) {
		String result = getUpdateServer(context);
		if (result == null) {
			return null;
		}
		if (!result.endsWith("/")) {
			result += "/";
		}
		return result + "package/" + packageName + ".xml";
	}
	
	public static String getIconUrl(Context context, String icon) {
		String result = getUpdateServer(context);
		if (result == null) {
			return null;
		}
		if (!result.endsWith("/")) {
			result += "/";
		}
		return result + "images/" + icon;
	}
	
	public static String getApkUrl(Context context, String file) {
		String result = getUpdateServer(context);
		if (result == null) {
			return null;
		}
		if (!result.endsWith("/")) {
			result += "/";
		}
		return result + "apk/" + file;
	}
}
