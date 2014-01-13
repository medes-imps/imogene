package org.imogene.web.client.ui.field.widget;

import java.text.ParseException;

import org.imogene.web.client.util.NumericUtil;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.text.shared.Parser;

/**
 * A localized parser based on {@link NumberFormat#getDecimalFormat}.
 */
public class ImogDoubleParser implements Parser<Double> {

	private static ImogDoubleParser INSTANCE;

	/**
	 * Returns the instance of the no-op renderer.
	 */
	public static Parser<Double> instance() {
		if (INSTANCE == null) {
			INSTANCE = new ImogDoubleParser();
		}
		return INSTANCE;
	}

	protected ImogDoubleParser() {
	}

	public Double parse(CharSequence object) throws ParseException {
		if ("".equals(object.toString())) {
			return null;
		}
		try {
			Double result = NumericUtil.parseToDouble(object.toString());
			return result;
		} catch (NumberFormatException e) {
			throw new ParseException(e.getMessage(), 0);
		}
	}
}