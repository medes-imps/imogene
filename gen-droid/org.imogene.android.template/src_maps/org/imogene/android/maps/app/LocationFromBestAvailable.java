package org.imogene.android.maps.app;

import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;

public class LocationFromBestAvailable extends LocationFromSensor {
	
	@Override
	protected String obtainProvider() {
		LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		return manager.getBestProvider(criteria, true);
	};

}
