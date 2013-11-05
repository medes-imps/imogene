package org.imogene.web.client.util;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.datepicker.client.DateBox;

/**
 * @author MEDES-IMPS
 */
public class SimpleImogDateFormat implements DateBox.Format {

	private final DateTimeFormat dateTimeFormat;

	public SimpleImogDateFormat(DateTimeFormat dateTimeFormat) {
		this.dateTimeFormat = dateTimeFormat;
	}

	@Override
	public String format(DateBox dateBox, Date date) {
		if (date == null)
			return "";
		else
			return dateTimeFormat.format(date);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Date parse(DateBox dateBox, String text, boolean reportError) {

		Date date = null;
		try {
			if (text.length() > 0) {
				date = dateTimeFormat.parse(text);
			}
		} catch (IllegalArgumentException exception) {
			try {
				date = new Date(text);
			} catch (IllegalArgumentException e) {
				return null;
			}
		}
		return date;
	}

	@Override
	public void reset(DateBox dateBox, boolean abandon) {
	}
}
