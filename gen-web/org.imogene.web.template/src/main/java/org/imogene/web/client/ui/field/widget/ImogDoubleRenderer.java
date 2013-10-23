package org.imogene.web.client.ui.field.widget;

import org.imogene.web.client.util.NumericUtil;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.text.shared.Renderer;

/**
 * A localized renderer based on {@link NumberFormat#getDecimalFormat}.
 */
public class ImogDoubleRenderer extends AbstractRenderer<Double> {
	private static ImogDoubleRenderer INSTANCE;

	/**
	 * Returns the instance.
	 */
	public static Renderer<Double> instance() {
		if (INSTANCE == null) {
			INSTANCE = new ImogDoubleRenderer();
		}
		return INSTANCE;
	}

	protected ImogDoubleRenderer() {
	}

	public String render(Double object) {
		if (null == object) {
			return "";
		}
		return NumericUtil.parseToString(object);
	}
}