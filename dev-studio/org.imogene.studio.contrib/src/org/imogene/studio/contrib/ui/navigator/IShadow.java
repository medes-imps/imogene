package org.imogene.studio.contrib.ui.navigator;

import org.eclipse.swt.graphics.Image;

/**
 * Represents an item used as separator or group of resources.
 * This item is not backed on a real resource.
 * @author Medes-IMPS
 */
public interface IShadow {
	
	public String getLabel();
	
	public String getType();
	
	public Object[] getChildren();
	
	public boolean hasChildren();
	
	public Image getIcon();
}
