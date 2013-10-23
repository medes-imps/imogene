package org.imogene.android.maps.app;

import org.imogene.android.maps.MapsConstants;
import org.imogene.android.template.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class LocationFromSensor extends Activity implements OnCancelListener, LocationListener {

	private static final int PROGRESS_DIALOG_ID = 1;
	
	private LocationManager mLocationManager;
	private String mProvider;
	
		@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mProvider = obtainProvider();
		if (mProvider == null) {
			setResult(RESULT_CANCELED);
			finish();
			return;
		}
			
		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (!mLocationManager.isProviderEnabled(mProvider)) {
			Toast.makeText(this, getString(R.string.maps_provider_disabled, mProvider), Toast.LENGTH_LONG).show();
			setResult(RESULT_CANCELED);
			finish();
			return;
		}
		showDialog(PROGRESS_DIALOG_ID);
	}
		
	protected String obtainProvider() {
		return LocationManager.GPS_PROVIDER;
	}
	
	@Override
	protected void onResume() {	
		super.onResume();
		mLocationManager.requestLocationUpdates(mProvider, 0, 0, this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mLocationManager.removeUpdates(this);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case PROGRESS_DIALOG_ID:
			ProgressDialog dialog = new ProgressDialog(this);
			dialog.setMessage(getString(R.string.maps_obtaining_location));
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			dialog.setOnCancelListener(this);
			return dialog;
		default:
			return super.onCreateDialog(id);
		}
	}
	
	@Override
	public void onCancel(DialogInterface dialog) {
		mLocationManager.removeUpdates(this);
		setResult(RESULT_CANCELED);
		finish();
	}
	
	@Override
	public void onLocationChanged(Location location) {
		mLocationManager.removeUpdates(this);
		setResult(RESULT_OK, new Intent().putExtra(MapsConstants.EXTRA_LOCATION, location));
		finish();
	}

	@Override
	public void onProviderDisabled(String provider) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}

}
