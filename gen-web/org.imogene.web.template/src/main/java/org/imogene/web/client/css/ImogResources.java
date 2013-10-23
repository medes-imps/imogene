package org.imogene.web.client.css;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * @author MEDES-IMPS
 */
public interface ImogResources extends ClientBundle {

	// public ImogResources INSTANCE = GWT.create(ImogResources.class);

	/**
	 * The styles used in this project.
	 */
	@Source(ImogStyle.IMOG_CSS)
	ImogStyle imogStyle();

	/**
	 * 
	 * @author MEDES-IMPS
	 */
	public interface ImogStyle extends CssResource {

		String IMOG_CSS = "ImogCss.css";

		/**
		 * Applied to text colors
		 * @return
		 */
		String textColor();

		/**
		 * Applied to the page background
		 * @return
		 */
		String pageBackground();

		/**
		 * Applied to panel backgrounds
		 * @return
		 */
		String panelBackground();

		/**
		 * Applied to panel borders
		 * @return
		 */
		String panelBorder();

		/**
		 * Applied to panels
		 * @return
		 */
		String imogenePanel();

		/**
		 * Applied to buttons (light-grey buttons)
		 * @return
		 */
		String imogButton();
		
		String imogButtonDown();

		/**
		 * Applied to buttons of type 1 (default=blue)
		 * @return
		 */
		String imogButton1();
		
		String imogButton1Down();

		/**
		 * Applied to buttons of type 1 (default=red)
		 * @return
		 */
		String imogButton2();

		/**
		 * Applied to buttons of type 3 (default=green)
		 * @return
		 */
		String imogButton3();

		/**
		 * Applied to buttons of type 3 (default=yellow)
		 * @return
		 */
		String imogButton4();
	}
}