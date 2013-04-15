package org.imogene.android.maps.app;

import greendroid.widget.ActionBar;
import greendroid.widget.ActionBarItem;
import greendroid.widget.NormalActionBarItem;
import greendroid.widget.QuickAction;
import greendroid.widget.QuickActionBar;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;

import org.imogene.android.maps.MapsConstants;
import org.imogene.android.maps.overlay.BubbleClickableOverlay;
import org.imogene.android.template.R;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;


public class LocationPicker extends MapActivity implements OnQuickActionClickListener {
	
	private static final int ACTIONBAR_AUTOZOOM_ID = 100;
	private static final int ACTIONBAR_SAVE_ID = 101;
	private static final int ACTIONBAR_MORE_ID = 102;
	
	private BubbleClickableOverlay mOverlay;
	
	public LocationPicker() {
		super(ActionBar.Type.Empty);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (l == null) {
			l = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		
		final GeoPoint point;
		if (l != null) {
			point = new GeoPoint(l.getLatitude(), l.getLongitude());
		} else {
			final Intent intent = getIntent();
			double lat = intent.getDoubleExtra(MapsConstants.EXTRA_LATITUDE, MapsConstants.DEFAULT_LATIUDE);
			double lon = intent.getDoubleExtra(MapsConstants.EXTRA_LONGITUDE, MapsConstants.DEFAULT_LONGITUDE);
			point = new GeoPoint(lat, lon);
		}
		
		mOverlay = new BubbleClickableOverlay(this, point);
		getMapView().getOverlays().add(mOverlay);
		
		addActionBarItem(getGDActionBar().newActionBarItem(NormalActionBarItem.class)
				.setDrawable(R.drawable.ig_ic_title_save), ACTIONBAR_SAVE_ID);

		addActionBarItem(getGDActionBar().newActionBarItem(NormalActionBarItem.class)
				.setDrawable(R.drawable.ig_ic_title_autozoom),	ACTIONBAR_AUTOZOOM_ID);
		
		addActionBarItem(getGDActionBar().newActionBarItem(NormalActionBarItem.class)
				.setDrawable(R.drawable.ig_ic_title_more), ACTIONBAR_MORE_ID);
	}
	
	@Override
	public boolean isPrecachingEnabled() {
		return false;
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		IGeoPoint point = mOverlay.getGeoPoint();
		outState.putInt(MapsConstants.EXTRA_LATITUDE, point.getLatitudeE6());
		outState.putInt(MapsConstants.EXTRA_LONGITUDE, point.getLongitudeE6());
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		int latitude = savedInstanceState.getInt(MapsConstants.EXTRA_LATITUDE);
		int longitude = savedInstanceState.getInt(MapsConstants.EXTRA_LONGITUDE);
		GeoPoint point = new GeoPoint(latitude, longitude);
		mOverlay.setGeoPoint(point);

		autozoom(point);
	}
	
	@Override
	public boolean onHandleActionBarItemClick(final ActionBarItem item, int position) {
		switch (item.getItemId()) {
		case ACTIONBAR_AUTOZOOM_ID:
			autozoom(mOverlay.getGeoPoint());
			return true;
		case ACTIONBAR_SAVE_ID:
			IGeoPoint point = mOverlay.getGeoPoint();
			Location location = new Location("medesmapprovider");
			location.setLatitude(point.getLatitudeE6() / (double) 1E6);
			location.setLongitude(point.getLongitudeE6() / (double) 1E6);
			setResult(RESULT_OK, new Intent().putExtra(MapsConstants.EXTRA_LOCATION, location));
			finish();
			return true;
		case ACTIONBAR_MORE_ID:
			new QuickActionBar(this) {{
				addQuickAction(new QuickAction(LocationPicker.this, R.drawable.ig_ic_title_mylocation, R.string.maps_my_location));
				addQuickAction(new QuickAction(LocationPicker.this, R.drawable.ig_ic_title_compass, R.string.maps_compass));
				addQuickAction(new QuickAction(LocationPicker.this, R.drawable.ig_ic_title_mapmode, R.string.maps_map_mode));
				addQuickAction(new QuickAction(LocationPicker.this, R.drawable.ig_ic_title_offline, R.string.maps_offline_mode));
				setOnQuickActionClickListener(LocationPicker.this);
				show(item.getItemView());
			}};
			return true;
		default:
			return super.onHandleActionBarItemClick(item, position);
		}
	}
	
	@Override
	public void onQuickActionClicked(QuickActionWidget widget, int position) {
		switch (position) {
		case 0:
			changeMyLocationState();
			break;
		case 1:
			changeCompassState();
			break;
		case 2:
			showDialog(DIALOG_MAPMODE_ID);
			break;
		case 3:
			changeConnectionState();
			break;
		default:
			break;
		}
	}

}
