package org.imogene.model.validation.constraints;

import java.text.SimpleDateFormat;

public class Tools {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	private static final SimpleDateFormat DATI_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
	
	private static final String DEP_INT_REGEX = "((<|<=|>|>=|!=|==)[+-]?\\d*)|([+-]?\\d*;[+-]?\\d*)";
	private static final String DEP_FLOAT_REGEX = "((<|<=|>|>=|!=|==)[+-]?\\d*\\.?\\d*)|([+-]?\\d*\\.?\\d*;[+-]?\\d*\\.?\\d*)";
	
	/**
	 * Test if a String starts with an upper char
	 * 
	 * @param name
	 *            The string to test
	 * @return true if the string starts with an upper char
	 */
	public static boolean checkUppercase(String name) {
		String first = name.substring(0, 1);
		return name.startsWith(first.toUpperCase());
	}

	/**
	 * Tests if a String can be converted to a Integer
	 * 
	 * @param value
	 *            value to be tested if integer
	 * @return
	 */
	public static boolean isInteger(String value) {
		try {
			Integer.valueOf(value);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Tests if a String can be converted to a Float
	 * 
	 * @param value
	 *            value to be tested if float
	 * @return
	 */
	public static boolean isFloat(String value) {
		try {
			Float.valueOf(value);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Tests if a String can be converted to a Date
	 * 
	 * @param value
	 *            value to be tested if date
	 * @return
	 */
	public static boolean isDate(String value) {
		try {
			DATE_FORMAT.parse(value);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Tests if a String can be converted to a DateTime
	 * 
	 * @param value
	 *            value to be tested if date
	 * @return
	 */
	public static boolean isDateTime(String value) {
		try {
			DATI_FORMAT.parse(value);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Tests if a String can be converted to a Time
	 * 
	 * @param value
	 *            value to be tested if date
	 * @return
	 */
	public static boolean isTime(String value) {
		try {
			TIME_FORMAT.parse(value);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Tests if a String can be converted to a Boolean
	 * 
	 * @param value
	 *            value to be tested if date
	 * @return
	 */
	public static boolean isBoolean(String value) {
		return value != null &&
			(value.toLowerCase().equals("true") ||
			 value.toLowerCase().equals("false"));
	}
	
	/**
	 * Test if a String is a correct dependency field value for an integer field
	 * @param value
	 *            value to be tested if dependency field value
	 * @return
	 */
	public static boolean isDepValueForInteger(String value) {
		return value != null ? value.matches(DEP_INT_REGEX) : false;
	}
	
	/**
	 * Test if a String is a correct dependency field value for a float field
	 * @param value
	 *            value to be tested if dependency field value
	 * @return
	 */
	public static boolean isDepValueForFloat(String value) {
		return value != null ? value.matches(DEP_FLOAT_REGEX) : false;
	}
	
	/**
	 * Test if a String is a correct dependency field value for a date field
	 * @param value
	 *            value to be tested if dependency field value
	 * @return
	 */
	public static boolean isDepValueForDate(String value) {
		if (value != null)
			if (value.contains(";")) {
				String[] dates = value.split(";");
				return dates.length != 2 ? false : isDate(dates[0]) && isDate(dates[1]);
			} else if (	value.startsWith("<=") || value.startsWith(">=") ||
						value.startsWith("==") || value.startsWith("!="))
				return isDate(value.substring(2));
			else if (value.startsWith("<") || value.startsWith(">"))
				return isDate(value.substring(1));
			else
				return false;
		else
			return false;
	}
	
	/**
	 * Test if a String is a correct dependency field value for a date time field
	 * @param value
	 *            value to be tested if dependency field value
	 * @return
	 */
	public static boolean isDepValueForDateTime(String value) {
		if (value != null)
			if (value.contains(";")) {
				String[] dates = value.split(";");
				return dates.length != 2 ? false : isDateTime(dates[0]) && isDateTime(dates[1]);
			} else if (	value.startsWith("<=") || value.startsWith(">=") ||
						value.startsWith("==") || value.startsWith("!="))
				return isDateTime(value.substring(2));
			else if (value.startsWith("<") || value.startsWith(">"))
				return isDateTime(value.substring(1));
			else
				return false;
		else
			return false;
	}
	
	/**
	 * Test if a String is a correct dependency field for a time field
	 * @param value value to be tested
	 * @return
	 */
	public static boolean isDepValueForTime(String value) {
		if (value != null)
			if (value.contains(";")) {
				String[] dates = value.split(";");
				return dates.length != 2 ? false : isTime(dates[0]) && isTime(dates[1]);
			} else if (	value.startsWith("<=") || value.startsWith(">=") ||
						value.startsWith("==") || value.startsWith("!="))
				return isTime(value.substring(2));
			else if (value.startsWith("<") || value.startsWith(">"))
				return isTime(value.substring(1));
			else
				return false;
		else
			return false;
	}
	
	/**
	 * Test if a String is a valid regular expression
	 * @param value the value to be tested
	 * @return
	 */
	public static boolean isRegex(String value) {
		try {
			"test".matches(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	
	
}
