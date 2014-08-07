package org.imogene.android.widget.field.view;

import org.imogene.android.template.R;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import fr.medes.android.maps.MapsConstants;
import fr.medes.android.maps.app.LocationViewer;
import fr.medes.android.radar.app.RadarActivity;
import fr.medes.android.util.FormatHelper;
import fr.medes.android.widget.QuickAction;
import fr.medes.android.widget.QuickActionBar;
import fr.medes.android.widget.QuickActionWidget;
import fr.medes.android.widget.QuickActionWidget.OnQuickActionClickListener;

public class GeoFieldView extends BaseFieldView<Location> implements OnQuickActionClickListener {

	private QuickActionBar mBar;

	public GeoFieldView(Context context) {
		super(context, R.layout.imog__field_default);
		init();
	}

	public GeoFieldView(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.imog__field_default);
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
			mBar = new QuickActionBar(context) {
				{
					addQuickAction(new QuickAction(context, R.drawable.maps__navto, R.string.maps__navto));
					addQuickAction(new QuickAction(context, R.drawable.maps__map, R.string.maps__show_on_map));
					addQuickAction(new QuickAction(context, R.drawable.maps__radar, R.string.maps__navto_radar));

					setOnQuickActionClickListener(GeoFieldView.this);
				}
			};
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
