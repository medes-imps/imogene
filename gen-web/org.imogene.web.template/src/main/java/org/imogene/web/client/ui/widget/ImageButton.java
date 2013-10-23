package org.imogene.web.client.ui.widget;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Image;

/**
 * An {@link Image} that acts as a button.
 */
public class ImageButton extends Image {
	
	
	private boolean disabled;
	private final ImageResource resDisabled;
	private final ImageResource resEnabled;
	private final String styleDisabled;
	private final String styleAbled;

	public ImageButton(ImageResource resEnabled, ImageResource resDiabled, String abledStyle, String disabledStyle) {
		super(resEnabled);
		this.resEnabled = resEnabled;
		this.resDisabled = resDiabled;
		this.styleAbled = abledStyle;
		this.styleDisabled = disabledStyle;
	}

	public boolean isDisabled() {
		return disabled;
	}

	@Override
	public void onBrowserEvent(Event event) {
		// Ignore events if disabled.
		if (disabled) {
			return;
		}

		super.onBrowserEvent(event);
	}

	public void setDisabled(boolean isDisabled) {
		if (this.disabled == isDisabled) {
			return;
		}
		this.disabled = isDisabled;
		if (disabled) {
			setResource(resDisabled);
			getElement().getParentElement().addClassName(styleDisabled);
			getElement().getParentElement().removeClassName(styleAbled);
		} else {
			setResource(resEnabled);
			getElement().getParentElement().addClassName(styleAbled);
			getElement().getParentElement().removeClassName(styleDisabled);
		}
	}
}