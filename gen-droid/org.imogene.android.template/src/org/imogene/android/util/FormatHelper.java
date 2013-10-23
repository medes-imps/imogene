package org.imogene.android.util;

import java.text.DateFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.imogene.android.template.R;

import android.content.Context;
import android.location.Location;

/**
 * Contains static methods to format values to/from string representation or to
 * human readable string.
 * 
 * @author MEDES-IMPS
 * 
 */
public class FormatHelper {

	private static final DecimalFormatSymbols DFS = new DecimalFormatSymbols();

	static {
		DFS.setDecimalSeparator('.');
	}

	private static DateFormat DATE_FORMAT = DateFormat.getDateInstance();
	private static DateFormat DATI_FORMAT = DateFormat.getDateTimeInstance();
	private static DateFormat TIME_FORMAT = DateFormat.getTimeInstance();

	private static final SimpleDateFormat CONSTRAINT_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
	private static final SimpleDateFormat CONSTRAINT_DATI_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
	private static final SimpleDateFormat CONSTRAINT_TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.getDefault());

	/**
	 * Used to update the application {@link DateFormat}s when changing device
	 * configuration (ie: Locale).
	 */
	public static void updateFormats() {
		DATE_FORMAT = DateFormat.getDateInstance(DateFormat.FULL);
		DATI_FORMAT = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
		TIME_FORMAT = DateFormat.getTimeInstance(DateFormat.FULL);
	}

	/**
	 * Convert a {@link Date} representing a date to a string representation.
	 * 
	 * @param time a {@link Date} to format as a date.
	 * @return the formatted string.
	 */
	public static String displayDate(Date time) {
		if (time == null) {
			return null;
		}
		return DATE_FORMAT.format(time);
	}

	/**
	 * Convert a {@link Date} representing a date & time to a string
	 * representation.
	 * 
	 * @param time a {@link Date} to format as a date & time.
	 * @return the formatted string.
	 */
	public static String displayDateTime(Date time) {
		if (time == null) {
			return null;
		}
		return DATI_FORMAT.format(time);
	}

	/**
	 * Convert a {@link Date} representing a time to a string representation.
	 * 
	 * @param time a {@link Date} to format as a time.
	 * @return The formatted string.
	 */
	public static String displayTime(Date time) {
		if (time == null) {
			return null;
		}
		return TIME_FORMAT.format(time);
	}

	/**
	 * Convert a {@link Location} to a human readable string representation.
	 * 
	 * @param context The current context of the application.
	 * @param location The location to represent.
	 * @return The formatted string.
	 */
	public static String displayLocation(Context context, Location location) {
		return context.getString(R.string.ig_location_format, location.getLatitude(), location.getLongitude());
	}

	/**
	 * Parses the specified string as a signed decimal long value.
	 * 
	 * @param str Parses the specified string as a signed decimal long value.
	 * @return a {@link Long} instance containing the long value represented by
	 *         string or {@code null}.
	 */
	public static Long toLong(String str) {
		try {
			return Long.valueOf(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Parses the specified string as a {@link Date}. The string must be a
	 * number of milliseconds value representing a date.
	 * 
	 * @param str Parses the specified string as a signed decimal long value.
	 * @return a {@link Date} instance containing the date value represented by
	 *         string or {@code null}.
	 */
	public static Date toDate(String str) {
		Long time = toLong(str);
		if (time != null) {
			return new Date(time);
		}
		return null;
	}

	/**
	 * Parses the specified string as a float value.
	 * 
	 * @param str the string representation of a float value.
	 * @return a {@link Float} instance containing the float value represented
	 *         by string or {@code null}.
	 */
	public static Float toFloat(String str) {
		try {
			return Float.valueOf(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Parses the specified string as a signed decimal integer value.
	 * 
	 * @param str the string representation of an integer value.
	 * @return an {@link Integer} instance containing the integer value
	 *         represented by the string or {@code null}.
	 */
	public static Integer toInteger(String str) {
		try {
			return Integer.valueOf(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Parses the specified string as a boolean value.
	 * 
	 * @param str the string representation of a boolean value.
	 * @return {@code null} if the string is {@code null}, {@link Boolean#TRUE}
	 *         if string is equal to "true" using case insensitive comparison,
	 *         {@link Boolean#FALSE} otherwise.
	 */
	public static Boolean toBoolean(String str) {
		if (str == null) {
			return null;
		} else {
			return Boolean.valueOf(str);
		}
	}

	/**
	 * Parses the specified string as a double value.
	 * 
	 * @param str the string representation of a double value.
	 * @return a {@link Double} instance containing the double value represented
	 *         by string or {@code null}.
	 */
	public static Double toDouble(String str) {
		try {
			return Double.valueOf(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Parses a date from the specified string using the format
	 * {@code dd/MM/yyyy}.
	 * 
	 * @param str the string to parse.
	 * @return the {@link Date} resulting from the parsing or {@code null}.
	 */
	public static Date readDate(String str) {
		try {
			return CONSTRAINT_DATE_FORMAT.parse(str);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Parses a date from the specified string using the format
	 * {@code dd/MM/yyyy HH:mm}.
	 * 
	 * @param str the string to parse.
	 * @return the {@link Date} resulting from the parsing or {@code null}.
	 */
	public static Date readDateTime(String str) {
		try {
			return CONSTRAINT_DATI_FORMAT.parse(str);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Parses a date from the specified string using the format {@code HH:mm}.
	 * 
	 * @param str The string to parse.
	 * @return The {@link Date} resulting from the parsing or {@code null}.
	 */
	public static Date readTime(String str) {
		try {
			return CONSTRAINT_TIME_FORMAT.parse(str);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Parses a {@link Location} from the specified string which must follow the
	 * format {@code "latitude;longitude"}.
	 * 
	 * @param str The string to parse.
	 * @return The {@link Location} resulting from the parsing or {@code null}
	 *         if the string could not be parsed.
	 */
	public static Location readLocation(String str) {
		if (str != null) {
			String[] values = str.split(";");
			if (values.length > 1) {
				Double latitude = FormatHelper.toDouble(values[0]);
				Double longitude = FormatHelper.toDouble(values[1]);
				if (latitude != null && longitude != null) {
					Location location = new Location("gps");
					location.setLatitude(latitude);
					location.setLongitude(longitude);
					return location;
				}
			}
		}
		return null;
	}

}
