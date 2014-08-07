package org.imogene.android.widget.field.edit;

import org.imogene.android.Constants.Intents;
import org.imogene.android.template.R;
import org.imogene.android.util.IntentUtils;
import org.imogene.android.widget.field.FieldManager;
import org.imogene.android.widget.field.FieldManager.OnActivityResultListener;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

public class BarcodeFieldEdit extends BaseFieldEdit<String> implements OnActivityResultListener {

	private int mRequestCode;

	public BarcodeFieldEdit(Context context) {
		super(context, R.layout.imog__field_edit_buttons);
		init();
	}

	public BarcodeFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.imog__field_edit_buttons);
		init();
	}

	private void init() {
		findViewById(R.id.imog__acquire).setOnClickListener(this);
		findViewById(R.id.imog__delete).setOnClickListener(this);
		findViewById(R.id.imog__view).setOnClickListener(this);
	}

	@Override
	public boolean isEmpty() {
		return TextUtils.isEmpty(getValue());
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
	public boolean matchesDependencyValue(String value) {
		final String str = getValue();
		return str != null ? str.matches(value) : false;
	}

	@Override
	public String getFieldDisplay() {
		final String value = getValue();
		if (TextUtils.isEmpty(value)) {
			return getEmptyText();
		} else {
			return value;
		}
	}

	@Override
	protected void updateView() {
		super.updateView();
		if (TextUtils.isEmpty(getValue())) {
			findViewById(R.id.imog__acquire).setVisibility(View.VISIBLE);
			findViewById(R.id.imog__delete).setVisibility(View.GONE);
			findViewById(R.id.imog__view).setVisibility(View.GONE);
		} else {
			findViewById(R.id.imog__acquire).setVisibility(View.GONE);
			findViewById(R.id.imog__delete).setVisibility(View.VISIBLE);
			findViewById(R.id.imog__view).setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void dispatchClick(View v) {
		switch (v.getId()) {
		case R.id.imog__acquire:
			Intent intent = new Intent(Intents.ACTION_SCAN);
			IntentUtils.startActivityForResult(getFieldManager().getActivity(), intent, mRequestCode);
			break;
		case R.id.imog__delete:
			setValueInternal(null, true);
			break;
		case R.id.imog__view:
			showDialog(null);
			break;
		}
	}

	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		builder.setMessage(getValue());
		builder.setPositiveButton(android.R.string.ok, null);
	}

	@Override
	public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == mRequestCode && resultCode != Activity.RESULT_CANCELED) {
			setValueInternal(data.getStringExtra("SCAN_RESULT"), true);
			return true;
		}
		return false;
	}

}
