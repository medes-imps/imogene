package org.imogene.android.database;


import android.location.Location;

public interface GeoreferencedCursor extends ImogBeanCursor {
	
	public Location getGeoreference();

}
