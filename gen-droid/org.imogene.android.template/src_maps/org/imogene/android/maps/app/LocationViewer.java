package org.imogene.android.maps.app;

import greendroid.widget.ActionBar;
import greendroid.widget.ActionBarItem;
import greendroid.widget.NormalActionBarItem;

import org.imogene.android.maps.MapsConstants;
import org.imogene.android.maps.overlay.BubbleOverlay;
import org.imogene.android.template.R;
import org.osmdroid.util.GeoPoint;

import android.content.Intent;
import android.os.Bundle;

public class LocationViewer extends MapActivity {
	
	private static final int ACTIONBAR_AUTOZOOM_ID = 100;
	private static final int ACTIONBAR_MAPMODE_ID = 101;
	private static final int ACTIONBAR_OFFLINE_ID = 102;
	
	private BubbleOverlay mOverlay;

	public LocationViewer() {
		super(ActionBar.Type.Empty);
	}

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Intent intent = getIntent();
		double lat = intent.getDoubleExtra(MapsConstants.EXTRA_LATITUDE, MapsConstants.DEFAULT_LATIUDE);
		double lon = intent.getDoubleExtra(MapsConstants.EXTRA_LONGITUDE, MapsConstants.DEFAULT_LONGITUDE);
		
		mOverlay = new BubbleOverlay(this, new GeoPoint(lat, lon));

		getMapView().getOverlays().add(mOverlay);
		
		addActionBarItem(getGDActionBar().newActionBarItem(NormalActionBarItem.class)
				.setDrawable(R.drawable.ig_ic_title_autozoom), ACTIONBAR_AUTOZOOM_ID);
		
		addActionBarItem(getGDActionBar().newActionBarItem(NormalActionBarItem.class)
				.setDrawable(R.drawable.ig_ic_title_mapmode), ACTIONBAR_MAPMODE_ID);
		
		addActionBarItem(getGDActionBar().newActionBarItem(NormalActionBarItem.class)
				.setDrawable(R.drawable.ig_ic_title_offline), ACTIONBAR_OFFLINE_ID);
	}
	
	@Override
	public boolean onHandleActionBarItemClick(ActionBarItem item, int position) {
		switch (item.getItemId()) {
		case ACTIONBAR_AUTOZOOM_ID:
			autozoom(mOverlay.getGeoPoint());
			return true;
		case ACTIONBAR_MAPMODE_ID:
			showDialog(DIALOG_MAPMODE_ID);
			return true;
		case ACTIONBAR_OFFLINE_ID:
			changeConnectionState();
			return true;
		default:
			return super.onHandleActionBarItemClick(item, position);
		}
	}
	
}
