package org.imogene.android.util.field;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FieldPattern {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	private static final SimpleDateFormat DATI_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

	/**
	 * Validation test for floats
	 * 
	 * @param pattern the validation pattern
	 * @param value the value to test
	 * @return true if the test matches
	 */
	public static boolean matchesInt(String pattern, int value) {
		try {
			if (pattern.startsWith("<=")) {
				String integer = pattern.substring(2);
				return value <= Integer.parseInt(integer);
			}

			if (pattern.startsWith("<")) {
				String integer = pattern.substring(1);
				return value < Integer.parseInt(integer);
			}

			if (pattern.startsWith(">=")) {
				String integer = pattern.substring(2);
				return value >= Integer.parseInt(integer);
			}

			if (pattern.startsWith(">")) {
				String integer = pattern.substring(1);
				return value > Integer.parseInt(integer);
			}

			if (pattern.startsWith("!=")) {
				String integer = pattern.substring(2);
				return value != Integer.parseInt(integer);
			}

			if (pattern.startsWith("==")) {
				String integer = pattern.substring(1);
				return value == Integer.parseInt(integer);
			}

			if (pattern.contains(";")) {
				String[] integers = pattern.split(";");
				return Integer.parseInt(integers[0]) < value && value < Integer.parseInt(integers[1]);
			}
		} catch (Exception e) {
			return false;
		}

		return false;
	}

	/**
	 * Validation test for floats
	 * 
	 * @param pattern the validation pattern
	 * @param value the value to test
	 * @return true if the test matches
	 */
	public static boolean matchesFloat(String pattern, float value) {
		try {
			if (pattern.startsWith("<=")) {
				String number = pattern.substring(2);
				return value <= Float.parseFloat(number);
			}

			if (pattern.startsWith("<")) {
				String number = pattern.substring(1);
				return value < Float.parseFloat(number);
			}

			if (pattern.startsWith(">=")) {
				String number = pattern.substring(2);
				return value >= Float.parseFloat(number);
			}

			if (pattern.startsWith(">")) {
				String number = pattern.substring(1);
				return value > Float.parseFloat(number);
			}

			if (pattern.startsWith("!=")) {
				String number = pattern.substring(2);
				return value != Float.parseFloat(number);
			}

			if (pattern.startsWith("==")) {
				String number = pattern.substring(2);
				return value == Float.parseFloat(number);
			}

			if (pattern.contains(";")) {
				String[] integers = pattern.split(";");
				return Float.parseFloat(integers[0]) < value && value < Float.parseFloat(integers[1]);
			}
		} catch (Exception ex) {
			return false;
		}

		return false;
	}

	/**
	 * Validation test for date
	 * 
	 * @param pattern the validation pattern
	 * @param value the value to test
	 * @return true if the test matches
	 */
	public static boolean matchesDate(String pattern, Date value) {
		return matchesDates(DATE_FORMAT, pattern, value);
	}

	/**
	 * Validation test for time
	 * 
	 * @param pattern the validation pattern
	 * @param value the value to test
	 * @return true if the test matches
	 */
	public static boolean matchesTime(String pattern, Date value) {
		return matchesDates(TIME_FORMAT, pattern, value);
	}
	
	/**
	 * Validation test for date and time
	 * 
	 * @param pattern the validation pattern
	 * @param value the value to test
	 * @return true if the test matches
	 */
	public static boolean matchesDateTime(String pattern, Date value) {
		return matchesDates(DATI_FORMAT, pattern, value);
	}

	/**
	 * Validation test for date time
	 * 
	 * @param format the date formatter to use
	 * @param pattern the validation pattern
	 * @param value the value to test
	 * @return true if the test matches
	 */
	public static boolean matchesDates(DateFormat format, String pattern, Date value) {
		try {
			if (pattern.startsWith("<=")) {
				return value.compareTo(format.parse(pattern.substring(2))) <= 0;
			}

			if (pattern.startsWith("<")) {
				return value.before(format.parse(pattern.substring(1)));
			}

			if (pattern.startsWith(">=")) {
				return value.compareTo(format.parse(pattern.substring(2))) >= 0;
			}

			if (pattern.startsWith(">")) {
				return value.after(format.parse(pattern.substring(1)));
			}

			if (pattern.startsWith("!=")) {
				return value.compareTo(format.parse(pattern.substring(2))) != 0;
			}

			if (pattern.startsWith("==")) {
				return value.compareTo(format.parse(pattern.substring(2))) == 0;
			}

			if (pattern.contains(";")) {
				String[] integers = pattern.split(";");
				Date min = format.parse(integers[0]);
				Date max = format.parse(integers[1]);
				return value.after(min) && value.before(max);
			}
		} catch (Exception ex) {
			return false;
		}

		return false;
	}

}
