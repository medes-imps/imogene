package org.imogene.android.widget.field.edit;

import org.imogene.android.maps.MapsConstants;
import org.imogene.android.maps.app.LocationViewer;
import org.imogene.android.template.R;
import org.imogene.android.util.FormatHelper;
import org.imogene.android.widget.field.FieldManager;
import org.imogene.android.widget.field.FieldManager.OnActivityResultListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.location.Location;
import android.util.AttributeSet;
import android.view.View;

public class GeoFieldEdit extends BaseFieldEdit<Location> implements OnActivityResultListener {

	private int mProvider = -1;
	private int mRequestCode;
	
	public GeoFieldEdit(Context context) {
		super(context, R.layout.ig_field_edit_buttons);
		init();
	}

	public GeoFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.ig_field_edit_buttons);
		init();
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GeoFieldEdit, 0, 0);
		mProvider = a.getInt(R.styleable.GeoFieldEdit_igGeoType, -1);
		a.recycle();
	}
	
	private void init() {
		findViewById(R.id.ig_acquire).setOnClickListener(this);
		findViewById(R.id.ig_delete).setOnClickListener(this);
		findViewById(R.id.ig_view).setOnClickListener(this);
	}
	
	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		findViewById(R.id.ig_acquire).setVisibility(readOnly ? View.GONE : View.VISIBLE);
		findViewById(R.id.ig_delete).setVisibility(readOnly ? View.GONE : View.VISIBLE);
		findViewById(R.id.ig_view).setVisibility(readOnly ? View.GONE : View.VISIBLE);
	}
	
	@Override
	public void onAttachedToHierarchy(FieldManager manager) {
		super.onAttachedToHierarchy(manager);
		manager.registerOnActivityResultListener(this);
		mRequestCode = manager.getNextId();
	}
	
	@Override
	public String getFieldDisplay() {
		final Location location = getValue();
		if (location != null) {
			return FormatHelper.displayLocation(getContext(), location);
		} else {
			return getEmptyText();
		}
	}
	
	@Override
	protected void onChangeValue() {
		super.onChangeValue();
		final Location location = getValue();
		if (!isReadOnly()) {
			if (location == null) {
				findViewById(R.id.ig_acquire).setVisibility(View.VISIBLE);
				findViewById(R.id.ig_delete).setVisibility(View.GONE);
				findViewById(R.id.ig_view).setVisibility(View.GONE);
			} else {
				findViewById(R.id.ig_acquire).setVisibility(View.GONE);
				findViewById(R.id.ig_delete).setVisibility(View.VISIBLE);
				findViewById(R.id.ig_view).setVisibility(View.VISIBLE);
			}
		} else {
			findViewById(R.id.ig_acquire).setVisibility(View.GONE);
			findViewById(R.id.ig_delete).setVisibility(View.GONE);
			findViewById(R.id.ig_view).setVisibility(View.GONE);
		}
	}
	
	@Override
	protected void dispatchClick(View v) {
		switch (v.getId()) {
		case R.id.ig_acquire:
			Intent acquire = new Intent(Intent.ACTION_PICK);
			acquire.setType(MapsConstants.MIME_GPS);
			switch (mProvider) {
			case 0:
				acquire.addCategory(MapsConstants.CATEGORY_GPS);
				break;
			case 1:
				acquire.addCategory(MapsConstants.CATEGORY_NETWORK);
				break;
			case 2:
				acquire.addCategory(MapsConstants.CATEGORY_MAP);
				break;
			case 3:
				acquire.addCategory(MapsConstants.CATEGORY_BEST);
				break;
			}
			getFieldManager().getActivity().startActivityForResult(acquire, mRequestCode);
			break;
		case R.id.ig_delete:
			setValue(null);
			break;
		case R.id.ig_view:
			final Location l = getValue();
			if (l != null) {
				Intent intent = new Intent(getContext(), LocationViewer.class);
				intent.putExtra(MapsConstants.EXTRA_LATITUDE, l.getLatitude());
				intent.putExtra(MapsConstants.EXTRA_LONGITUDE, l.getLongitude());
				getContext().startActivity(intent);
			}
			break;
		}
	}
	
	@Override
	public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == mRequestCode && resultCode != Activity.RESULT_CANCELED) {
			setValue((Location) data.getParcelableExtra(MapsConstants.EXTRA_LOCATION));
			return true;
		}
		return false;
	}
	
}
