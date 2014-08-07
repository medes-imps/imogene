package org.imogene.android.widget.field.edit;

import org.imogene.android.common.binary.Binary;
import org.imogene.android.common.binary.BinaryFile;
import org.imogene.android.template.R;
import org.imogene.android.widget.field.FieldManager;
import org.imogene.android.widget.field.FieldManager.OnActivityResultListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import fr.medes.android.util.content.ContentUrisUtils;

public class BinaryFieldEdit extends BaseFieldEdit<Binary> implements OnActivityResultListener {

	private int mRequestCode;

	public BinaryFieldEdit(Context context) {
		super(context, R.layout.imog__field_edit_buttons);
		init();
	}

	public BinaryFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.imog__field_edit_buttons);
		init();
	}

	private void init() {
		findViewById(R.id.imog__acquire).setOnClickListener(this);
		findViewById(R.id.imog__delete).setOnClickListener(this);
		findViewById(R.id.imog__view).setOnClickListener(this);
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

	protected int displayId() {
		return R.string.imog__bin_binary;
	}

	@Override
	public String getFieldDisplay() {
		return getValue() != null ? getValue().getMainDisplay(getContext()) : getEmptyText();
	}

	@Override
	protected void updateView() {
		super.updateView();
		boolean isNull = getValue() == null;
		findViewById(R.id.imog__acquire).setVisibility(isNull ? View.VISIBLE : View.GONE);
		findViewById(R.id.imog__delete).setVisibility(isNull ? View.GONE : View.VISIBLE);
		findViewById(R.id.imog__view).setVisibility(isNull ? View.GONE : View.VISIBLE);
	}

	@Override
	protected void dispatchClick(View v) {
		switch (v.getId()) {
		case R.id.imog__acquire:
			acquire(mRequestCode);
			break;
		case R.id.imog__delete:
			delete();
			break;
		case R.id.imog__view:
			view();
			break;
		}
	}

	protected void acquire(int requestCode) {
		Intent acquire = new Intent(Intent.ACTION_GET_CONTENT);
		onCreateIntent(acquire);
		startActivityForResult(acquire, mRequestCode);
	}

	protected void onCreateIntent(Intent acquire) {
		acquire.addCategory(Intent.CATEGORY_OPENABLE);
		acquire.setType("*/*");
	}

	protected void delete() {
		setValueInternal(null, true);
	}

	protected void view() {
		Binary value = getValue();
		if (value != null) {
			Uri uri = ContentUrisUtils.withAppendedId(Binary.Columns.CONTENT_URI, value.getId());
			Intent show = new Intent(Intent.ACTION_VIEW, uri);
			getContext().startActivity(show);
		}
	}

	@Override
	public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == mRequestCode && resultCode != Activity.RESULT_CANCELED) {
			Binary binary = BinaryFile.toBinary(getContext(), data.getData());
			setValueInternal(binary, true);
			return true;
		}
		return false;
	}

}
