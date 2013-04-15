package org.imogene.web.server.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Class that helps to manipulate the date.
 * @author Medes-IMPS 
 */
public class DateUtil {
	
	private static ResourceBundle rb = ResourceBundle.getBundle("org.imogene.web.client.i18n.ImogConstants");
	
	private static SimpleDateFormat dateFormater = new SimpleDateFormat(rb.getString("format_date"));
	private static SimpleDateFormat dateTimeFormater = new SimpleDateFormat(rb.getString("format_date") +
            " " + rb.getString("format_time"));
	private static SimpleDateFormat timeFormater = new SimpleDateFormat(rb.getString("format_time"));
	
	/**
	 * Get the string representation of a date.
	 * @param date the date
	 * @return the string representation
	 */
	public static String getFormatedDate(Date date) {
		return dateFormater.format(date);		
	}
	
	/**
	 * Get the string representation of a date with time.
	 * @param date the date with time
	 * @return the string representation
	 */
	public static String getFormatedDateTime(Date datetime) {
		return dateTimeFormater.format(datetime);		
	}
	
	/**
	 * Get the string representation of time.
	 * @param date the time
	 * @return the string representation
	 */
	public static String getFormatedTime(Date time) {
		return timeFormater.format(time);		
	}
	
	/**
	 * Get a date from its string representation.
	 * @param date the string representation
	 * @return the date
	 */
	public static Date parseDate(String date) throws ParseException {
		return dateFormater.parse(date);		
	}

	/**
	 * Get a date with time from its string representation.
	 * @param datetime the string representation
	 * @return the date with time
	 */
	public static Date parseDateTime(String datetime) throws ParseException{
		return dateTimeFormater.parse(datetime);		
	}
	
	/**
	 * Get a time from its string representation.
	 * @param time the string representation
	 * @return the time
	 */
	public static Date parseTime(String time) throws ParseException{
		return timeFormater.parse(time);		
	}
}
