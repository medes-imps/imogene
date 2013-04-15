package org.imogene.android.widget.field.view;

import greendroid.widget.QuickAction;
import greendroid.widget.QuickActionBar;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;

import org.imogene.android.maps.MapsConstants;
import org.imogene.android.maps.app.LocationViewer;
import org.imogene.android.radar.app.RadarActivity;
import org.imogene.android.template.R;
import org.imogene.android.util.FormatHelper;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;

public class GeoFieldView extends BaseFieldView<Location> implements OnQuickActionClickListener {
	
	private QuickActionBar mBar;
	
	public GeoFieldView(Context context) {
		super(context, R.layout.ig_field_default);
		init();
	}

	public GeoFieldView(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.ig_field_default);
		init();
	}
	
	private void init() {
		setOnClickListener(this);
		setIconId(android.R.drawable.ic_dialog_map);
	}
	
	@Override
	protected void dispatchClick(View v) {
		if (mBar == null) {
			final Context context = getContext();
			mBar = new QuickActionBar(context) {{
				addQuickAction(new QuickAction(context, R.drawable.maps_navto, R.string.maps_navto));
				addQuickAction(new QuickAction(context, R.drawable.maps_map, R.string.maps_show_on_map));
				addQuickAction(new QuickAction(context, R.drawable.maps_radar, R.string.maps_navto_radar));
				
				setOnQuickActionClickListener(GeoFieldView.this);
			}};
		}
		
		mBar.show(this);
	}
	
	@Override
	public void onQuickActionClicked(QuickActionWidget widget, int position) {
		Location location = getValue();
		switch (position) {
		case 0:
			Uri uri = Uri.parse("google.navigation:q=" + location.getLatitude() + "," + location.getLongitude());
			getContext().startActivity(new Intent(Intent.ACTION_VIEW, uri));
			return;
		case 1:
			Intent map = new Intent(getContext(), LocationViewer.class);
			map.putExtra(MapsConstants.EXTRA_LATITUDE, location.getLatitude());
			map.putExtra(MapsConstants.EXTRA_LONGITUDE, location.getLongitude());
			getContext().startActivity(map);
			return;
		case 2:
			Intent radar = new Intent(getContext(), RadarActivity.class);
			radar.putExtra(MapsConstants.EXTRA_LATITUDE, location.getLatitude());
			radar.putExtra(MapsConstants.EXTRA_LONGITUDE, location.getLongitude());
			getContext().startActivity(radar);
			return;
		default:
			break;
		}
	}
	
	@Override
	public String getFieldDisplay() {
		final Location location = getValue();
		if (location != null) {
			return FormatHelper.displayLocation(getContext(), location);
		}
		return super.getFieldDisplay();
	}

}
