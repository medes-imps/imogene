package org.imogene.web.client.ui.field.widget;

import org.imogene.web.client.util.NumericUtil;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.text.shared.Renderer;

/**
 * A localized renderer based on {@link NumberFormat#getDecimalFormat}.
 */
public class ImogIntRenderer extends AbstractRenderer<Integer> {
	private static ImogIntRenderer INSTANCE;

	/**
	 * Returns the instance.
	 */
	public static Renderer<Integer> instance() {
		if (INSTANCE == null) {
			INSTANCE = new ImogIntRenderer();
		}
		return INSTANCE;
	}

	protected ImogIntRenderer() {
	}

	public String render(Integer object) {
		if (null == object) {
			return "";
		}
		return NumericUtil.parseToString(object);
	}
}