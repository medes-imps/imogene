package org.imogene.android.maps.offline;

import org.imogene.android.app.WakefulIntentService;
import org.imogene.android.maps.MapsConstants;
import org.imogene.android.template.R;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class TileLoaderSettings extends Activity implements OnClickListener {
	
	private static final int DIALOG_ZOOM_ERROR_ID = 1;
	
	private OnlineTileSourceBase mTileSource = TileSourceFactory.MAPNIK;
	private double mLatitude;
	private double mLongitude;
	
	private Spinner mZoomMinSpinner;
	private Spinner mZoomMaxSpinner;
	private Spinner mAreaSpinner;
	
	private View mStartButton;
	private View mCancelButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps_tile_loader_settings_content);
		
		final Intent intent = getIntent();
		
		mLatitude = intent.getDoubleExtra(MapsConstants.EXTRA_LATITUDE, MapsConstants.DEFAULT_LATIUDE);
		mLongitude = intent.getDoubleExtra(MapsConstants.EXTRA_LONGITUDE, MapsConstants.DEFAULT_LONGITUDE);
		
		if (intent.hasExtra(MapsConstants.EXTRA_TILE_SOURCE)) {
			final String name = intent.getStringExtra(MapsConstants.EXTRA_TILE_SOURCE);
			ITileSource source = TileSourceFactory.getTileSource(name);
			if (source instanceof OnlineTileSourceBase) {
				mTileSource = (OnlineTileSourceBase) source;
			}
		}
		
		ArrayAdapter<Integer> zoomAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item);
		zoomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		for (int i = mTileSource.getMinimumZoomLevel(); i <= mTileSource.getMaximumZoomLevel(); i++) {
			zoomAdapter.add(i);
		}
		
		mZoomMinSpinner = (Spinner) findViewById(R.id.maps_offline_zoommin);
		mZoomMaxSpinner = (Spinner) findViewById(R.id.maps_offline_zoommax);
		mAreaSpinner = (Spinner) findViewById(R.id.maps_offline_area);
		
		mStartButton = findViewById(R.id.maps_offline_start);
		mCancelButton = findViewById(R.id.maps_offline_cancel);
		
		mZoomMinSpinner.setAdapter(zoomAdapter);
		mZoomMaxSpinner.setAdapter(zoomAdapter);
		mZoomMaxSpinner.setSelection(zoomAdapter.getCount() - 1);
		
		mStartButton.setOnClickListener(this);
		mCancelButton.setOnClickListener(this);
	}
	
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_ZOOM_ERROR_ID:
			return new AlertDialog.Builder(this)
			.setTitle(android.R.string.dialog_alert_title)
			.setMessage(R.string.maps_offline_zoom_error)
			.setPositiveButton(android.R.string.ok, null)
			.create();
		default:
			return super.onCreateDialog(id);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.maps_offline_start:
			int zoomMin = (Integer) mZoomMinSpinner.getSelectedItem();
			int zoomMax = (Integer) mZoomMaxSpinner.getSelectedItem();
			if (zoomMin < zoomMax) {
				final double distance = (1 << mAreaSpinner.getSelectedItemPosition() + 1) * 1000;
				final double lat = Math.toRadians(mLatitude);
				final double deltaLat = distance / (2 * MapsConstants.EARTH_MAJOR_AXIS);
				final double deltaLon = Math.abs(2 * Math.asin(Math.sin(distance / (4 * MapsConstants.EARTH_MAJOR_AXIS)) / Math.cos(lat)));
				
				final double deltaLatDegree = Math.toDegrees(deltaLat);
				final double deltaLonDegree = Math.toDegrees(deltaLon);
				
				final double latNorth = mLatitude + deltaLatDegree;
				final double latSouth = mLatitude - deltaLatDegree;
				final double lonEast = mLongitude + deltaLonDegree;
				final double lonWest = mLongitude - deltaLonDegree;
				
				Intent intent = new Intent(this, TileLoaderService.class);
				intent.putExtra(MapsConstants.EXTRA_TILE_SOURCE, mTileSource.name());
				intent.putExtra(MapsConstants.EXTRA_LAT_NORTH, latNorth);
				intent.putExtra(MapsConstants.EXTRA_LAT_SOUTH, latSouth);
				intent.putExtra(MapsConstants.EXTRA_LON_EAST, lonEast);
				intent.putExtra(MapsConstants.EXTRA_LON_WEST, lonWest);
				intent.putExtra(MapsConstants.EXTRA_ZOOM_MIN, zoomMin);
				intent.putExtra(MapsConstants.EXTRA_ZOOM_MAX, zoomMax);
				
				WakefulIntentService.sendWakefulWork(this, intent);
				
				startActivity(new Intent(this, TileLoaderActivity.class));
				finish();
			} else {
				showDialog(DIALOG_ZOOM_ERROR_ID);
			}
			break;
		case R.id.maps_offline_cancel:
			finish();
			break;	
		}
	}
	
}

