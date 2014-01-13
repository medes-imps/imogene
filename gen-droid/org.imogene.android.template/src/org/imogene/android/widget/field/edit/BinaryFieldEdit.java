package org.imogene.android.widget.field.edit;

import org.imogene.android.template.R;
import org.imogene.android.widget.field.FieldManager;
import org.imogene.android.widget.field.FieldManager.OnActivityResultListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;

public class BinaryFieldEdit extends BaseFieldEdit<Uri> implements OnActivityResultListener {

	private int mRequestCode;
	
	public BinaryFieldEdit(Context context) {
		super(context, R.layout.ig_field_edit_buttons);
		init();
	}
	
	public BinaryFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.ig_field_edit_buttons);
		init();
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
	
	protected int displayId() {
		return R.string.ig_bin_binary;
	}
	
	@Override
	public String getFieldDisplay() {
		return getValue() != null ? getResources().getString(displayId()) : getEmptyText();
	}
	
	@Override
	protected void onChangeValue() {
		super.onChangeValue();
		final Uri uri = getValue();
		if (uri == null) {
			findViewById(R.id.ig_acquire).setVisibility(View.VISIBLE);
			findViewById(R.id.ig_delete).setVisibility(View.GONE);
			findViewById(R.id.ig_view).setVisibility(View.GONE);
		} else {
			findViewById(R.id.ig_acquire).setVisibility(View.GONE);
			findViewById(R.id.ig_delete).setVisibility(View.VISIBLE);
			findViewById(R.id.ig_view).setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	protected void dispatchClick(View v) {
		switch (v.getId()) {
		case R.id.ig_acquire:
			acquire(mRequestCode);
			break;
		case R.id.ig_delete:
			delete();
			break;
		case R.id.ig_view:
			view();
			break;
		}
	}
	
	protected void acquire(int requestCode) {
		Intent acquire = new Intent(Intent.ACTION_GET_CONTENT);
		onCreateIntent(acquire);
		getFieldManager().getActivity().startActivityForResult(acquire, mRequestCode);
	}
	
	protected void onCreateIntent(Intent acquire) {
		acquire.addCategory(Intent.CATEGORY_OPENABLE);
		acquire.setType("*/*");
	}
	
	protected void delete() {
		setValue(null);
	}
	
	protected void view() {
		Intent show = new Intent(Intent.ACTION_VIEW, getValue());
		getContext().startActivity(show);
	}
	
	@Override
	public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == mRequestCode && resultCode != Activity.RESULT_CANCELED) {
			setValue(data.getData());
			return true;
		}
		return false;
	}
	
}
