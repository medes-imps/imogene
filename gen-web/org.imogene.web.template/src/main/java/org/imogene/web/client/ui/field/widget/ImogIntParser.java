package org.imogene.web.client.ui.field.widget;
import java.text.ParseException;

import org.imogene.web.client.util.NumericUtil;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.text.shared.Parser;

/**
 * A localized parser based on {@link NumberFormat#getDecimalFormat}.
 */
public class ImogIntParser implements Parser<Integer> {

  private static ImogIntParser INSTANCE;

  /**
   * Returns the instance of the no-op renderer.
   */
  public static Parser<Integer> instance() {
    if (INSTANCE == null) {
      INSTANCE = new ImogIntParser();
    }
    return INSTANCE;
  }

  protected ImogIntParser() {
  }

  public Integer parse(CharSequence object) throws ParseException {
    if ("".equals(object.toString())) {
      return null;
    }

    try {
			return NumericUtil.parseToInteger(object.toString());
    } catch (NumberFormatException e) {
      throw new ParseException(e.getMessage(), 0);
    }
  }
}