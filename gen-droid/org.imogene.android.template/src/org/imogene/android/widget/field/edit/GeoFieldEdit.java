package org.imogene.android.widget.field.edit;

import org.imogene.android.template.R;
import org.imogene.android.widget.field.FieldManager;
import org.imogene.android.widget.field.FieldManager.OnActivityResultListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.location.Location;
import android.util.AttributeSet;
import android.view.View;
import fr.medes.android.maps.MapsConstants;
import fr.medes.android.maps.app.LocationViewer;
import fr.medes.android.util.FormatHelper;

public class GeoFieldEdit extends BaseFieldEdit<Location> implements OnActivityResultListener {

	private int mProvider = -1;
	private int mRequestCode;

	public GeoFieldEdit(Context context) {
		super(context, R.layout.imog__field_edit_buttons);
		findViewById(R.id.imog__acquire).setOnClickListener(this);
		findViewById(R.id.imog__delete).setOnClickListener(this);
		findViewById(R.id.imog__view).setOnClickListener(this);
	}

	public GeoFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.imog__field_edit_buttons);
		findViewById(R.id.imog__acquire).setOnClickListener(this);
		findViewById(R.id.imog__delete).setOnClickListener(this);
		findViewById(R.id.imog__view).setOnClickListener(this);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GeoFieldEdit, 0, 0);
		mProvider = a.getInt(R.styleable.GeoFieldEdit_geoType, -1);
		a.recycle();
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		findViewById(R.id.imog__acquire).setVisibility(readOnly ? View.GONE : View.VISIBLE);
		findViewById(R.id.imog__delete).setVisibility(readOnly ? View.GONE : View.VISIBLE);
		findViewById(R.id.imog__view).setVisibility(readOnly ? View.GONE : View.VISIBLE);
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
	protected void updateView() {
		super.updateView();
		if (!isReadOnly()) {
			boolean isNull = getValue() == null;
			findViewById(R.id.imog__acquire).setVisibility(isNull ? View.VISIBLE : View.GONE);
			findViewById(R.id.imog__delete).setVisibility(isNull ? View.GONE : View.VISIBLE);
			findViewById(R.id.imog__view).setVisibility(isNull ? View.GONE : View.VISIBLE);
		} else {
			findViewById(R.id.imog__acquire).setVisibility(View.GONE);
			findViewById(R.id.imog__delete).setVisibility(View.GONE);
			findViewById(R.id.imog__view).setVisibility(View.GONE);
		}
	}

	@Override
	protected void dispatchClick(View v) {
		switch (v.getId()) {
		case R.id.imog__acquire:
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
			startActivityForResult(acquire, mRequestCode);
			break;
		case R.id.imog__delete:
			setValueInternal(null, true);
			break;
		case R.id.imog__view:
			final Location l = getValue();
			if (l != null) {
				Intent intent = new Intent(getContext(), LocationViewer.class);
				intent.putExtra(MapsConstants.EXTRA_LATITUDE, l.getLatitude());
				intent.putExtra(MapsConstants.EXTRA_LONGITUDE, l.getLongitude());
				startActivity(intent);
			}
			break;
		}
	}

	@Override
	public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == mRequestCode && resultCode != Activity.RESULT_CANCELED) {
			setValueInternal((Location) data.getParcelableExtra(MapsConstants.EXTRA_LOCATION), true);
			return true;
		}
		return false;
	}

}
